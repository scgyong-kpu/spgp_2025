package kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.util.Log;

import java.util.ArrayList;

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
    }

    public int count() {
        return gameObjects.size();
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
    }

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
