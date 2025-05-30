package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    protected final DesertMapBg tiledBg;
    protected final MapSelector mapSelector;


    enum Layer {
        bg, enemy, cannon, shell, selection, controller,
    }
    public MainScene() {
        initLayers(Layer.values().length);
        tiledBg = new DesertMapBg();
        add(Layer.bg, tiledBg);
        add(Layer.selection, mapSelector = new MapSelector(this));
        add(Layer.controller, new WaveGen(this));


        add(Layer.cannon, new Cannon(2, 1500, 500));
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
