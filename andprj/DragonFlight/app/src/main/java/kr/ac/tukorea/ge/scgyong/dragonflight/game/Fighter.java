package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Fighter extends Sprite {
    private static final float RADIUS = 100f;
    private static final float SPEED = 300f;
    private float targetX;

    private static final float FIRE_INTERVAL = 0.25f;
    private float fireCoolTime = FIRE_INTERVAL;
    public Fighter() {
        super(R.mipmap.fighter);
        setPosition(Metrics.width / 2, Metrics.height - 200, RADIUS);
        targetX = x;
    }

    @Override
    public void update() {
        if (targetX < x) {
            dx = -SPEED;
        } else if (x < targetX) {
            dx = SPEED;
        } else {
            dx = 0;
        }
        super.update();
        float adjx = x;
        if ((dx < 0 && x < targetX) || (dx > 0 && x > targetX)) {
            adjx = targetX;
        } else {
            adjx = Math.max(RADIUS, Math.min(x, Metrics.width - RADIUS));
        }
        if (adjx != x) {
            setPosition(adjx, y, RADIUS);
        }
        fireCoolTime -= GameView.frameTime;
        if (fireCoolTime <= 0) {
            fireBullet();
            fireCoolTime = FIRE_INTERVAL;
        }
    }

    private void fireBullet() {
        Scene.top().add(new Bullet(x, y));
    }

    private void setTargetX(float x) {
        targetX = Math.max(RADIUS, Math.min(x, Metrics.width - RADIUS));
    }
    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                float[] pts = Metrics.fromScreen(event.getX(), event.getY());
                setTargetX(pts[0]);
                return true;

        }
        return false;
    }
}
