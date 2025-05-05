package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class Obstacle extends MapObject {

    protected static final float IMAGE_SIZE_RATIO = 1.25f;
    public Obstacle() {
        super(MainScene.Layer.obstacle);
        setImageResourceId(R.mipmap.epn01_tm01_jp1a);
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
        float half_w = bitmap.getWidth() * IMAGE_SIZE_RATIO / 2;
        float h = bitmap.getHeight() * IMAGE_SIZE_RATIO;
        dstRect.set(cx - half_w, bottom - h, cx + half_w, bottom);
        //Log.d("Obs", "dstRect="+dstRect);
    }
}
