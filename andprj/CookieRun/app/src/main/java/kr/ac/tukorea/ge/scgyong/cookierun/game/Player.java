package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.JsonReader;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.SheetSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.Sound;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;



// SheetSprite를 상속받아 스프라이트 시트 애니메이션 처리 가능
// IBoxCollidable 인터페이스 구현으로 충돌 검사용 사각형(RectF) 제공
public class Player extends SheetSprite implements IBoxCollidable {
    private static final String TAG = Player.class.getSimpleName();

    public enum State {
        running, jump, doubleJump, falling, slide, hurt
        // running 달리기 상태
        // jump 점프 상태
        // doubleJump 이중 점프 상태
        // falling 떨어지는 상태
        // slide 슬라이드 상태
        // hurt 피격 상태
    }

    protected State state = State.running;
    // 현재 상태 (기본은 running)

    private float jumpSpeed;
    // 점프 및 낙하 속도 (y축 속도)

    private final RectF collisionRect = new RectF();
    private Obstacle obstacle;
    // 플레이어가 맞은 장애물 참조용

    //private static final float JUMP_POWER = 900f;
    private static final float GRAVITY = 1700f;
    private static final float NORMAL_COOKIE_DST_SIZE = 386;
    // 플레이어 기본 크기

    private int imageSize = 0;
    // 각 스프라이트 이미지 크기 (가로/세로)

    // 쿠키별 고유 정보 (점프력, 이름, 점수 비율 등)
    public static class CookieInfo {
        public int id;
        public String name;
        public float jumpPower, scoreRate;
    }
    public static int[] COOKIE_IDS;
    public static HashMap<Integer, CookieInfo> cookieInfoMap;
    // 쿠키의 고유 속성을 저장하는 내부 클래스
    //cookieInfoMap에 쿠키 ID별 정보를 저장하고 관리

    private final CookieInfo cookieInfo;

    // 상태별로 사용할 이미지 소스 영역 배열
    protected Rect[][] srcRectsArray;

    // 상태별 애니메이션 프레임 인덱스를 배열로 정의
    // 각 상태에 따라 다양한 프레임 사용 가능
    private void makeSourceRects() {
        srcRectsArray = new Rect[][] {
                makeRects(100, 101, 102, 103), // State.running // running 애니메이션 프레임 인덱스
                makeRects(7, 8),               // State.jump
                makeRects(1, 2, 3, 4),         // State.doubleJump
                makeRects(0),                  // State.falling
                makeRects(9, 10),              // State.slide
                makeRects(503, 504),           // State.hurt
        };
    }

    // 각 상태별로 충돌 박스의 좌우, 상하 여백 비율을 정의
    // collisionRect를 dstRect보다 작게 만들어 충돌 검사 정확도를 높임
    protected static float[][] edgeInsetRatios = {
            { 0.3f, 0.5f, 0.3f, 0.0f }, // State.running
            { 0.3f, 0.6f, 0.3f, 0.0f }, // State.jump
            { 0.3f, 0.6f, 0.3f, 0.0f }, // State.doubleJump
            { 0.3f, 0.5f, 0.3f, 0.0f }, // State.falling
            { 0.2f, 0.75f, 0.2f, 0.0f }, // State.slide
            { 0.3f, 0.50f, 0.4f, 0.0f }, // State.hurt
    };

