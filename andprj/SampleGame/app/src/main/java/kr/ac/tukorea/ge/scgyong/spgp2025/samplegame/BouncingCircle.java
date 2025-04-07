package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class BouncingCircle implements IGameObject {
    private static final float GRAVITY = 18.0f;
    private static Random random = new Random();
    private final float x, radius;
    private float speed, y;
    private final Paint paint;

    public BouncingCircle() {
        this.x = random.nextFloat() * GameView.SCREEN_WIDTH;
        this.y = random.nextFloat() * GameView.SCREEN_HEIGHT;
        this.radius = random.nextFloat() + 1.0f; // 1.0 ~ 2.0
        this.speed = random.nextFloat() * 10.0f - 5.0f; // -5.0 ~ +5.0

        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(0.1f);
        this.paint.setColor(Color.rgb(
                random.nextInt(128) + 64,
                random.nextInt(128) + 64,
                random.nextInt(128) + 64
        ));
    }

    public void update() {
        this.y += this.speed * GameView.frameTime;
        //Log.d(BouncingCircle.class.getSimpleName(), "Speed=" + speed);
        if (speed > 0 && y >= GameView.SCREEN_HEIGHT) { // bounce
            speed = -speed * 0.8f;
            if (Math.abs(speed) < 0.2f) {
                this.speed = random.nextFloat() * 10.0f - 25.0f; // -25.0 ~ -15.0
                //Log.d(BouncingCircle.class.getSimpleName(), "Speed=" + speed);
            }
        }
        this.speed += GRAVITY * GameView.frameTime;
    }


    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, paint);
    }
}
