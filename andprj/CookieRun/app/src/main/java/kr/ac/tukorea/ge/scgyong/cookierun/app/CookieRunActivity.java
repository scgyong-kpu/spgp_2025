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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
        Metrics.setGameSize(1600, 900);
        super.onCreate(savedInstanceState);
        int stage = getIntent().getIntExtra("stage", 1);
        Log.d(CookieRunActivity.class.getSimpleName(), "Stage = " + stage);
        //new MainScene().push();
        new Scene().push();
    }
}