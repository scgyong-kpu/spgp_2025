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
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;

public class Scene {
    private static final String TAG = Scene.class.getSimpleName();
    protected final ArrayList<IGameObject> gameObjects = new ArrayList<>();

    //////////////////////////////////////////////////
    // Game Object Management
    public void add(IGameObject gameObject) {
        gameObjects.add(gameObject);
        //Log.d(TAG, gameObjects.size() + " objects in " + this);
    }

    public void remove(IGameObject gobj) {
        gameObjects.remove(gobj);
        if (gobj instanceof IRecyclable) {
            collectRecyclable((IRecyclable) gobj);
            ((IRecyclable) gobj).onRecycle();
        }
    }

    public int count() {
        return gameObjects.size();
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

    public IRecyclable getRecyclable(Class clazz) {
        ArrayList<IRecyclable> bin = recycleBin.get(clazz);
        if (bin == null) return null;
        if (bin.size() == 0) return null;
        // Log.d(TAG, "get(): " + clazz.getSimpleName() + " : " + (bin.size() - 1) + " objects");
        return bin.remove(0);
    }

    //////////////////////////////////////////////////
    // Game Loop Functions

    public void update() {
        int count = gameObjects.size();
        for (int i = count - 1; i >= 0; i--) {
            IGameObject gobj = gameObjects.get(i);
            gobj.update();
        }
    }
    public void draw(Canvas canvas) {
        for (IGameObject gobj : gameObjects) {
            gobj.draw(canvas);
        }
        if (GameView.drawsDebugStuffs) {
            if (bboxPaint == null) {
                bboxPaint = new Paint();
                bboxPaint.setStyle(Paint.Style.STROKE);
                bboxPaint.setColor(Color.RED);
            }
            for (IGameObject gobj : gameObjects) {
                if (gobj instanceof IBoxCollidable) {
                    RectF rect = ((IBoxCollidable) gobj).getCollisionRect();
                    canvas.drawRect(rect, bboxPaint);
                }
            }
        }
    }
    protected static Paint bboxPaint;

    //////////////////////////////////////////////////
    // Scene Stack Functions

    public void push() {
        GameView.view.pushScene(this);
    }
    public static Scene pop() {
        return GameView.view.popScene();
    }
    public static Scene top() {
        return GameView.view.getTopScene();
    }

    //////////////////////////////////////////////////
    // Overridables
    public boolean onTouchEvent(MotionEvent event) {
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
}
