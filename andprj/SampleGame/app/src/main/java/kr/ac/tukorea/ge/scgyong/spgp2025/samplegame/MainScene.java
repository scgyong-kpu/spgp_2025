package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private Fighter fighter;

    public MainScene() {
        //Metrics.setGameSize(1000, 1600);
        //Metrics.setGameSize(700, 1600);
        Metrics.setGameSize(1000, 600);
        fighter = new Fighter();

        for (int i = 0; i < 5; i++) {
            gameObjects.add(new BouncingCircle());
        }
        for (int i = 0; i < 10; i++) {
            gameObjects.add(Ball.random());
        }
        gameObjects.add(fighter);

    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float[] xy = Metrics.fromScreen(event.getX(), event.getY());
                if (xy[0] < 100 && xy[1] < 100) {
                    new SubScene().push();
                    return false;
                }
                fighter.setTargetPosition(xy[0], xy[1]);
                return true;
        }
        return false;
    }
}
