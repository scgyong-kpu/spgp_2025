package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.spgp2025.framework.scene.Scene;

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
