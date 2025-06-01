package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class Explosion extends AnimSprite implements IRecyclable {
    public Explosion() {
        super(R.mipmap.explosion, 20);
    }
    public static Explosion get(float x, float y, float radius) {
        return Scene.top().getRecyclable(Explosion.class).init(x, y, radius);
    }

    private Explosion init(float x, float y, float radius) {
        setPosition(x, y, radius);
        createdOn = System.currentTimeMillis();
        return this;
    }

    @Override
    public void update() {
        super.update();
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        if (time > 19.0f/20.0f) {
            Scene.top().remove(MainScene.Layer.explosion, this);
        }
    }

    @Override
    public void onRecycle() {

    }
}
