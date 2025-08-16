package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.HorzScrollBackground;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.Sound;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    public enum Layer {
        bg, floor, item, obstacle, player, ui, touch, controller;
        public static final int COUNT = values().length;
    }
    private final Player player;
    private static final String TAG = MainScene.class.getSimpleName();
    public MainScene(int stage, int cookieId) {
        initLayers(Layer.COUNT);
        // 전체 레이어 개수만큼 내부 구조 초기화

        // 3개 레이어로 깊이감을 줌 (속도 차이로 패럴럭스 효과)
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_1, -50));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_2, -100f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_3, -150f));

        // cookieId: 캐릭터의 종류를 나타냄
        player = new Player(cookieId);
        add(Layer.player, player);

        // 🔘 버튼 추가 (터치 레이어)
        add(Layer.touch, new Button(R.mipmap.btn_slide_n, 150f, 800f, 200f, 75f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                //Log.d(TAG, "Button: Slide - pressed:" + pressed);
                player.slide(pressed);
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.btn_jump_n, 1450f, 770f, 200f, 75f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                //Log.d(TAG, "Button: Jump");
                player.jump();
                return false;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.btn_fall_n, 1450f, 850f, 200f, 75f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                //Log.d(TAG, "Button: Fall");
                player.fall();
                return false;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.btn_pause, 1500f, 100f, 100f, 100f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                new PauseScene().push();
                return false;
            }
        }));

        // MapLoader: 맵 구성 요소 로드 (배경, 장애물 등)
        // CollisionChecker: 플레이어와 아이템/장애물 충돌 체크
        add(Layer.controller, new MapLoader(this, stage));
        add(Layer.controller, new CollisionChecker(this, player));
    }

    // 장애물 레이어의 모든 MapObject에 대해 일시정지/재시작 처리
    private void pauseAnimations() {
        for (IGameObject obj : objectsAt(Layer.obstacle)) {
            ((MapObject)obj).pause();
        }
    }
    private void resumeAnimations() {
        for (IGameObject obj : objectsAt(Layer.obstacle)) {
            ((MapObject)obj).resume();
        }
    }

    // Overridables


    @Override
    public boolean onBackPressed() {
        new PauseScene().push();
        return true;
    }

    // Scene 내부 터치 처리 시 터치 가능한 객체들만 존재하는 레이어 지정
    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    @Override
    public void onEnter() {
        Sound.playMusic(R.raw.main);
    }

    // 앱이 일시정지되거나 다시 활성화될 때 애니메이션과 사운드 제어
    @Override
    public void onPause() {
        Sound.pauseMusic();
        pauseAnimations();
    }

    @Override
    public void onResume() {
        resumeAnimations();
        Sound.resumeMusic();
    }
    @Override
    public void onExit() {
        Sound.stopMusic();
    }
}
