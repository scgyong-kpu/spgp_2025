package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class FallingObstacle extends Obstacle {
    private static final int RES_ID = R.mipmap.epn01_tm01_sda;
    private static final float FALL_SPEED = 500f;
    private float destTop = 0;

    public FallingObstacle() {
        setImageResourceId(RES_ID);
    }

    public static Obstacle get(float left, float top) {
        return Scene.top().getRecyclable(FallingObstacle.class).init(left, top);
    }

    private Obstacle init(float left, float top) {
        setObstaclePosition(left, top);
        destTop = dstRect.top - 100; // slide 할 공간을 마련해 주기 위해 100 올린다.
        dstRect.offset(0, -dstRect.height());
        return this;
    }

    @Override
    public void update() {
        super.update();
        if (dstRect.left >= 1000f) return;

        float dy = FALL_SPEED * GameView.frameTime;
        if (dy > this.destTop - dstRect.top) {
            dy = this.destTop - dstRect.top;
        }
        if (dy == 0) return;

        dstRect.offset(0, dy);
    }
}
