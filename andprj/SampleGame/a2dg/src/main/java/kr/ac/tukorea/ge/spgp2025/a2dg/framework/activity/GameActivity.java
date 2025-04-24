package kr.ac.tukorea.ge.spgp2025.a2dg.framework.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

// GameActivity 코드는 액티비티가 시작될 때 gameView를 화면에 설정하고,
// 해당 뷰가 정상적으로 이벤트를 받을 수 있는 상태로 만들어주는 흐름
public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);
        // setContentView()로 액티비티의 루트 뷰로 설정해.
        //이후 모든 터치 이벤트, 키 이벤트 등은 GameView로 전달될 수 있음.
        //즉, GameView는 화면을 그릴 수 있고, 사용자와 상호작용할 수 있는 상태야.

        setFullScreen();

        // gameView가 갖고 있는 stack이 비면 GameActivity를 finish 할 수 있는 구조 만들기
        gameView.setEmptyStackListener(new GameView.OnEmptyStackListener() {
            @Override
            public void onEmptyStack() {
                finish();
            }
            //
            // emptyStackListener.onEmptyStack(); // <- 콜백 호출!
            //      GameView가 어떤 일이 끝났을 때 emptyStackListener에게 알려줘!
            //      → 마치 "야 나 지금 스택 비었어! 이제 뭐할까?" 라고 호출하는 느낌.
            //
            // 여기서 onEmptyStack()이 호출되면 → GameActivity는 자기 자신을 finish()로 종료함.
            //→ 즉, GameView가 자기 일을 다 하면 GameActivity도 함께 종료되는 구조야.
        });

        getOnBackPressedDispatcher().addCallback(onBackPressedCallback);
    }

    // deprecated( 중요도가 떨어져 더 이상 사용되지 않고 앞으로는 사라지게 될 (컴퓨터 시스템 기능 등) )
    // 된 onBackPressed 대신 OnBackPressedCallBack을 사용하는 것으로 변경
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            gameView.onBackPressed();
            // 안드로이드에서 뒤로가기 버튼을 눌렀을 때, gameView.onBackPressed()로 이벤트를 넘김.
            //
            //GameView가 씬을 pop하거나 다른 처리 로직을 담당할 수 있음.
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