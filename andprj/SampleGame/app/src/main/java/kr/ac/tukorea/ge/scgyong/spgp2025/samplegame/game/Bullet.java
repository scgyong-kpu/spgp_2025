package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;

public class Bullet implements IGameObject {
    private static final float SPEED = 1000f;
    private static final float RADIUS = 30f;

    private float x, y;
    private final float angle;
    private final Paint paint;

    public Bullet(float x, float y, float angle_radian) {
        this.x = x;
        this.y = y;
        this.angle = angle_radian;
        this.paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
    }

    @Override
    public void update() {
        x += (float) (SPEED * Math.cos(angle) * GameView.frameTime);
        y += (float) (SPEED * Math.sin(angle) * GameView.frameTime);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, RADIUS, paint);
    }
}
