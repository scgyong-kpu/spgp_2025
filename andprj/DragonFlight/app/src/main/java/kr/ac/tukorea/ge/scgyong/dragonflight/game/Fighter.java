package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Fighter extends Sprite {
    private static final float RADIUS = 100f;
    private static final float SPEED = 300f;
    private float startX;

    public Fighter() {
        super(R.mipmap.fighter);
        setPosition(Metrics.width / 2, Metrics.height - 200, RADIUS);
    }

    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                if (x < startX) {
                    dx = -SPEED;
                } else if (startX < x) {
                    dx = SPEED;
                } else {
                    dx = 0;
                }
                return true;
            case MotionEvent.ACTION_UP:
                dx = 0;
                return true;
        }
        return false;
    }
}
