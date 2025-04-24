package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;


// 게임에서 화면에 원을 그리며 중력의 영향을 받아 튕기는 공을 구현한 것입니다.
// 공은 떨어지고 튕기면서 속도가 변화하는데, 튕기고 난 후의 속도에 따라 공이 다시 튕겨 올라가거나 멈추게 됩니다.
public class BouncingCircle implements IGameObject {
    private static final float GRAVITY = 1800f;
    // 중력 값으로, 공에 작용하는 중력의 세기를 나타냅니다
    private static Random random = new Random();
    // 난수를 생성하는 Random 객체로, 공의 위치, 속도, 색상 등을 랜덤하게 결정하는 데 사용됩니다.

    private final float x, radius;
    private float speed, y;
    private final Paint paint;
    private final String text;
    private float textOffsetX, textOffsetY;

    public BouncingCircle() {
        this.x = random.nextFloat() * Metrics.width; // 화면 너비 범위 내에서 랜덤한 x값을 설정
        this.y = random.nextFloat() * Metrics.height;

        this.radius = random.nextFloat() * 100 + 100f; // 100 ~ 200
        // 공의 반지름. random.nextFloat() * 100 + 100f로 100~200 범위의 값을 랜덤하게 설정

        this.speed = random.nextFloat() * 1000f - 500f; // -500 ~ +500
        // -500 ~ +500 범위의 난수 값을 할당하여, 공이 위로 올라가거나 아래로 떨어질 수 있도록 합니다

        this.paint = new Paint();
        // 공을 그릴 때 사용할 Paint 객체로, 공의 색상, 테두리 두께, 텍스트 크기 등을 설정

        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(10f);
        this.paint.setColor(Color.rgb(
                random.nextInt(128) + 64,
                random.nextInt(128) + 64,
                random.nextInt(128) + 64
        ));
        this.paint.setTextSize(radius / 2);

        this.text = String.valueOf((int)radius);
        // 공의 반지름을 나타내는 텍스트 값으로, 공의 크기(radius)를 문자열로 저장합니다.

        // 텍스트의 가로 너비
        float textWidth = paint.measureText(text);
        // 텍스트의 세로 높이 정보
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        //float textHeight = fontMetrics.descent - fontMetrics.ascent;

        // 중심을 맞추기 위한 보정값
        this.textOffsetX = -textWidth / 2;
        this.textOffsetY = -(fontMetrics.ascent + fontMetrics.descent) / 2;
        // 텍스트가 공의 중심에 맞게 그려지도록 하기 위한 보정값입니다.
        //
        // textOffsetX는 텍스트의 가로 너비의 절반,
        // textOffsetY는 텍스트 높이를 고려하여 텍스트가 중앙에 위치하도록 설정
    }

    // 중력받는 공아 아래로 떨어지다가 바닥에 닿으면 튕기고, 느려지면 다시 위로 확 튀게
    public void update() {
        this.y += this.speed * GameView.frameTime;
        // 공의 y좌표는 현재 속도(speed)에 프레임 시간(GameView.frameTime)을 곱한 값을 더해 업데이트됩니다.
        // 이는 공이 매 프레임마다 아래로 떨어지거나 위로 올라가는 속도를 계산합니다.

        //Log.d(BouncingCircle.class.getSimpleName(), "Speed=" + speed);

        // Metrics.height: 화면 높이
        // 공이 아래로 떨어지고(speed > 0), 바닥(y >= Metrics.height)에 닿았을 때 튕기도록 체크
        // => 안로이드 좌표계는 y값이 커질 수록 아래로 내려간다.
        //
        // speed > 0이면 → y가 커진다 → 아래로 떨어지는 중
        // speed < 0이면 → y가 작아진다 → 위로 올라가는 중
        //
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

        // 그 후 중력의 영향을 받도록 speed에 중력 값을 더하여, 공이 계속해서 떨어지도록 만듭니다.
        this.speed += GRAVITY * GameView.frameTime;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, paint);
        canvas.drawText(text, x + textOffsetX, y + textOffsetY, paint);
    }
}
