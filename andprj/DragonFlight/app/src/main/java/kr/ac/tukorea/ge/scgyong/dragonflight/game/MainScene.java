package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    private final Fighter fighter;
    private final JoyStick joyStick;

    public MainScene() {
        this.joyStick = new JoyStick(R.mipmap.joystick_bg, R.mipmap.joystick_thumb, 100, 1500, 100, 30, 80);
        this.fighter = new Fighter(joyStick);
        add(fighter);
        add(joyStick);
    }

    // Overridables
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return joyStick.onTouch(event);
    }
}
