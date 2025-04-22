package kr.ac.tukorea.ge.spgp2025.a2dg.framework.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);

        setFullScreen();

        gameView.setEmptyStackListener(new GameView.OnEmptyStackListener() {
            @Override
            public void onEmptyStack() {
                finish();
            }
        });
        getOnBackPressedDispatcher().addCallback(onBackPressedCallback);
    }

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            gameView.onBackPressed();
        }
    };


    // 게임 화면을 '완전한 전체화면'으로 전환해 주는 기능 [ 상태바, 네비바를 숨긴다 ]
    // @SuppressWarnings("deprecation"):
    //옛날 API 방식도 사용하니까, Android Studio가 "이건 옛날 방식이야!" 하고 경고하지 않도록 경고 무시 처리해줌.
    @SuppressWarnings("deprecation")
    public void setFullScreen() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // API 30 이상: 최신 방식

            // getInsetsController()를 통해 시스템 UI를 제어할 수 있어.
            WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {

                // setSystemBarsBehavior(...)는 유저가 스와이프하면 바를 잠깐 보이게 할지 여부를 정함.
                insetsController.setSystemBarsBehavior(
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                );

                // hide(...)는 상단바, 하단바 모두 숨김 처리
                insetsController.hide(WindowInsets.Type.systemBars());
            }
            // 📱 유저는 화면을 아래서 위로 쓸어올리면 네비게이션 바가 잠깐 보였다가 다시 숨겨짐.

        } else {

            // API 29 이하: 기존 방식
            int flags =
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                            // 유저가 스와이프해서 바를 보이면 자동으로 다시 사라짐
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                            // 상단 상태바(Status bar) 숨김
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                            // 하단 내비게이션 바 숨김

            gameView.setSystemUiVisibility(flags);
        }

    }
}