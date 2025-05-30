package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;

public class MapSelector extends Sprite {
    private static final String TAG = MapSelector.class.getSimpleName();
    private final MainScene scene;

    public MapSelector(MainScene scene) {
        super(R.mipmap.selection);
        this.scene = scene;
        setPosition(-100, -100, 200, 200);
    }
    private void hideSelector() {
        setPosition(-100, -100);
        // 시작 위치가 -100, -100 이면 보이지 않는다.
        // 보여줄 지 여부를 member 로 가지는 방법도 있지만
        // 그 경우 여부에 따라 보여주거나 안 보여주는 코드를 작성해야 하므로
        // 이 방법을 선택해 본다.
    }

    public boolean onTouch(int action, float x, float y) {
        Cannon cannon = findCannonAt(x, y);
        if (cannon != null) {
            Log.d(TAG, "Found: " + cannon);
            if (action == MotionEvent.ACTION_UP) {
                cannon.upgrade();
            } else {
                bitmap = BitmapPool.get(R.mipmap.selection);
                setPosition(cannon.getX(), cannon.getY());
            }
            return true;
        }
        int mapX = (int)(x / 100);
        int mapY = (int)(y / 100);
        float cx = (mapX + 1) * 100;
        float cy = (mapY + 1) * 100;

        setPosition(cx, cy);

        boolean possible = !intersectsIfInstalledAt(cx, cy) && scene.tiledBg.canInstallAt(mapX, mapY);
        int resId = possible ? R.mipmap.selection : R.mipmap.sel_non_installable;
        bitmap = BitmapPool.get(resId);
        if (action != MotionEvent.ACTION_UP) {
            return true;
        }
        if (!possible) {
            hideSelector();
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
