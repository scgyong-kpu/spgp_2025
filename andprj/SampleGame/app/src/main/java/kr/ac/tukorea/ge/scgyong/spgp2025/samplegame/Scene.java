package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.util.Log;

import java.util.ArrayList;

public class Scene {
    private static final String TAG = Scene.class.getSimpleName();
    protected final ArrayList<IGameObject> gameObjects = new ArrayList<>();

    //////////////////////////////////////////////////
    // Game Object Management
    public void add(IGameObject gameObject) {
        gameObjects.add(gameObject);
    }

//    Process: kr.ac.tukorea.ge.scgyong.spgp2025.samplegame, PID: 8452
//    java.util.ConcurrentModificationException
//        at java.util.ArrayList$Itr.next(ArrayList.java:860)
//        at kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.Scene.update(Scene.java:23)
//        at kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.GameView.update(GameView.java:159)
//        at kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.GameView.doFrame(GameView.java:147)
//        at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1035)
//        at android.view.Choreographer.doCallbacks(Choreographer.java:845)
//        at android.view.Choreographer.doFrame(Choreographer.java:775)
//        at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1022)
//        at android.os.Handler.handleCallback(Handler.java:938)
//        at android.os.Handler.dispatchMessage(Handler.java:99)
//        at android.os.Looper.loopOnce(Looper.java:201)
//        at android.os.Looper.loop(Looper.java:288)
//        at android.app.ActivityThread.main(ActivityThread.java:7839)
//        at java.lang.reflect.Method.invoke(Native Method)
//        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:548)
//        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1003)
//
    //////////////////////////////////////////////////
    // Game Loop Functions

    public void update() {
        for (IGameObject gobj : gameObjects) {
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
