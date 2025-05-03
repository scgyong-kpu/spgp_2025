package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class AnimObstacle extends Obstacle {
    public static Obstacle get(int type, float left, float top) {
        return Scene.top().getRecyclable(AnimObstacle.class).init(type, left, top);
    }

    protected static int[] RES_IDS = {
            R.mipmap.epn01_tm01_jp1up_03,
            R.mipmap.epn01_tm01_jp2up_03,
    };
    private Obstacle init(int type, float left, float top) {
        bitmap = BitmapPool.get(RES_IDS[type]);
        setObstaclePosition(left, top);
        return this;
    }
}

