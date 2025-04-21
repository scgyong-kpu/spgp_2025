package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

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
        add(Layer.controller, new CollisionChecker());
    }

    // Overridables

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return fighter.onTouch(event);
    }
}
