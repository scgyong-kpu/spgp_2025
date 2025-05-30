package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.tudefence.game.map.MapLayer;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final DesertMapBg tiledBg;

    enum Layer {
        bg, enemy, cannon, shell, controller,
    }
    public MainScene() {
        initLayers(Layer.values().length);
        tiledBg = new DesertMapBg();
        add(Layer.bg, tiledBg);
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
        Cannon cannon = findCannonAt(pts[0], pts[1]);
        if (cannon != null) {
            Log.d(TAG, "Found: " + cannon);
            return false;
        }
        int mapX = (int)(pts[0] / 100);
        int mapY = (int)(pts[1] / 100);
        boolean possible = tiledBg.canInstallAt(mapX, mapY);
        if (!possible) return false;
        cannon = new Cannon(1, (mapX + 1) * 100, (mapY + 1) * 100);
        add(Layer.cannon, cannon);
        return true;
    }

    private Cannon findCannonAt(float x, float y) {
        for (IGameObject obj: objectsAt(Layer.cannon)) {
            Cannon cannon = (Cannon) obj;
            if (cannon.containsPoint(x, y)) {
                return cannon;
            }
        }
        return null;
    }
}
