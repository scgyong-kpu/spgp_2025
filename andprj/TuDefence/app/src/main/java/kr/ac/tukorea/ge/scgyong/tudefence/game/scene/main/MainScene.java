package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.tudefence.game.map.MapLayer;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
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
        add(Layer.controller, new WaveGen(this, 2.0f));
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
            cannon.upgrade();
            return false;
        }
        int mapX = (int)(pts[0] / 100);
        int mapY = (int)(pts[1] / 100);
        boolean possible = tiledBg.canInstallAt(mapX, mapY);
        if (!possible) return false;

        float cx = (mapX + 1) * 100;
        float cy = (mapY + 1) * 100;
        if (intersectsIfInstalledAt(cx, cy)) {
            return false;
        }
        cannon = new Cannon(1, cx, cy);
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
    private boolean intersectsIfInstalledAt(float x, float y) {
        for (IGameObject obj: objectsAt(Layer.cannon)) {
            Cannon cannon = (Cannon) obj;
            if (cannon.intersectsIfInstalledAt(x, y)) {
                Log.d(TAG, "Intersects with: " + cannon);
                return true;
            }
        }
        return false;    }
}
