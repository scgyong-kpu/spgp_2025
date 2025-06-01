package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Shell extends Sprite implements IRecyclable {

    private static final String TAG = Shell.class.getSimpleName();

    public Shell() {
        super(R.mipmap.shells, 0, 0, 50f, 50f);
        srcRect = new Rect();
    }

    public static Shell get(Cannon cannon, Fly target) {
        return Scene.top().getRecyclable(Shell.class).init(cannon, target);
    }

    protected float power;
    protected boolean splashes;
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
        this.power = (float) (10 * Math.pow(1.2, level - 1));
        this.splashes = level >= 4;
        // 10.0, 12.0, 14.4, 17.28, 20.736, 24.8832, 29.85984, 35.83181, 42.99817, 51.5978
        radius = 20f + level * 2f;
        setPosition(cannon.getX(), cannon.getY(), radius);

        return this;
    }

    @Override
    public void update() {
        super.update();
        MainScene scene = (MainScene) Scene.top();
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
                hit(fly, power, scene);
                if (splashes) {
                    explode(scene, fly, flies);
                }
                break;
            }
        }
    }

    private void hit(Fly fly, float damage, MainScene scene) {
        Log.d(TAG, "Hit: " + damage + " to:" + fly);
        boolean dead = fly.decreaseLife(damage);
        if (dead) {
            scene.remove(MainScene.Layer.enemy, fly);
            scene.score.add(fly.score());
        }
    }

    private void explode(MainScene scene, Fly flyHit, ArrayList<IGameObject> flies) {
        float fx = flyHit.getX(), fy = flyHit.getY();
        float explosion_radius = 60 + 3 * power;
        Explosion ex = Explosion.get(fx, fy, explosion_radius);
        scene.add(MainScene.Layer.explosion, ex);
        double radius_sq = explosion_radius * explosion_radius;
        Log.v(TAG, "[Explosion");
        for (int index = flies.size() - 1; index >= 0; index--) {
            Fly fly = (Fly) flies.get(index);
            if (fly == flyHit) continue;
            float dx = fx - fly.getX(), dy = fy - fly.getY();
            double dist_sq = dx * dx + dy * dy;
            if (dist_sq >= radius_sq) continue;
            float damage = (float) (power * (1 - dist_sq / radius_sq));
            hit(fly, damage, scene);
        }
        Log.v(TAG, "Explosion]");
    }

    @Override
    public void onRecycle() {}
}
