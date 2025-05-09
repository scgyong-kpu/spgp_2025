package kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ILayerProvider;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ITouchable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Scene {
    private static final String TAG = Scene.class.getSimpleName();
    protected ArrayList<ArrayList<IGameObject>> layers = new ArrayList<>();
    //////////////////////////////////////////////////
    // Game Object Management

    protected void initLayers(int layerCount) {
        layers.clear();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
    }
    public <E extends Enum<E>> void add(E layer, IGameObject gameObject) {
        int layerIndex = layer.ordinal();
        ArrayList<IGameObject> gameObjects = layers.get(layerIndex);
        gameObjects.add(gameObject);
    }

    public void add(ILayerProvider<?> gameObject) {
        Enum<?> e = gameObject.getLayer();
        int layerIndex = e.ordinal();
        ArrayList<IGameObject> gameObjects = layers.get(layerIndex);
        gameObjects.add(gameObject);
    }

    public <E extends Enum<E>> void remove(E layer, IGameObject gobj) {
        int layerIndex = layer.ordinal();
        remove(layerIndex, gobj);
    }

    public void remove(ILayerProvider<?> gameObject) {
        Enum<?> e = gameObject.getLayer();
        int layerIndex = e.ordinal();
        remove(layerIndex, gameObject);
    }

    private void remove(int layerIndex, IGameObject gobj) {
        ArrayList<IGameObject> gameObjects = layers.get(layerIndex);
        gameObjects.remove(gobj);
        if (gobj instanceof IRecyclable) {
            collectRecyclable((IRecyclable) gobj);
            ((IRecyclable) gobj).onRecycle();
        }
    }

    public <E extends Enum<E>> ArrayList<IGameObject> objectsAt(E layer) {
        int layerIndex = layer.ordinal();
        return layers.get(layerIndex);
    }

    public int count() {
        int total = 0;
        for (ArrayList<IGameObject> layer : layers) {
            total += layer.size();
        }
        return total;
    }
    public <E extends Enum<E>> int countAt(E layer) {
        int layerIndex = layer.ordinal();
        return layers.get(layerIndex).size();
    }


    public String getDebugCounts() {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<IGameObject> gameObjects : layers) {
            if (sb.length() == 0) {
                sb.append('[');
            } else {
                sb.append(',');
            }
            sb.append(gameObjects.size());
        }
        sb.append(']');
        return sb.toString();
    }

    //////////////////////////////////////////////////
    // Object Recycling
    protected HashMap<Class, ArrayList<IRecyclable>> recycleBin = new HashMap<>();
    public void collectRecyclable(IRecyclable object) {
        Class clazz = object.getClass();
        ArrayList<IRecyclable> bin = recycleBin.get(clazz);
        if (bin == null) {
            bin = new ArrayList<>();
            recycleBin.put(clazz, bin);
        }
        //object.onRecycle(); // 객체가 재활용통에 들어가기 전에 정리해야 할 것이 있다면 여기서 한다
        bin.add(object);
        // Log.d(TAG, "collect(): " + clazz.getSimpleName() + " : " + bin.size() + " objects");
    }

    public <T extends IRecyclable> T getRecyclable(Class<T> clazz) {
        ArrayList<IRecyclable> bin = recycleBin.get(clazz);
        if (bin == null || bin.isEmpty()) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        // Log.d(TAG, "get(): " + clazz.getSimpleName() + " : " + (bin.size() - 1) + " objects");
        return clazz.cast(bin.remove(0));
    }

    //////////////////////////////////////////////////
    // Game Loop Functions

    public void update() {
        for (ArrayList<IGameObject> gameObjects : layers) {
            int count = gameObjects.size();
            for (int i = count - 1; i >= 0; i--) {
                IGameObject gobj = gameObjects.get(i);
                gobj.update();
            }
        }
    }
    public void draw(Canvas canvas) {
        if (this.clipsRect()) {
            canvas.clipRect(0, 0, Metrics.width, Metrics.height);
        }
        for (ArrayList<IGameObject> gameObjects : layers) {
            for (IGameObject gobj : gameObjects) {
                gobj.draw(canvas);
            }
        }
        if (GameView.drawsDebugStuffs) {
            if (bboxPaint == null) {
                bboxPaint = new Paint();
                bboxPaint.setStyle(Paint.Style.STROKE);
                bboxPaint.setColor(Color.RED);
            }
            for (ArrayList<IGameObject> gameObjects : layers) {
                for (IGameObject gobj : gameObjects) {
                    if (gobj instanceof IBoxCollidable) {
                        RectF rect = ((IBoxCollidable) gobj).getCollisionRect();
                        canvas.drawRect(rect, bboxPaint);
                    }
                }
            }
        }
    }
    protected static Paint bboxPaint;

    //////////////////////////////////////////////////
    // Scene Stack Functions

    public void change() {
        GameView.view.changeScene(this);
    }
    public void push() {
        GameView.view.pushScene(this);
    }
    public static Scene pop() {
        return GameView.view.popScene();
    }
    public static Scene top() {
        return GameView.view.getTopScene();
    }
    public static void popAll() {
        GameView.view.popAllScenes();
    }

    //////////////////////////////////////////////////
    // Overridables
    public boolean isTransparent() {
        return false;
    }
    protected int getTouchLayerIndex() {
        return -1;
    }
    protected ITouchable capturingTouchable;
    public boolean onTouchEvent(MotionEvent event) {
        int touchLayer = getTouchLayerIndex();
        if (touchLayer < 0) return false;
        if (capturingTouchable != null) {
            boolean processed = capturingTouchable.onTouchEvent(event);
            if (!processed || event.getAction() == MotionEvent.ACTION_UP) {
                Log.d(TAG, "Capture End: " + capturingTouchable);
                capturingTouchable = null;
            }
            return processed;
        }
        ArrayList<IGameObject> gameObjects = layers.get(touchLayer);
        for (IGameObject gobj : gameObjects) {
            if (!(gobj instanceof ITouchable)) {
                continue;
            }
            boolean processed = ((ITouchable) gobj).onTouchEvent(event);
            if (processed) {
                capturingTouchable = (ITouchable) gobj;
                Log.d(TAG, "Capture Start: " + capturingTouchable);
                return true;
            }
        }
        return false;
    }

    public void onEnter() {
        Log.v(TAG, "onEnter: " + getClass().getSimpleName());
    }
    public void onPause() {
        Log.v(TAG, "onPause: " + getClass().getSimpleName());
    }
    public void onResume() {
        Log.v(TAG, "onResume: " + getClass().getSimpleName());
    }
    public void onExit() {
        Log.v(TAG, "onExit: " + getClass().getSimpleName());
    }

    public boolean onBackPressed() {
        return false;
    }
    public boolean clipsRect() {
        return true;
    }
}
