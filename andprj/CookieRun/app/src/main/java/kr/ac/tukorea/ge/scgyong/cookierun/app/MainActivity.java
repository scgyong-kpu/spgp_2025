package kr.ac.tukorea.ge.scgyong.cookierun.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.scgyong.cookierun.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding ui;
    private int stage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        setStage(1);

        //if (BuildConfig.DEBUG) {
        //    startActivity(new Intent(this, CookieRunActivity.class));
        //}
    }

    public void onBtnStartGame(View view) {
        startGame();
    }

    private void startGame() {
        Intent intent = new Intent(this, CookieRunActivity.class);
        intent.putExtra("stage", stage);
        startActivity(intent);
    }

    private void setStage(int stage) {
        this.stage = stage;
        String text = getString(R.string.title_stage_fmt, stage);
        ui.stageTextView.setText(text);
        ui.prevButton.setEnabled(stage > 1);
        ui.nextButton.setEnabled(stage < 3);
    }

    public void onBtnPrevious(View view) {
        setStage(stage - 1);
    }
    public void onBtnNext(View view) {
        setStage(stage + 1);
    }
}