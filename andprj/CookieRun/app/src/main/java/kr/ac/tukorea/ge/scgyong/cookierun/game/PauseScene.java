package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.app.AlertDialog;
import android.content.DialogInterface;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

// 일시정지 화면 구성 및 UI 버튼 배치
//
//배경과 타이틀 이미지 표시
//‘재개(Resume)’ 버튼과 ‘종료(Exit)’ 버튼 구현
//일시정지 씬이 투명하게 아래 씬이 약간 보이도록 설정


public class PauseScene extends Scene {
    public enum Layer {
        bg, title, touch
        // 배경(bg), 제목(title), 터치 가능한 UI(touch) 레이어로 구성
    }
    protected float angle = -(float)Math.PI / 2;
    public PauseScene() {
        initLayers(Layer.values().length);
        // 씬 레이어 초기화

        float w = Metrics.width, h = Metrics.height;

        // 배경 이미지 2개 추가 (반투명 배경 + 도시 풍경)
        add(Layer.bg, new Sprite(R.mipmap.trans_50b, w/2, h/2, w, h));
        add(Layer.bg, new Sprite(R.mipmap.bg_city_landscape, w/2, h/2, 1200f, 675f));

        // 타이틀 이미지 추가 (일정한 궤도를 따라 움직임)
        // 타이틀 스프라이트가 좌우와 상하로 부드럽게 움직이도록 angle 기반 위치 조정
        add(Layer.title, new Sprite(R.mipmap.cookie_run_title, w/2, h/2, 369f, 136f) {
            @Override
            public void update() {
                super.update();
                angle -= (float) (GameView.frameTime * Math.PI / 4);
                float x = (float) (800f + 400f * Math.cos(angle));
                float y = (float) (450f + 200f * Math.sin(angle));
                setPosition(x, y, width, height);
            }
        });

        // 재개(Resume) 버튼 추가
        //
        //위치: 오른쪽 위 (1450, 100)
        //눌리면 pop() 호출 → 현재 씬 종료하고 이전 씬으로 돌아감
        add(Layer.touch, new Button(R.mipmap.btn_resume_n, 1450f, 100f, 200f, 75f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                pop();
                return false;
            }
        }));

        // 위치: 중앙 아래 (800, 550)
        //눌리면 AlertDialog 띄워서 확인 후 종료 처리 (popAll() → 모든 씬 종료)
        add(Layer.touch, new Button(R.mipmap.btn_exit_n, 800f, 550f, 267f, 100f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                new AlertDialog.Builder(GameView.view.getContext())
                        .setTitle("Confirm")
                        .setMessage("Do you really want to exit the game?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                popAll();
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        }));
    }

    // 터치 입력 처리를 터치 레이어에서만 하도록 지정
    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    // Overridables
    // 이 씬이 투명하다고 알려줌
    // → 이 씬 아래에 있는 씬도 일부 보임 (일시정지 시 배경이 흐릿하게 보이도록)
    @Override
    public boolean isTransparent() {
        return true;
    }
}
