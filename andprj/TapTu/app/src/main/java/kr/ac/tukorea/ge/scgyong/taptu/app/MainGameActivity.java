package kr.ac.tukorea.ge.scgyong.taptu.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import kr.ac.tukorea.ge.scgyong.taptu.R;

public class MainGameActivity extends AppCompatActivity {

    public static final String KEY_SONG_INDEX = "songIndex";
    private static final String TAG = MainGameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int songIndex = extras.getInt(KEY_SONG_INDEX);
        Log.d(TAG, "Song Index = " + songIndex);
    }
}