package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Fighter fighter;
    private final Score score;

    public enum Layer {
        bg1, enemy, bullet, fighter, bg2, ui, controller;
        public static final int COUNT = values().length;
    }
    public MainScene() {
        //Metrics.setGameSize(900, 1600); default=900x1600
        initLayers(Layer.COUNT);

        add(Layer.bg1, new VertScrollBackground(R.mipmap.bg_city, 20));
        add(Layer.bg2, new VertScrollBackground(R.mipmap.clouds, 40));

        this.fighter = new Fighter();
        add(Layer.fighter, fighter);

        this.score = new Score(R.mipmap.number_24x32, 850f, 50f, 60f);
        score.setScore(0);
        add(Layer.ui, score);

        add(Layer.controller, new EnemyGenerator(this));
        add(Layer.controller, new CollisionChecker(this));
    }
    public void addScore(int amount) {
        score.add(amount);
    }
    public int getScore() {
        return score.getScore();
    }

    // Overridables

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return fighter.onTouch(event);
    }
}
