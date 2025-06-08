package kr.ac.tukorea.ge.scgyong.taptu.app;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import kr.ac.tukorea.ge.scgyong.taptu.R;
import kr.ac.tukorea.ge.scgyong.taptu.data.Song;

public class MainGameActivity extends AppCompatActivity {
    private static final String TAG = MainGameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int songIndex = Song.selectedIndex;
        Song song = Song.songs.get(songIndex);
        Log.d(TAG, "Index=" + songIndex + " song=" + song);
    }
}