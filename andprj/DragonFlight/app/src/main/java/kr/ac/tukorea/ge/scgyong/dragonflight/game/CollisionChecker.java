package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    @Override
    public void update() {
        Scene scene = Scene.top();
        if (scene == null) return;

        ArrayList<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);
        for (int e = enemies.size() - 1; e >= 0; e--) {
            Enemy enemy = (Enemy)enemies.get(e);
            ArrayList<IGameObject> bullets = scene.objectsAt(MainScene.Layer.bullet);
            for (int b = bullets.size() - 1; b >= 0; b--) {
                Bullet bullet = (Bullet)bullets.get(b);
                if (CollisionHelper.collides(enemy, bullet)) {
                    Log.d(TAG, "Collision !! : Bullet@" + System.identityHashCode(bullet) + " vs Enemy@" + System.identityHashCode(enemy));
                    scene.remove(bullet);
                    scene.remove(enemy);
//                    removed = true;
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
