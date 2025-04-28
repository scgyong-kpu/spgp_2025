package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.graphics.RectF;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ILayerProvider;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class Bullet extends Sprite implements IRecyclable, IBoxCollidable, ILayerProvider<MainScene.Layer> {
    private static final float BULLET_WIDTH = 68f;
    private static final float BULLET_HEIGHT = BULLET_WIDTH * 40 / 28;
    private static final float SPEED = 2000f;
    public Bullet() {
        super(R.mipmap.laser_1);
        dy = -SPEED;
    }

    private Bullet init(float x, float y) {
        setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        return this;
    }
    public static Bullet get(float x, float y) {
        Bullet bullet = Scene.top().getRecyclable(Bullet.class);
        bullet.init(x, y);
        return bullet;
    }
    @Override
    public void update() {
        super.update();
        if (dstRect.bottom < 0) {
            Scene.top().remove(this);
        }
    }

    public int getPower() {
        return 10;
    }
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void onRecycle() {
    }

    @Override
    public MainScene.Layer getLayer() {
        return MainScene.Layer.bullet;
    }
}
