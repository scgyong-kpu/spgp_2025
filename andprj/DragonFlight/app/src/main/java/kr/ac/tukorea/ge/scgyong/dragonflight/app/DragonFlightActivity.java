package kr.ac.tukorea.ge.scgyong.dragonflight.app;

import android.os.Bundle;

import kr.ac.tukorea.ge.scgyong.dragonflight.BuildConfig;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class DragonFlightActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameView.drawsDebugStuffs = true;
        super.onCreate(savedInstanceState);
    }
}