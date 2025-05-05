package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.util.Log;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ILayerProvider;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public abstract class MapObject extends Sprite implements IRecyclable, ILayerProvider<MainScene.Layer> {
    public static final float SPEED = -200f;
    public MapObject() {
        super(0);
    }
    private static final String TAG = MapObject.class.getSimpleName();

    @Override
    public void update() {
        float dx = SPEED * GameView.frameTime;
        dstRect.offset(dx, 0);
        if (dstRect.right < 0) {
            //Log.d(TAG, "Removing:" + this);
            removeFromScene();
        }
    }

    // abstract public MainScene.Layer getLayer();
    public void addToScene() {
        Scene scene = Scene.top();
        if (scene == null) {
            Log.e(TAG, "Scene stack is empty in addToScene() " + this.getClass().getSimpleName());
            return;
        }
        scene.add(this);
    }
    public void removeFromScene() {
        Scene scene = Scene.top();
        if (scene == null) {
            Log.e(TAG, "Scene stack is empty in removeFromScene() " + this.getClass().getSimpleName());
            return;
        }
        scene.remove(this);
    }
    @Override
    public void onRecycle() {
    }
}
