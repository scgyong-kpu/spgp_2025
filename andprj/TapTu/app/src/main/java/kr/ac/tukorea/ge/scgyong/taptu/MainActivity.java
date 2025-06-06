package kr.ac.tukorea.ge.scgyong.taptu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import kr.ac.tukorea.ge.scgyong.taptu.data.Song;
import kr.ac.tukorea.ge.scgyong.taptu.data.SongLoader;
import kr.ac.tukorea.ge.scgyong.taptu.databinding.ActivityMainBinding;
import kr.ac.tukorea.ge.scgyong.taptu.databinding.SongItemBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
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

        ui.songsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ui.songsRecyclerView.setAdapter(new SongAdapter());
    }

    private class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

        class SongViewHolder extends RecyclerView.ViewHolder {
            SongItemBinding binding;

            SongViewHolder(SongItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            void bind(Song song) {
                binding.title.setText(song.title);
                binding.artist.setText(song.artist);
                binding.album.setText(song.album);
                try {
                    String filename = String.format(Locale.ENGLISH, "thumbnails/cover_%03d.jpg", song.rank);
                    Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open(filename));
                    binding.thumbnail.setImageBitmap(bitmap);
                } catch (Exception e) {
                    binding.thumbnail.setImageResource(R.mipmap.default_thumbnail);
                }
            }
        }

        @NonNull
        @Override
        public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d(TAG, "onCreateViewHolder");
            SongItemBinding binding = SongItemBinding.inflate(getLayoutInflater(), parent, false);
            return new SongViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
            Log.v(TAG, "onBindViewHolder(" + position + ")");
            holder.bind(songs.get(position));
        }

        @Override
        public int getItemCount() {
            return songs.size();
        }
    }
}
