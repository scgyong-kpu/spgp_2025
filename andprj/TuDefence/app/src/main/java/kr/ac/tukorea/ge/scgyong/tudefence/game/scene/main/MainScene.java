package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();

    enum Layer {
        bg, enemy, cannon, shell, controller,
    }
    public MainScene() {
        initLayers(Layer.values().length);
        add(Layer.bg, new TiledBackground("map/desert.tmj", 100, 100));
        add(Layer.controller, new WaveGen(this, 2.0f));
        add(Layer.cannon, new Cannon(1, 400, 600));
        add(Layer.cannon, new Cannon(2, 1500, 500));
        add(Layer.cannon, new Cannon(5, 700, 1600));
        add(Layer.cannon, new Cannon(10, 2600, 800));
    }

    @Override
    public boolean onBackPressed() {
        new PauseScene().push();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action != MotionEvent.ACTION_DOWN) return false;
        float[] pts = Metrics.fromScreen(event.getX(), event.getY());
        int x = (int)(pts[0] / 100);
        int y = (int)(pts[1] / 100);
        Log.d(TAG, "Snapped XY: (" + x + "," + y + ")");
        return true;
    }
}
