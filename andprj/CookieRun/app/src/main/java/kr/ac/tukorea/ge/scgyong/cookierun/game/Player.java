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

public class Player extends SheetSprite implements IBoxCollidable {
    private static final String TAG = Player.class.getSimpleName();

    public enum State {
        running, jump, doubleJump, falling, slide, hurt
    }
    protected State state = State.running;
    private float jumpSpeed;
    private final RectF collisionRect = new RectF();
    private Obstacle obstacle;
    //private static final float JUMP_POWER = 900f;
    private static final float GRAVITY = 1700f;
    private static final float NORMAL_COOKIE_DST_SIZE = 386;

    private int imageSize = 0;
    public static class CookieInfo {
        public int id;
        public String name;
        public float jumpPower, scoreRate;
    }
    public static int[] COOKIE_IDS;
    public static HashMap<Integer, CookieInfo> cookieInfoMap;

    private final CookieInfo cookieInfo;

    protected Rect[][] srcRectsArray;
    private void makeSourceRects() {
        srcRectsArray = new Rect[][] {
                makeRects(100, 101, 102, 103), // State.running
                makeRects(7, 8),               // State.jump
                makeRects(1, 2, 3, 4),         // State.doubleJump
                makeRects(0),                  // State.falling
                makeRects(9, 10),              // State.slide
                makeRects(503, 504),           // State.hurt
        };
    }
    protected static float[][] edgeInsetRatios = {
            { 0.3f, 0.5f, 0.3f, 0.0f }, // State.running
            { 0.3f, 0.6f, 0.3f, 0.0f }, // State.jump
            { 0.3f, 0.6f, 0.3f, 0.0f }, // State.doubleJump
            { 0.3f, 0.5f, 0.3f, 0.0f }, // State.falling
            { 0.2f, 0.75f, 0.2f, 0.0f }, // State.slide
            { 0.3f, 0.50f, 0.4f, 0.0f }, // State.hurt
    };
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
    public Player(int cookieId) {
        super(0, 8);
        loadSheetFromAsset(cookieId);
        cookieInfo = cookieInfoMap.get(cookieId);
        setPosition(200f, 200f, NORMAL_COOKIE_DST_SIZE, NORMAL_COOKIE_DST_SIZE);
        setState(State.running);
    }
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

    private void setState(State state) {
        this.state = state;
        srcRects = srcRectsArray[state.ordinal()];
        updateCollisionRect();
    }

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
    public void hurt(Obstacle obstacle) {
        if (state == State.hurt) return;
        Sound.playEffect(R.raw.hurt);
        setState(State.hurt);
        this.obstacle = obstacle;
    }
}
