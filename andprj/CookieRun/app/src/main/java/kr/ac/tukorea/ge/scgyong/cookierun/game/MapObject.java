package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ILayerProvider;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;


// MapObject 클래스는 쿠키런 스타일 게임에서 맵에 등장하는
// **오브젝트(예: 발판, 아이템, 장애물 등)**의 공통 부모 역할을 하는 기본 클래스
public class MapObject extends Sprite implements IRecyclable, IBoxCollidable, ILayerProvider<MainScene.Layer> {
    public static final float SPEED = -300f;
    //    왼쪽으로 움직이는 속도 (px/sec)

    private final MainScene.Layer layer;
    // 이 오브젝트가 소속될 레이어 (item, obstacle, floor 등)

    protected RectF collisionRect;


    // 이미지 리소스 ID는 0 (이후 하위 클래스에서 설정)
    //어떤 레이어에 들어갈지도 지정
    public MapObject(MainScene.Layer layer) {
        super(0);
        this.layer = layer;
    }
    private static final String TAG = MapObject.class.getSimpleName();

    @Override
    public void update() {
        float dx = SPEED * GameView.frameTime;
        dstRect.offset(dx, 0);
        if (dstRect.right < 0) {
            //Log.d(TAG, "Removing:" + this);
            removeFromScene();
            // 화면 밖으로 나가면 자동으로 제거
        }
    }

    // 경계 안쪽으로 조금 inset(여백)을 줘서 충돌 영역 설정
    //예) 너무 정확한 충돌은 불합리하니 살짝 작게 잡는 식
    protected void updateCollisionRect(float inset) {
        updateCollisionRect(inset, inset, inset, inset);
    }

    protected void updateCollisionRect(float left, float top, float right, float bottom) {
        collisionRect.set(
                dstRect.left + width * left,
                dstRect.top + height * top,
                dstRect.right - width * right,
                dstRect.bottom - height * bottom);
    }

    public MainScene.Layer getLayer() {
        return layer;
    }

    // 현재 최상위 Scene에 본 객체를 추가/삭제
    // MapLoader나 CollisionChecker 등에서 활용
    public void addToScene() {
        Scene scene = Scene.top();
        if (scene == null) {
            Log.e(TAG, "Scene stack is empty in addToScene() " + this.getClass().getSimpleName());
            return;
        }
        scene.add(this);
    }
    public void removeFromScene() {
        Scene scene = Scene.top();
        if (scene == null) {
            Log.e(TAG, "Scene stack is empty in removeFromScene() " + this.getClass().getSimpleName());
            return;
        }
        scene.remove(this);
    }
    public void pause() {
    }
    public void resume() {
    }

    // 현재 이 오브젝트의 충돌 영역 반환 (기본은 dstRect
    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }
    @Override
    public void onRecycle() {
    }
}
