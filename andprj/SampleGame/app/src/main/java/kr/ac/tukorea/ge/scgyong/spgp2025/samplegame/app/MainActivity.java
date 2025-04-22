package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnStartGame(View view) {
        // **Intent**는 "어떤 작업을 하고 싶다"는 의도를 나타내는 객체야.
        // 여기선 SampleGameActivity를 시작하고 싶다는 의도를 만든 거야
        Intent intent = new Intent(this, SampleGameActivity.class);
        startActivity(intent);
    }
}