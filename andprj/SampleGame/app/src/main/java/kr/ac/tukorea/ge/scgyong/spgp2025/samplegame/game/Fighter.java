package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.R;


// 주로 비행기 같은 캐릭터가 화면 상에서 움직이도록 처리하는 클래스입니다.
// 이 클래스는 게임 씬(Scene) 내에서 캐릭터의 위치, 이동, 회전 등을 관리합니다.
// JoyStick 객체를 사용하여 사용자의 입력을 받아 캐릭터의 이동 방향과 속도를 제어합니다.
public class Fighter extends Sprite {
    private static final String TAG = Fighter.class.getSimpleName();
    private static final float SPEED = 800f;
    // 비행기의 이동 속도. 초당 800 유닛의 거리를 이동
    private static final float RADIUS = 125f;
    // 비행기의 충돌 범위 또는 반지름 (게임 내에서 크기와 관련됨).
    // 240x240 크기니까
    private final JoyStick joyStick;
    // 조이스틱을 통해 사용자가 입력하는 방향과 힘을 받아오는 객체.
    private float angle;
    // 비행기가 회전하는 각도 (방향).

    private static final float BULLET_INTERVAL = 1.0f / 3.0f;
    // 총알 발사 간격 (1/3초마다 한 번씩 발사).
    private float bulletCoolTime;
    // 총알 발사 대기 시간.

    // [생성자]
    public Fighter(JoyStick joyStick) {
        super(R.mipmap.plane_240);
        // Sprite 클래스의 생성자를 호출하여 비행기의 이미지(plane_240)를 설정합니다.

        this.joyStick = joyStick;
        // 조이스틱 객체를 받아 joyStick 변수에 저장합니다.

        setPosition(Metrics.width / 2, 2 * Metrics.height / 3, RADIUS);
        // 비행기의 초기 위치를 화면 중간, 세로로 약간 하단에 배치합니다.
        angle = -90;
        // angle = -90: 비행기의 초기 회전 각도를 설정합니다 (상향 방향을 0도로 설정).
    }

    public void update() {
        bulletCoolTime -= GameView.frameTime;
        // 총알 발사 대기 시간이 남은 시간만큼 감소합니다.

        // 대기 시간이 다 되면 총알을 발사하고, 다시 쿨타임을 설정합니다.
        if (bulletCoolTime <= 0) {
            Bullet bullet = new Bullet(x, y, (float) Math.toRadians(angle));
            // x, y 위치에서 angle 각도로 총알을 생성합니다.
            Scene.top().add(bullet);
            // 총알을 현재 씬에 추가합니다.
            bulletCoolTime = BULLET_INTERVAL;
        }

        //  조이스틱의 power가 0 이하라면 이동을 하지 않도록 처리합니다.
        if (joyStick.power <= 0) {
            return;
        }

        // 옵션 1-1 : power 를 적용하지 않는 경우
        //float distance = SPEED * GameView.frameTime;

        // 옵션 1-2 : power 를 적용하는 경우
        // 조이스틱의 힘(joyStick.power)에 따라 이동 거리를 계산합니다.
        float distance = SPEED * joyStick.power * GameView.frameTime;

        // 옵션 2-1 : 8방향인 경우
        // -> 360도가 아닌, 8방향만 사용하는 경우, 각도에 8을 곱하고 2pi로 나누어 반올림한 값을 다시
        // 2pi / 8을 곱하면 된다. 8방향이 아닌 4방향으로 하고자 하면 이 수식의 8 대신 4를 쓰면 되고,
        // 2도 가능하지만 2는 굳이 조이스틱을 쓸 필요가 없다
        //
        //final int way = 8;
        //final double TWO_PI = Math.PI * 2;
        //float eightWayAngle = (float) (Math.round(way * joyStick.angle_radian / TWO_PI) * TWO_PI / way);
        //x += (float) (distance * Math.cos(eightWayAngle));
        //y += (float) (distance * Math.sin(eightWayAngle));
        //setPosition(x, y);
        //angle = (float) Math.toDegrees(eightWayAngle);

        // 옵션 2-2 : 360° 인 경우
        // -> 조이스틱의 방향에 맞게 x 좌표를 계산하여 이동시킵니다.
        // -> y 좌표를 계산하여 이동시킵니다.
        x += (float) (distance * Math.cos(joyStick.angle_radian));
        y += (float) (distance * Math.sin(joyStick.angle_radian));

        setPosition(x, y, RADIUS);
        // 이동 후, 비행기의 새로운 위치를 설정합니다.

        // 비트맵의 크기 2배로 확장
        setScale(2.0f, 2.0f);

        angle = (float) Math.toDegrees(joyStick.angle_radian);
        // 조이스틱의 각도에 맞게 비행기의 회전 각도를 업데이트합니다.
    }

    // 회전해서 그리므로 구현해야 한다
    public void draw(Canvas canvas) {

        canvas.save();
        canvas.rotate(angle + 90, x, y);
        // 비행기를 angle + 90도만큼 회전시킵니다.
        // (이유: 기본적으로 이미지가 위쪽을 향하고 있기 때문에, 90도만큼 회전시켜야 맞는 방향으로 그려집니다.)
        //
         // 회전할 때 각도만 주면 원점 중심으로 회전한다 -> 비행기 중심점을 중심으로 회전해야 하므로
        // 중심점 위치도 준다

        //canvas.drawBitmap(bitmap, null, dstRect, null);
        super.draw(canvas); // 직접 그려도 되고 Sprite의 Draw.(super) 를 불러도 된다.
        canvas.restore();
    }
}
