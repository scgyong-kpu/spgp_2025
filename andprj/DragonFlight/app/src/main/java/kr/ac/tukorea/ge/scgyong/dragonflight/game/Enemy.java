package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Enemy extends AnimSprite {
    private static final float SPEED = 300f;
    private static final float RADIUS = 90f;
    private static final int[] resIds = {
            R.mipmap.enemy_01, R.mipmap.enemy_02, R.mipmap.enemy_03, R.mipmap.enemy_04, R.mipmap.enemy_05,
            R.mipmap.enemy_06, R.mipmap.enemy_07, R.mipmap.enemy_08, R.mipmap.enemy_09, R.mipmap.enemy_10,
            R.mipmap.enemy_11, R.mipmap.enemy_12, R.mipmap.enemy_13, R.mipmap.enemy_14, R.mipmap.enemy_15,
            R.mipmap.enemy_16, R.mipmap.enemy_17, R.mipmap.enemy_18, R.mipmap.enemy_19, R.mipmap.enemy_20,
    };
    public static final int MAX_LEVEL = resIds.length - 1;
    private Enemy(int level, int index) {
        super(resIds[level], 10);
        setPosition(Metrics.width / 10 * (2 * index + 1), -RADIUS, RADIUS);
        dy = SPEED;
    }
    public static Enemy get(int level, int index) {
        return new Enemy(level, index);
    }
    @Override
    public void update() {
        super.update();
        if (dstRect.top > Metrics.height) {
            Scene.top().remove(this);
        }
    }
}
