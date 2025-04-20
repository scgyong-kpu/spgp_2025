package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import java.util.ArrayList;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class Bullet extends Sprite {
    private static final float BULLET_WIDTH = 68f;
    private static final float BULLET_HEIGHT = BULLET_WIDTH * 40 / 28;
    private static final float SPEED = 2000f;
    protected static ArrayList<Bullet> objPool = new ArrayList<>();
    private Bullet(float x, float y) {
        super(R.mipmap.laser_1);
        setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        dy = -SPEED;
    }
    public static Bullet get(float x, float y) {
        if (!objPool.isEmpty()) {
            Bullet bullet = objPool.remove(0);
            bullet.setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
            return bullet;
        }
        return new Bullet(x, y);
    }
    @Override
    public void update() {
        super.update();
        if (dstRect.bottom < 0) {
            Scene.top().remove(this);
            objPool.add(this);
        }
    }
}
