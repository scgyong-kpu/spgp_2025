package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.view.MotionEvent;

public class SubScene extends Scene {
    public SubScene() {
        gameObjects.add(new BouncingCircle());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pop();
        return false;
    }
}
