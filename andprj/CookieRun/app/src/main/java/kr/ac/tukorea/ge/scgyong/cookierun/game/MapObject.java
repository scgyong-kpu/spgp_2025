package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ILayerProvider;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class MapObject extends Sprite implements IRecyclable, IBoxCollidable, ILayerProvider<MainScene.Layer> {
    public static final float SPEED = -300f;
    private final MainScene.Layer layer;
    protected RectF collisionRect;
    public MapObject(MainScene.Layer layer) {
        super(0);
        this.layer = layer;
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
    protected void updateCollisionRect(float inset) {
        updateCollisionRect(inset, inset, inset, inset);
    }

    protected void updateCollisionRect(float left, float top, float right, float bottom) {
        collisionRect.set(
                dstRect.left + width * left,
                dstRect.top + height * top,
                dstRect.right - width * right,
                dstRect.bottom - height * bottom);
    }

    public MainScene.Layer getLayer() {
        return layer;
    }
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
    public void pause() {
    }
    public void resume() {
    }
    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }
    @Override
    public void onRecycle() {
    }
}
