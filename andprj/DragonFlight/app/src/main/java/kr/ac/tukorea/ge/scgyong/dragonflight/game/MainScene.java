package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Fighter fighter;
    public enum Layer {
        enemy, bullet, fighter, controller;
        public static final int COUNT = values().length;
    }
    public MainScene() {
        initLayers(Layer.COUNT);

        this.fighter = new Fighter();
        add(Layer.fighter.ordinal(), fighter);
        add(Layer.controller.ordinal(), new EnemyGenerator());
    }

    // Overridables

    @Override
    public void update() {
        super.update();
        checkCollision();
    }

    private void checkCollision() {
        ArrayList<IGameObject> enemies = getLayer(Layer.enemy.ordinal());
        for (int i1 = enemies.size() - 1; i1 >= 0; i1--) {
            Enemy enemy = (Enemy) enemies.get(i1);
            // boolean removed = false;
            ArrayList<IGameObject> bullets = getLayer(Layer.bullet.ordinal());
            for (int i2 = bullets.size() - 1; i2 >= 0; i2--) {
                Bullet bullet = (Bullet) bullets.get(i2);
                if (CollisionHelper.collides(enemy, bullet)) {
                    Log.d(TAG, "Collision !! : Bullet@" + System.identityHashCode(bullet) + " vs Enemy@" + System.identityHashCode(enemy));
                    remove(Layer.bullet.ordinal(), bullet);
                    remove(Layer.enemy.ordinal(), enemy);
                    // removed = true;
                    break;
                }
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return fighter.onTouch(event);
    }
}
