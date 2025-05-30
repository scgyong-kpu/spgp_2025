package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;

public class MapSelector extends Sprite {
    private static final String TAG = MapSelector.class.getSimpleName();
    private final MainScene scene;

    public MapSelector(MainScene scene) {
        super(R.mipmap.selection);
        this.scene = scene;
        setPosition(-100, -100, 200, 200); // 임시 위치
    }
    private void hideSelector() {
        setPosition(-100, -100);
    }

    public boolean onTouch(int action, float x, float y) {
        Cannon cannon = findCannonAt(x, y);
        if (cannon != null) {
            Log.d(TAG, "Found: " + cannon);
            if (action == MotionEvent.ACTION_UP) {
                cannon.upgrade();
            } else {
                setPosition(cannon.getX(), cannon.getY());
            }
            return true;
        }
        int mapX = (int)(x / 100);
        int mapY = (int)(y / 100);
        float cx = (mapX + 1) * 100;
        float cy = (mapY + 1) * 100;

        if (intersectsIfInstalledAt(cx, cy)) {
            return false;
        }
        setPosition(cx, cy);

        boolean possible = scene.tiledBg.canInstallAt(mapX, mapY);
        if (!possible) {
            hideSelector();
        }
        if (!possible || action != MotionEvent.ACTION_UP) {
            return true;
        }
        cannon = new Cannon(1, cx, cy);
        scene.add(MainScene.Layer.cannon, cannon);
        return true;
    }
    private Cannon findCannonAt(float x, float y) {
        for (IGameObject obj: scene.objectsAt(MainScene.Layer.cannon)) {
            Cannon cannon = (Cannon) obj;
            if (cannon.containsPoint(x, y)) {
                return cannon;
            }
        }
        return null;
    }
    private boolean intersectsIfInstalledAt(float x, float y) {
        for (IGameObject obj: scene.objectsAt(MainScene.Layer.cannon)) {
            Cannon cannon = (Cannon) obj;
            if (cannon.intersectsIfInstalledAt(x, y)) {
                Log.d(TAG, "Intersects with: " + cannon);
                return true;
            }
        }
        return false;
    }
}
