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
    private JoyStick joyStick;

    public MainScene() {
        Metrics.setGameSize(900, 1600);

        for (int i = 0; i < 5; i++) {
            add(new BouncingCircle());
        }
        for (int i = 0; i < 10; i++) {
            add(Ball.random());
        }
        joyStick = new JoyStick();
        fighter = new Fighter(joyStick);
        add(fighter);
        add(joyStick);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return joyStick.onTouch(event);
    }
}
