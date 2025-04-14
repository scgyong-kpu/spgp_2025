package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;

public class Bullet extends Sprite {
    private static final float BULLET_WIDTH = 68f;
    private static final float BULLET_HEIGHT = BULLET_WIDTH * 40 / 28;
    private static final float SPEED = 2000f;
    public Bullet(float x, float y) {
        super(R.mipmap.laser_1);
        setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        dy = -SPEED;
    }
}
