package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Rect;

import java.util.ArrayList;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Shell extends Sprite implements IRecyclable {

    public Shell() {
        super(R.mipmap.shells, 0, 0, 50f, 50f);
        srcRect = new Rect();
    }

    public static Shell get(Cannon cannon, Fly target) {
        return Scene.top().getRecyclable(Shell.class).init(cannon, target);
    }

    private Shell init(Cannon cannon, Fly target) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int maxLevel = w / h;
        int level = cannon.level;
        if (level < 1) level = 1;
        if (level > maxLevel) level = maxLevel;
        srcRect.set(h * (level - 1), 0, h * level, h);
        //Log.d("CannonFire", "shell rect: " + srcRect);
        //this.target = target;
        double radian = Math.toRadians(cannon.angle);
        double speed = (level + 10) * 100; // 1100 ~ 2000
        dx = (float) (speed * Math.cos(radian));
        dy = (float) (speed * Math.sin(radian));
        //this.power = level;
        radius = 20f + level * 2f;
        setPosition(cannon.getX(), cannon.getY(), radius);

        return this;
    }

    @Override
    public void update() {
        super.update();
        Scene scene = Scene.top(); // MainScene
        if (x < -radius || x > Metrics.width + radius ||
                y < -radius || y > Metrics.height + radius) {
            //Log.d("CannonFire", "Remove(" + x + "," + y + ") " + this);
            scene.remove(MainScene.Layer.shell, this);
            return;
        }

        ArrayList<IGameObject> flies = scene.objectsAt(MainScene.Layer.enemy);
        for (int index = flies.size() - 1; index >= 0; index--) {
            Fly fly = (Fly) flies.get(index);
            boolean collides = CollisionHelper.collidesRadius(this, fly);
            if (collides) {
                scene.remove(MainScene.Layer.shell, this);
                boolean dead = fly.decreaseLife(10);
                if (dead) {
                    scene.remove(MainScene.Layer.enemy, fly);
                }
                break;
            }
        }
    }

    @Override
    public void onRecycle() {}
}
