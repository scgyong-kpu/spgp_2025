package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.app;

import android.os.Bundle;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.activity.GameActivity;
import kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game.MainScene;


// framework의 일부인 GameActivity가 app의 MainScene을 아는 것은 옳지 않다.
// app에서 GameActivity를 상속하여 MainScene을 push 하도록 한다.
public class SampleGameActivity extends GameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MainScene().push();
    }
}
