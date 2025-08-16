package kr.ac.tukorea.ge.scgyong.cookierun.app;

import android.os.Bundle;
import android.util.Log;

import kr.ac.tukorea.ge.scgyong.cookierun.BuildConfig;
import kr.ac.tukorea.ge.scgyong.cookierun.game.MainScene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;


//  안드로이드 게임 앱에서 실제 게임 플레이 화면을 담당하는 Activity 클래스
public class CookieRunActivity extends GameActivity {

    public static final String KEY_STAGE = "stage";
    public static final String KEY_COOKIE_ID = "cookieId";
    // Intent를 통해 전달된 값(스테이지 번호와 쿠키 ID)을 받을 때 사용할 키


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
        Metrics.setGameSize(1600, 900);
        super.onCreate(savedInstanceState);

        // 이전 액티비티(MainActivity)에서 전달된 선택 정보를 받아옵니다.
        // 디폴트 값은 stage = 1, cookieId = 107566로 설정되어 있습니다.
        int stage = getIntent().getIntExtra(KEY_STAGE, 1);
        int cookieId = getIntent().getIntExtra(KEY_COOKIE_ID, 107566);

        Log.d(CookieRunActivity.class.getSimpleName(), "Stage = " + stage + " cookieId = " + cookieId);

        // push()를 호출해 해당 씬을 씬 스택에 넣고 화면을 전환
        new MainScene(stage, cookieId).push();
//        new Scene().push();
    }
}