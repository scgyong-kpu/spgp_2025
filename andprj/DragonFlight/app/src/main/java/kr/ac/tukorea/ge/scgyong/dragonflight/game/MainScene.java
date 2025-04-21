package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Fighter fighter;
    private final Score score;

    public enum Layer {
        enemy, bullet, fighter, ui, controller;
        public static final int COUNT = values().length;
    }
    public MainScene() {
        initLayers(Layer.COUNT);

        this.fighter = new Fighter();
        add(Layer.fighter, fighter);

        this.score = new Score(R.mipmap.number_24x32, 850f, 50f, 60f);
        score.setScore(12345);
        add(Layer.ui, score);

        add(Layer.controller, new EnemyGenerator());
    }

    // Overridables

    @Override
    public void update() {
        super.update();
        checkCollision();
    }

    private void checkCollision() {
        ArrayList<IGameObject> enemies = objectsAt(Layer.enemy);
        for (int i1 = enemies.size() - 1; i1 >= 0; i1--) {
            Enemy enemy = (Enemy) enemies.get(i1);
            // boolean removed = false;
            ArrayList<IGameObject> bullets = objectsAt(Layer.bullet);
            for (int i2 = bullets.size() - 1; i2 >= 0; i2--) {
                Bullet bullet = (Bullet) bullets.get(i2);
                if (CollisionHelper.collides(enemy, bullet)) {
                    Log.d(TAG, "Collision !! : Bullet@" + System.identityHashCode(bullet) + " vs Enemy@" + System.identityHashCode(enemy));
                    remove(bullet);
                    remove(enemy);
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
