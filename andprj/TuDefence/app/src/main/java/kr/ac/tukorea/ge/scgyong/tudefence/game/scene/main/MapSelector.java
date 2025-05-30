package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Arrays;

import kr.ac.tukorea.ge.scgyong.tudefence.BuildConfig;
import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MapSelector extends Sprite {
    private static final String TAG = MapSelector.class.getSimpleName();
    private static final float TILE_SIZE = 100;
    private static final float SELECTOR_SIZE = 2 * TILE_SIZE;
    private final MainScene scene;
    private static final int[] MENU_ITEMS_BLANK = {};
    private static final int[] MENU_ITEMS_INSTALL = {
            R.mipmap.f_01_01, R.mipmap.f_02_01, R.mipmap.f_03_01,
    };
    private static final int[] MENU_ITEMS_CANNON = {
            R.mipmap.upgrade, R.mipmap.uninstall,
    };
    private int[] menuItems = MENU_ITEMS_BLANK;
    private final Bitmap menuBgBitmap;

    public MapSelector(MainScene scene) {
        super(R.mipmap.selection);
        this.scene = scene;
        menuBgBitmap = BitmapPool.get(R.mipmap.menu_bg);
        setPosition(-SELECTOR_SIZE, -SELECTOR_SIZE, SELECTOR_SIZE, SELECTOR_SIZE);
    }
    private void hideSelector() {
        setPosition(-SELECTOR_SIZE, -SELECTOR_SIZE);
        // 시작 위치가 -SELECTOR_SIZE, -SELECTOR_SIZE 이면 보이지 않는다.
        // 보여줄 지 여부를 member 로 가지는 방법도 있지만
        // 그 경우 여부에 따라 보여주거나 안 보여주는 코드를 작성해야 하므로
        // 이 방법을 선택해 본다.
    }

    private final RectF menuRect = new RectF();
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        menuRect.set(dstRect);
        float right = dstRect.right + SELECTOR_SIZE * menuItems.length;
        if (right > Metrics.width) {
            menuRect.offset(-SELECTOR_SIZE * (menuItems.length + 1), 0);
        }
        for (int item: menuItems) {
            menuRect.offset(SELECTOR_SIZE, 0);
            canvas.drawBitmap(menuBgBitmap, null, menuRect, null);
            Bitmap itemBitmap = BitmapPool.get(item);
            canvas.drawBitmap(itemBitmap, null, menuRect, null);
        }
    }

    public boolean onTouch(int action, float x, float y) {
        Cannon cannon = findCannonAt(x, y);
        if (cannon != null) {
            Log.d(TAG, "Found: " + cannon);
            if (action == MotionEvent.ACTION_UP) {
                setMenuItems(MENU_ITEMS_CANNON);
            } else {
                bitmap = BitmapPool.get(R.mipmap.selection);
                setPosition(cannon.getX(), cannon.getY());
                setMenuItems(MENU_ITEMS_BLANK);
            }
            return true;
        }
        int mapX = (int)(x / TILE_SIZE);
        int mapY = (int)(y / TILE_SIZE);
        float cx = (mapX + 1) * TILE_SIZE;
        float cy = (mapY + 1) * TILE_SIZE;

        setPosition(cx, cy);

        boolean possible = !intersectsIfInstalledAt(cx, cy) && scene.tiledBg.canInstallAt(mapX, mapY);
        int resId = possible ? R.mipmap.selection : R.mipmap.sel_non_installable;
        bitmap = BitmapPool.get(resId);

        if (action != MotionEvent.ACTION_UP) {
            setMenuItems(MENU_ITEMS_BLANK);
            return true;
        }
        if (!possible) {
            hideSelector();
            return true;
        }
        setMenuItems(MENU_ITEMS_INSTALL);
        return true;
    }
    private void setMenuItems(int... items) {
        menuItems = items;
        if (BuildConfig.DEBUG) {
            // 문자열 생성 비용이 있는 로그들은 BuildConfig.DEBUG 로 감싸는 것이 유리하다
            Log.d(TAG, "Items = " + Arrays.toString(items));
        }
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
