package kr.ac.tukorea.ge.scgyong.cookierun.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import kr.ac.tukorea.ge.scgyong.cookierun.BuildConfig;
import kr.ac.tukorea.ge.scgyong.cookierun.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnStartGame(View view) {
        startGame(1);
    }

    private void startGame(int stage) {
        Intent intent = new Intent(this, CookieRunActivity.class);
        startActivity(intent);
    }
}