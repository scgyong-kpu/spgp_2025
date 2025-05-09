package kr.ac.tukorea.ge.scgyong.cookierun.app;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.scgyong.cookierun.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding ui;
    private int stage, cookieIndex;
    private static final int[] COOKIE_IDS = {
            107566, 107567, 107568, 107571, 107583,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        setStage(1);
        setCookieIndex(0);

        //if (BuildConfig.DEBUG) {
        //    startActivity(new Intent(this, CookieRunActivity.class));
        //}
    }

    public void onBtnStartGame(View view) {
        startGame();
    }

    private void startGame() {
        Intent intent = new Intent(this, CookieRunActivity.class);
        intent.putExtra(CookieRunActivity.KEY_STAGE, stage);
        intent.putExtra(CookieRunActivity.KEY_COOKIE_ID, COOKIE_IDS[cookieIndex]);
        startActivity(intent);
    }

    private void setStage(int stage) {
        this.stage = stage;
        String text = getString(R.string.title_stage_fmt, stage);
        ui.stageTextView.setText(text);
        ui.prevButton.setEnabled(stage > 1);
        ui.nextButton.setEnabled(stage < 3);
    }
    private void setCookieIndex(int index) {
        this.cookieIndex = index;
        try {
            int cookieId = COOKIE_IDS[index];
            AssetManager assets = getAssets();
            String fileName = "cookies/" + cookieId + "_icon.png";
            InputStream is = assets.open(fileName);
            Bitmap bmp = BitmapFactory.decodeStream(is);
            ui.cookieImageView.setImageBitmap(bmp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ui.prevCookieButton.setEnabled(index > 0);
        ui.nextCookieButton.setEnabled(index < COOKIE_IDS.length - 1);
    }
    public void onBtnPrevious(View view) {
        setStage(stage - 1);
    }
    public void onBtnNext(View view) {
        setStage(stage + 1);
    }
    public void onBtnPreviousCookie(View view) {
        setCookieIndex(cookieIndex - 1);
    }
    public void onBtnNextCookie(View view) {
        setCookieIndex(cookieIndex + 1);
    }
}