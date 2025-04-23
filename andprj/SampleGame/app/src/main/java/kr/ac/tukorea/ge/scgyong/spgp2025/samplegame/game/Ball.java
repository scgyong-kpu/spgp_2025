package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.R;


// 2D 축구공 같은 이미지를 사용해서 화면 안에서 튕기며 움직이는 공을 표현하는 클래스
public class Ball extends Sprite {
    private static final float BALL_RADIUS = 100f; // 공의 크기를 설정. 중심 기준으로 그리기 때문에 반지름
    private static final float SPEED = 700f;
    // 초당 700 unit 을 움직이는 속도. - : 공의 이동 속도. 초당 700 단위 거리
    private static final Random random = new Random();


    // Factory Function
    // 체를 생성하는 함수를 의미합니다. 특히, 객체의 생성 방식을 캡슐화하여 객체 생성 로직을 단순화하고,
    // 복잡한 객체 생성 과정을 숨겨줄 수 있습니다. 일반적으로 객체를 생성할 때 사용하는 생성자와는 달리,
    // 이 함수는 객체를 반환하는 역할만 하며, 객체의 생성 방법을 쉽게 관리할 수 있게 도와줍니다.
    //
    //
    // static: 이 메소드는 클래스 메소드입니다. 즉, 인스턴스화하지 않고 클래스명으로 바로 호출할 수 있습니다.
    public static Ball random() {
        // random()은 화면의 임의 위치와 방향으로 움직이는 공을 하나 만들어주는 static 메서드

        return new Ball(
                random.nextFloat() * Metrics.width, // 화면의 너비 내에서 랜덤한 x 좌표를 생성
                random.nextFloat() * Metrics.height,
                random.nextFloat() * 360
        );
    }

    // 반시계 방향은 맞는데 좌표계 y가 반대라 반대처럼 보인다는거
    public Ball(float centerX, float centerY, float angle_degree) {
        super(R.mipmap.soccer_ball_240); // 공 이미지 불러오기

        setPosition(centerX, centerY, BALL_RADIUS); // 중심 위치, 반지름 설정
        //double radian = Math.PI * angle_degree / 180;
        double radian = Math.toRadians(angle_degree); // 각도(도)를 라디안(실수)으로 변환

        this.dx = SPEED * (float) Math.cos(radian); // X축 속도 계산
        this.dy = SPEED * (float) Math.sin(radian); // Y축 속도 계산
    }

    public void update() {
        super.update();
        // Sprite의 위치 갱신 함수 호출 (기본 이동 처리)


        if (dx > 0) {
            if (dstRect.right > Metrics.width) { // Alt+Enter -> Make GameView.SCREEN_WIDTH public
                dx = -dx;
                // 오른쪽 벽에 부딪힘
            }
        } else {
            if (dstRect.left < 0) {
                dx = -dx;
            }
        }
        if (dy > 0) {
            if (dstRect.bottom > Metrics.height) {
                dy = -dy;
            }
        } else {
            if (dstRect.top < 0) {
                dy = -dy;
            }
        }
    }
}
