package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.BuildConfig;
import kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.R;


// MainScene이라는 게임 씬을 정의하고 있습니다.
// 이 씬은 주로 게임 화면에 표시되는 객체들을 설정하고, 터치 이벤트를 처리하는 역할
//
 // GameView에는 View 관련 내용만 남겨두고 GameObject와 관련된 것은 모두 Scene으로 만들어서 분리한다

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();

    private Fighter fighter;
    //  게임에서 주인공이 되는 Fighter 객체입니다. fighter는 조이스틱의 입력을 받아서 움직이는 캐릭터
    private JoyStick joyStick;
    // 사용자가 게임 캐릭터를 조작할 수 있게 해주는 조이스틱입니다.
    // 조이스틱은 화면에서 사용자의 터치 입력을 받아 캐릭터의 이동을 제어하는 역할


    public MainScene() {

        Metrics.setGameSize(900, 1600);
        // 게임 화면의 크기를 설정합니다. 화면의 크기는 가로 900px, 세로 1600px로 설정됩니다.

        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
        // 디버그 모드에서 화면에 디버깅 정보를 표시할지 여부를 설정합니다.
        // BuildConfig.DEBUG는 디버깅 빌드를 사용 중이면 true로 설정됩니다.
        // -> framework module로 분리되어야하기 때문에, GameView에서 BuildConfig를 참조하지 않도록 한다.

        for (int i = 0; i < 5; i++) {
            add(new BouncingCircle());
        }
        for (int i = 0; i < 10; i++) {
            add(Ball.random());
        }

        // 조이스틱 객체를 생성하고 설정 (배경, 썸, 위치, 크기)
        // move_radius: 조이스틱의 이동 범위
        joyStick = new JoyStick(
                R.mipmap.joystick_bg, R.mipmap.joystick_thumb,
                200, 1400,  200, 60,
                150
        );

        // 주인공 캐릭터인 Fighter를 생성하여 조이스틱을 전달
        fighter = new Fighter(joyStick);

        // Fighter와 JoyStick을 씬에 추가
        add(fighter);
        add(joyStick);

        // 추가된 순서대로 그리므로, 비행기를 가장 나중에 그리고 싶다면 가장 나중에 넣어야 한다.
        // 이 문제는 나중에 Object Layering으로 해결한다.
    }


    // 터치 이벤트 처리 (onTouchEvent(MotionEvent event))
    // -> 사용자가 화면을 터치할 때 호출되는 메소드
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 터치가 시작된 지점을 감지합니다. ACTION_DOWN은 사용자가 화면을 처음 터치했을 때 발생하는 이벤트이다.

            // 터치가 시작되면 화면 좌표를 게임 좌표로 변환
            // 터치된 화면 좌표 (event.getX(), event.getY())를 게임 좌표로 변환합니다.
            // 게임 좌표와 화면 좌표는 다를 수 있기 때문에 변환이 필요합니다.
            float[] pts = Metrics.fromScreen(event.getX(), event.getY());
            float x = pts[0], y = pts[1];

            // 변환된 좌표가 (0,0)에서 (100,100) 영역에 있으면,
            // SubScene을 호출하여 새로운 씬을 시작합니다.
            if (x < 100 && y < 100) {
                new SubScene().push();
                return false;
            }
        }

        // 조이스틱 터치 이벤트 처리
        // 만약 위 조건이 충족되지 않으면, 조이스틱에 대한 터치 이벤트를 처리합니다.
        // 이 부분은 사용자가 조이스틱을 터치하거나 드래그할 때 캐릭터의 이동을 처리합니다.
        return joyStick.onTouch(event);
    }
}
