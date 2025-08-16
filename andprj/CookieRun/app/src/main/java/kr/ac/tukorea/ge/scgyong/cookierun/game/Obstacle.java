package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.RectF;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class Obstacle extends MapObject {

    // 원본 이미지보다 25% 확대해서 실제 표시 크기를 설정함
    protected static final float IMAGE_SIZE_RATIO = 1.25f;
    protected float[] collisionInsets = { 0.2f, 0.2f, 0.2f, 0.0f };
    // 충돌 사각형을 원본보다 살짝 작게 만들기 위한 여백 설정
    //→ {left, top, right, bottom} 방향으로 각각 20%씩 줄임

    public Obstacle() {
        super(MainScene.Layer.obstacle);
        setImageResourceId(R.mipmap.epn01_tm01_jp1a);
        collisionRect = new RectF();
    }

    public static Obstacle get(float left, float top) {
        return Scene.top().getRecyclable(Obstacle.class).init(left, top);
    }

    private Obstacle init(float left, float top) {
        setObstaclePosition(left, top);
        return this;
    }

    protected void setObstaclePosition(float left, float top) {
        float cx = left + 50, bottom = top + 100;
        width = bitmap.getWidth() * IMAGE_SIZE_RATIO;
        height = bitmap.getHeight() * IMAGE_SIZE_RATIO;
        float half_w = width / 2;
        dstRect.set(cx - half_w, bottom - height, cx + half_w, bottom);
        //Log.d("Obs", "dstRect="+dstRect);
    }

    @Override
    public void update() {
        super.update();
        updateCollisionRect(collisionInsets[0], collisionInsets[1], collisionInsets[2], collisionInsets[3]);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }
}
