package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.spgp2025.framework.Metrics;
import kr.ac.tukorea.ge.scgyong.spgp2025.framework.Scene;
import kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.R;

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
        joyStick = new JoyStick(R.mipmap.joystick_bg, R.mipmap.joystick_thumb, 200, 1400,  200, 60, 150);
        fighter = new Fighter(joyStick);
        add(fighter);
        add(joyStick);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return joyStick.onTouch(event);
    }
}
