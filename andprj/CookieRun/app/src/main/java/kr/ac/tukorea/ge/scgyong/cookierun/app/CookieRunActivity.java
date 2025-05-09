package kr.ac.tukorea.ge.scgyong.cookierun.app;

import android.os.Bundle;
import android.util.Log;

import kr.ac.tukorea.ge.scgyong.cookierun.BuildConfig;
import kr.ac.tukorea.ge.scgyong.cookierun.game.MainScene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class CookieRunActivity extends GameActivity {

    public static final String KEY_STAGE = "stage";
    public static final String KEY_COOKIE_ID = "cookieId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
        Metrics.setGameSize(1600, 900);
        super.onCreate(savedInstanceState);
        int stage = getIntent().getIntExtra(KEY_STAGE, 1);
        int cookieId = getIntent().getIntExtra(KEY_COOKIE_ID, 107566);
        Log.d(CookieRunActivity.class.getSimpleName(), "Stage = " + stage + " cookieId = " + cookieId);
        new MainScene(stage, cookieId).push();
//        new Scene().push();
    }
}