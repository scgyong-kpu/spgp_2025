package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class AnimObstacle extends Obstacle {
    public static Obstacle get(int type, float left, float top) {
        return Scene.top().getRecyclable(AnimObstacle.class).init(type, left, top);
    }
    private static final float FPS = 8.0f;
    private int resIndex;
    private float time = 0;
    private static final int[][] RES_ID_ARRAYS = new int[][] {
            new int[] {
                    R.mipmap.epn01_tm01_jp1up_01,
                    R.mipmap.epn01_tm01_jp1up_02,
                    R.mipmap.epn01_tm01_jp1up_03,
                    R.mipmap.epn01_tm01_jp1up_04,
            },
            new int[]{
                    R.mipmap.epn01_tm01_jp2up_01,
                    R.mipmap.epn01_tm01_jp2up_02,
                    R.mipmap.epn01_tm01_jp2up_03,
                    R.mipmap.epn01_tm01_jp2up_04,
                    R.mipmap.epn01_tm01_jp2up_05,
            },
    };
    private static final float[][] COLLISION_INSETS_ARRAYS = {
            { 0.2f, 0.6f, 0.2f, 0.0f },
            { 0.2f, 0.4f, 0.2f, 0.0f },
    };
    private Obstacle init(int type, float left, float top) {
        resIndex = type;
        time = 0;
        setImageResourceId(RES_ID_ARRAYS[resIndex][0]);
        setObstaclePosition(left, top);
        setImageResourceId(R.mipmap.trans_00p);
        collisionInsets = COLLISION_INSETS_ARRAYS[resIndex];
        return this;
    }

    @Override
    public void update() {
        super.update();
        if (dstRect.left >= 1000f) return;
        // x 좌표가 어느정도 줄었을 때에만 시간합산을 진행한다
        // x 는 사용하지 않으니 dstRect.left 로 체크한다

        time += GameView.frameTime;
        int[] resIds = RES_ID_ARRAYS[resIndex];
        int frameIndex = Math.round(time * FPS);
        if (frameIndex >= resIds.length) {
            frameIndex = resIds.length - 1; // 마지막 프레임으로 유지한다.
        }
        int resId = resIds[frameIndex];
        bitmap = BitmapPool.get(resId);
    }
}

