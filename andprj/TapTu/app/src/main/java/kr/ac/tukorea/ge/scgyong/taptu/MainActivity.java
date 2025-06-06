package kr.ac.tukorea.ge.scgyong.taptu;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import kr.ac.tukorea.ge.scgyong.taptu.data.Song;
import kr.ac.tukorea.ge.scgyong.taptu.data.SongLoader;
import kr.ac.tukorea.ge.scgyong.taptu.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private @NonNull ActivityMainBinding ui;
    private ArrayList<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        songs = new SongLoader(this).loadSongs();

        TextView tv = new TextView(this);
        tv.setText(songs.get(0).title);
        ui.songsListView.addView(tv);

        //Caused by: java.lang.UnsupportedOperationException: addView(View) is not supported in AdapterView
        //at android.widget.AdapterView.addView(AdapterView.java:489)
        //at kr.ac.tukorea.ge.scgyong.taptu.MainActivity.onCreate(MainActivity.java:40)
        //at android.app.Activity.performCreate(Activity.java:8051)
        //at ...
    }
}