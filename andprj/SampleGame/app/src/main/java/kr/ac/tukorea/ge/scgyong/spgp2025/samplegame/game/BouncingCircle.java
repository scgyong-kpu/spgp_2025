package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class BouncingCircle implements IGameObject {
    private static final float GRAVITY = 1800f;
    private static Random random = new Random();
    private final float x, radius;
    private float speed, y;
    private final Paint paint;
    private final String text;
    private float textOffsetX, textOffsetY;

    public BouncingCircle() {
        this.x = random.nextFloat() * Metrics.width;
        this.y = random.nextFloat() * Metrics.height;
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

        this.text = String.valueOf((int)radius);
        // 텍스트의 가로 너비
        float textWidth = paint.measureText(text);
        // 텍스트의 세로 높이 정보
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        //float textHeight = fontMetrics.descent - fontMetrics.ascent;

        // 중심을 맞추기 위한 보정값
        this.textOffsetX = -textWidth / 2;
        this.textOffsetY = -(fontMetrics.ascent + fontMetrics.descent) / 2;
    }

    public void update() {
        this.y += this.speed * GameView.frameTime;
        //Log.d(BouncingCircle.class.getSimpleName(), "Speed=" + speed);
        if (speed > 0 && y >= Metrics.height) { // bounce
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
        canvas.drawText(text, x + textOffsetX, y + textOffsetY, paint);
    }
}
