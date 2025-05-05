package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class FallingObstacle extends Obstacle {
    private static final int RES_ID = R.mipmap.epn01_tm01_sda;
    private float time = 0;

    public FallingObstacle() {
        setImageResourceId(R.mipmap.epn01_tm01_sda);
    }

    public static Obstacle get(float left, float top) {
        return Scene.top().getRecyclable(FallingObstacle.class).init(left, top);
    }

    private Obstacle init(float left, float top) {
        setObstaclePosition(left, top);
        time = 0;
        return this;
    }
}
