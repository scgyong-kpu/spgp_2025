package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.app;

import android.os.Bundle;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.activity.GameActivity;
import kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game.MainScene;

public class SampleGameActivity extends GameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MainScene().push();
    }
}