    // 쿠키 정보 로드 (JSON)
    // cookies.json에서 쿠키 데이터(아이디, 이름, 점프력, 점수 배율)를 파싱해서 저장
    // 게임 내 여러 쿠키 정보를 불러오고 관리
    public static void load(Context context) {
        if (cookieInfoMap != null) return;

        ArrayList<Integer> idArrayList = new ArrayList<>();
        AssetManager assets = context.getAssets();
        try {
            InputStream is = assets.open("cookies.json");
            InputStreamReader isr = new InputStreamReader(is);
            JsonReader jr = new JsonReader(isr);
            jr.beginArray();
            cookieInfoMap = new HashMap<>();
            while (jr.hasNext()) {
                CookieInfo ci = new CookieInfo();
                jr.beginObject();
                while (jr.hasNext()) {
                    String name = jr.nextName();
                    switch (name) { // Java 에서는 String 으로 switch-case 가 가능하다
                        case "id":
                            ci.id = jr.nextInt();
                            break;
                        case "name":
                            ci.name = jr.nextString();
                            break;
                        case "jumpPower":
                            ci.jumpPower = (float) jr.nextDouble();
                            break;
                        case "scoreRate":
                            ci.scoreRate = (float) jr.nextDouble();
                            break;
                    }
                }
                jr.endObject();
                if (ci.id == 0) break;
                cookieInfoMap.put(ci.id, ci);
                idArrayList.add(ci.id);
            }
            jr.endArray();
            jr.close();
            COOKIE_IDS = new int[idArrayList.size()];
            for (int i = 0; i < COOKIE_IDS.length; i++) {
                COOKIE_IDS[i] = idArrayList.get(i);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 스프라이트 시트에서 각 프레임 이미지의 좌표(Rect)를 계산
    // 인덱스별로 위치가 정해짐 (가로, 세로 기준)
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = 2 + (idx % 100) * (imageSize + 2);
            int t = 2 + (idx / 100) * (imageSize + 2);
            rects[i] = new Rect(l, t, l + imageSize, t + imageSize);
        }
        return rects;
    }

    // 특정 쿠키 ID로 플레이어 객체 생성
    // 해당 쿠키 스프라이트 시트 로드
    // 초기 위치, 크기 설정
    // 상태 running으로 초기화
    public Player(int cookieId) {
        super(0, 8);
        loadSheetFromAsset(cookieId);
        cookieInfo = cookieInfoMap.get(cookieId);
        setPosition(200f, 200f, NORMAL_COOKIE_DST_SIZE, NORMAL_COOKIE_DST_SIZE);
        setState(State.running);
    }

    // 쿠키 ID에 맞는 스프라이트 시트 파일을 불러옴
    //가로 11 프레임으로 가정해 각 프레임 크기를 계산
    //상태별 소스 Rect 배열 생성
    private void loadSheetFromAsset(int cookieId) {
        AssetManager assets = GameView.view.getContext().getAssets();
        String filename = "cookies/" + cookieId + "_sheet.png";
        try {
            InputStream is = assets.open(filename);
            bitmap = BitmapFactory.decodeStream(is);
            imageSize = (bitmap.getWidth() - 2) / 11 - 2;
            // 쿠키마다 이미지 한 장에 할애된 크기가 다르다. 가로로 11장이 있으므로
            // 구분선 2px 를 제외하고 한장당의 이미지 크기를 구한다.
            //Log.d(TAG, "File=" + filename + " imageSize=" + imageSize);
            makeSourceRects();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 플레이어 상태별로 움직임과 물리 처리를 다르게 함
    // 점프/더블점프/낙하 상태: 중력 적용하여 y 위치 변경
    // 착지하면 상태 running으로 변경
    // 달리기/슬라이드 중 바닥이 없으면 낙하 상태로 전환
    // 피격 상태면 장애물과 더 이상 충돌하지 않을 때 상태 복귀
    // magSpeed가 있으면 확대/축소 애니메이션 처리
    @Override
    public void update() {
        float foot = collisionRect.bottom;
        switch (state) {
        case jump:
        case doubleJump:
        case falling:
            float dy = jumpSpeed * GameView.frameTime;
            jumpSpeed += GRAVITY * GameView.frameTime;
            if (jumpSpeed >= 0) { // 낙하하고 있다면 발밑에 땅이 있는지 확인한다
                float floor = findNearestFloorTop(foot);
                if (foot + dy >= floor) {
                    dy = floor - foot;
                    setState(State.running);
                }
            }
            foot += dy;
            setCookiePosition(foot);
            break;
        case running:
        case slide:
            float floor = findNearestFloorTop(foot);
            if (foot < floor) {
                Log.v(TAG, "foot=" + foot + " floor=" + floor + " magSpeed=" + magSpeed);
                // 달리는 중에 발밑 floor 좌표가 발보다 아래에 있다면 떨어지자
                setState(State.falling);
                jumpSpeed = 0; // 자유낙하이므로 속도가 0 부터 시작한다.
            }
            break;
        case hurt:
            if (!CollisionHelper.collides(this, obstacle)) {
                setState(State.running);
                obstacle = null;
            }
            break;
        }
        if (magSpeed != 0) {
            scale += GameView.frameTime * magSpeed;
            if (magSpeed < 0 && scale <= SCALE_NORMAL) {
                magSpeed = 0;
                scale = SCALE_NORMAL;
            } else if (magSpeed > 0 && scale >= SCALE_MAGNIFIED) {
                magSpeed = 0;
                scale = SCALE_MAGNIFIED;
            }
            //Log.i(TAG, "dstR.bot=" + dstRect.bottom + " colR.bot=" + collisionRect.bottom);
            width = height = NORMAL_COOKIE_DST_SIZE * scale;
            setCookiePosition(foot);
        }
    }

    // 플레이어 발(foot) 위치 기준 아래쪽에서 가장 가까운 바닥을 찾음
    // 바닥 리스트를 순회하면서 조건에 맞는 바닥 중 가장 위쪽(높이 낮은) 바닥 반환
    private float findNearestFloorTop(float foot) {
        // 플레이어 발의 y 좌표에서 아래쪽으로 가장 가까운 floor 의 좌표를 찾는다.
        Floor platform = findNearestFloor(foot);
        if (platform == null) return Metrics.height;
        return platform.getCollisionRect().top;
    }

    private Floor findNearestFloor(float foot) {
        // 플레이어 발의 y 좌표에서 아래쪽으로 가장 가까운 floor 를 찾는다.
        Floor nearest = null;
        MainScene scene = (MainScene) Scene.top();
        if (scene == null) return null;
        ArrayList<IGameObject> floors = scene.objectsAt(MainScene.Layer.floor);
        float top = Metrics.height; // 못 찾으면 디폴트 값은 화면 아래이다.
        for (IGameObject obj: floors) {
            Floor floor = (Floor) obj;
            RectF rect = floor.getCollisionRect();
            if (rect.left > x || x > rect.right) {
                // floor 의 좌우 좌표 범위가 player 의 x 좌표를 포함하지 않으면 대상에서 제외한다.
                continue;
            }
            //Log.d(TAG, "foot:" + foot + " floor: " + rect);
            if (rect.top < foot) {
                // 발보다 위에 있는 floor 는 대상에서 제외한다
                continue;
            }
            if (top > rect.top) {
                // 더 가까운 것을 찾았다.
                top = rect.top;
                nearest = floor;
            }
            //Log.d(TAG, "top=" + top + " gotcha:" + floor);
        }
        return nearest;
    }


    // 플레이어 위치는 foot 좌표(발 위치)로부터 계산
    // 충돌 사각형은 현재 상태에 따른 비율만큼 축소
    private void setCookiePosition(float foot) {
        float hw = width / 2;
        dstRect.set(x - hw, foot - height, x + hw, foot);
        updateCollisionRect();
    }

    private void updateCollisionRect() {
        float[] insets = edgeInsetRatios[state.ordinal()];
        collisionRect.set(
                dstRect.left + width * insets[0],
                dstRect.top + height * insets[1],
                dstRect.right - width * insets[2],
                dstRect.bottom - height * insets[3]);
    }

    // 상태가 바뀌면 애니메이션 프레임 초기화, 충돌 영역 업데이트
    // 달리기 상태면 점프 속도 초기화
    private void setState(State state) {
        this.state = state;
        srcRects = srcRectsArray[state.ordinal()];
        updateCollisionRect();
    }


    // 달리기 상태에서 점프하면 jumpSpeed를 음수로 세팅(위로 속도)
    // 점프 중에 다시 점프하면 더 약한 힘으로 이중 점프
    // 이외 상태에서는 무시
    public void jump() {
        if (state == State.running) {
            //jumpSpeed = -JUMP_POWER;
            jumpSpeed = -cookieInfo.jumpPower;
            Sound.playEffect(R.raw.jump1);
            setState(State.jump);
        } else if (state == State.jump) {
            //jumpSpeed = -JUMP_POWER;
            jumpSpeed = -cookieInfo.jumpPower;
            //jumpSpeed -= JUMP_POWER;
            Sound.playEffect(R.raw.jump2);
            setState(State.doubleJump);
        }
    }

    // 달리기 상태에서 슬라이드 상태로 변경
    public void slide(boolean startsSlide) {
        if (state == State.running && startsSlide) {
            setState(State.slide);
            return;
        }
        if (state == State.slide && !startsSlide) {
            setState(State.running);
            //return;
        }
    }
    public void fall() {
        if (state != State.running) return;
        float foot = collisionRect.bottom;
        Floor floor = findNearestFloor(foot);
        if (floor == null) return;
        if (!floor.canPass()) return;
        y += 0.1f; // 아래로 아주 약간 내려준다.
        dstRect.offset(0, 0.1f); // y 좌표와 dstRect 를 함께 내려준다.
        setState(State.falling); // collisinRect 는 이곳에서 update 되므로 추가작업하지 않아도 된다.
        jumpSpeed = 0;
    }

    private static final float SCALE_NORMAL = 1.0f;
    private static final float SCALE_MAGNIFIED = 2.0f;
    private float scale = 1.0f, magSpeed = 0;
    public void magnify(boolean enlarges) {
//        magSpeed = enlarges ? 1.0f : -1.0f;
        magSpeed = scale == 1.0f ? 1.0f : -1.0f;
        Log.d(TAG, "Scale="+scale+" magSpeed="+magSpeed);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    // 장애물과 충돌 시 상태를 피격으로 변경하고 해당 장애물 참조 저장
    public void hurt(Obstacle obstacle) {
        if (state == State.hurt) return;
        Sound.playEffect(R.raw.hurt);
        setState(State.hurt);
        this.obstacle = obstacle;
    }
}
