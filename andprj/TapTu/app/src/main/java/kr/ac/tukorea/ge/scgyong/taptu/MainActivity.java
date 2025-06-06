package kr.ac.tukorea.ge.scgyong.taptu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import kr.ac.tukorea.ge.scgyong.taptu.data.Song;
import kr.ac.tukorea.ge.scgyong.taptu.data.SongLoader;
import kr.ac.tukorea.ge.scgyong.taptu.databinding.ActivityMainBinding;
import kr.ac.tukorea.ge.scgyong.taptu.databinding.SongItemBinding;

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

        ui.songsListView.setAdapter(adapter);
    }

    private final BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return songs.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            SongItemBinding item = SongItemBinding.inflate(getLayoutInflater());
            Song song = songs.get(i);
            item.title.setText(song.title);
            item.artist.setText(song.artist);
            item.album.setText(song.album);
            try {
                String filename = String.format(Locale.ENGLISH, "thumbnails/cover_%03d.jpg", song.rank);
                Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open(filename));
                item.thumbnail.setImageBitmap(bitmap);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return item.getRoot();
        }
    };
}