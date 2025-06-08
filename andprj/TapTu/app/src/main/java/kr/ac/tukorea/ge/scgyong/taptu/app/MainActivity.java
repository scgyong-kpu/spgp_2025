package kr.ac.tukorea.ge.scgyong.taptu.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.ac.tukorea.ge.scgyong.taptu.R;
import kr.ac.tukorea.ge.scgyong.taptu.data.Song;
import kr.ac.tukorea.ge.scgyong.taptu.data.SongLoader;
import kr.ac.tukorea.ge.scgyong.taptu.databinding.ActivityMainBinding;
import kr.ac.tukorea.ge.scgyong.taptu.databinding.SongItemBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private @NonNull ActivityMainBinding ui;
    private ArrayList<Song> songs;
    private final SongAdapter adapter = new SongAdapter();
    private int selectedPosition = RecyclerView.NO_POSITION;

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
        ui.songsRecyclerView.setAdapter(adapter);

        // 구분선 추가
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        ui.songsRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

        class SongViewHolder extends RecyclerView.ViewHolder {
            SongItemBinding binding;

            SongViewHolder(SongItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

                binding.getRoot().setOnClickListener(v -> {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        onItemClick(songs.get(pos), pos);
                    }
                });
            }

            void bind(Song song) {
                binding.title.setText(song.title);
                binding.artist.setText(song.artist);
                binding.album.setText(song.album);
                //Context context = MainActivity.this;
                Context context = binding.thumbnail.getContext();
                Bitmap bitmap = song.getThumbnailBitmap(context);
                binding.thumbnail.setImageBitmap(bitmap);

                boolean selected = getAdapterPosition() == selectedPosition;
                binding.getRoot().setSelected(selected);
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

    private void onItemClick(Song song, int pos) {
        int prevPosition = selectedPosition;
        if (prevPosition != pos) {
            selectedPosition = pos;
        } else {
            selectedPosition = RecyclerView.NO_POSITION;
        }

        if (prevPosition != RecyclerView.NO_POSITION) {
            adapter.notifyItemChanged(prevPosition);
            Song prevSong = songs.get(prevPosition);
            prevSong.stop();
        }
        if (selectedPosition != RecyclerView.NO_POSITION) {
            adapter.notifyItemChanged(selectedPosition);
            song.playDemo(this);
        }
        ui.startButton.setEnabled(selectedPosition != RecyclerView.NO_POSITION);

        if (selectedPosition != RecyclerView.NO_POSITION) {
            Log.d(TAG, "Song selected: " + pos + " = " + song);
        } else {
            Log.i(TAG, "No Song selected");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (selectedPosition != RecyclerView.NO_POSITION) {
            Song prevSong = songs.get(selectedPosition);
            prevSong.stop();
        }
    }

    @Override
    protected void onResume() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            onItemClick(null, selectedPosition);
        }
        super.onResume();
    }

    public void onBtnStartGame(View view) {
        Intent intent = new Intent(this, MainGameActivity.class);
        startActivity(intent);
    }
}
