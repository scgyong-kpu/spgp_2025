package kr.ac.tukorea.ge.scgyong.tudefence.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import kr.ac.tukorea.ge.scgyong.tudefence.BuildConfig;
import kr.ac.tukorea.ge.scgyong.tudefence.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (BuildConfig.DEBUG) {
            startGameActivity();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startGameActivity();
        }
        return super.onTouchEvent(event);
    }

    private void startGameActivity() {
        Intent intent = new Intent(this, MainGameActivity.class);
        startActivity(intent);
    }
}