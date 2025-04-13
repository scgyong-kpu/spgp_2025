package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class BouncingCircle implements IGameObject {
    private static final float GRAVITY = 1800f;
    private static Random random = new Random();
    private final float x, radius;
    private float speed, y;
    private final Paint paint;

    public BouncingCircle() {
        this.x = random.nextFloat() * Metrics.SCREEN_WIDTH;
        this.y = random.nextFloat() * Metrics.SCREEN_HEIGHT;
        this.radius = random.nextFloat() * 100 + 100f; // 100 ~ 200
        this.speed = random.nextFloat() * 1000f - 500f; // -500 ~ +500

        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(10f);
        this.paint.setColor(Color.rgb(
                random.nextInt(128) + 64,
                random.nextInt(128) + 64,
                random.nextInt(128) + 64
        ));
        this.paint.setTextSize(radius / 2);
    }

    public void update() {
        this.y += this.speed * GameView.frameTime;
        //Log.d(BouncingCircle.class.getSimpleName(), "Speed=" + speed);
        if (speed > 0 && y >= Metrics.SCREEN_HEIGHT) { // bounce
            speed = -speed * 0.8f;
            if (Math.abs(speed) < 20f) {
                this.speed = random.nextFloat() * 1000f - 2500f; // -2500 ~ -1500
                //Log.d(BouncingCircle.class.getSimpleName(), "Speed=" + speed);
            }
        }
        this.speed += GRAVITY * GameView.frameTime;
    }


    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, paint);
        canvas.drawText("BC", x, y, paint);
    }
}
