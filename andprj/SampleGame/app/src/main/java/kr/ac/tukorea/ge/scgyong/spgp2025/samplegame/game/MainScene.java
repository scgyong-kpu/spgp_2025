package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.BuildConfig;
import kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.R;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private Fighter fighter;
    private JoyStick joyStick;

    public MainScene() {
        Metrics.setGameSize(900, 1600);
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;

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
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float[] pts = Metrics.fromScreen(event.getX(), event.getY());
            float x = pts[0], y = pts[1];
            if (x < 100 && y < 100) {
                new SubScene().push();
                return false;
            }
        }
        return joyStick.onTouch(event);
    }
}
