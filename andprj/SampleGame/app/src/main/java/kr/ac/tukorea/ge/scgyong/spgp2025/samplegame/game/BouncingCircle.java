package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import kr.ac.tukorea.ge.scgyong.spgp2025.framework.view.GameView;
import kr.ac.tukorea.ge.scgyong.spgp2025.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.scgyong.spgp2025.framework.view.Metrics;

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

    // 중력받는 공아 아래로 떨어지다가 바닥에 닿으면 튕기고, 느려지면 다시 위로 확 튀게
    public void update() {
        this.y += this.speed * GameView.frameTime;
        //Log.d(BouncingCircle.class.getSimpleName(), "Speed=" + speed);

        // Metrics.height: 화면 높이
        // 공이 아래로 떨어지고(speed > 0), 바닥(y >= Metrics.height)에 닿았을 때 튕기도록 체크
        // => 안로이드 좌표계는 y값이 커질 수록 아래로 내려간다.
        // speed > 0이면 → y가 커진다 → 아래로 떨어지는 중
        //speed < 0이면 → y가 작아진다 → 위로 올라가는 중
        // Metrics.height는 화면 아래쪽 끝의 y 좌표야
        if (speed > 0 && y >= Metrics.height) { // bounce
            // 반사: 속도를 반대로 해서 위로 튀게 함 (0.8배로 에너지 손실 표현)
            speed = -speed * 0.8f;
            if (Math.abs(speed) < 20f) {
                // 튕기는 힘이 너무 작으면 (절댓값이 20 이하) → 그냥 다시 강하게 위로 점프하도록 재설정
                //-2500 ~ -1500 범위로 위로 튀게 함 (speed가 음수면 위 방향)
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
