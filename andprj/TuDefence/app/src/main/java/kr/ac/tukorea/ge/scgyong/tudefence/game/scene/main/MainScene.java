package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.scgyong.tudefence.game.scene.pause.PauseScene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    protected final DesertMapBg tiledBg;
    protected final MapSelector mapSelector;
    protected final Score score;


    enum Layer {
        bg, enemy, cannon, shell, explosion, score, selection, controller,
    }
    public MainScene() {
        initLayers(Layer.values().length);
        tiledBg = new DesertMapBg();
        add(Layer.bg, tiledBg);
        add(Layer.selection, mapSelector = new MapSelector(this));
        add(Layer.controller, new WaveGen(this));

        score = new Score(R.mipmap.gold_number, Metrics.width - 50, 50, 100);
        score.setScore(30);
        add(Layer.score, score);
    }

    @Override
    public boolean onBackPressed() {
        new PauseScene().push();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float[] pts = Metrics.fromScreen(event.getX(), event.getY());
        return mapSelector.onTouch(action, pts[0], pts[1]);
    }
}
