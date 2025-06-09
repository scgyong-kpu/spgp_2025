package kr.ac.tukorea.ge.scgyong.taptu.data;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import kr.ac.tukorea.ge.scgyong.taptu.R;

public class Song {
    private static final String TAG = Song.class.getSimpleName();
    public int rank;
    public String title;
    public String artist;
    public String album;
    public int demoStart, demoEnd;
    private MediaPlayer mediaPlayer;
    //public String thumbnail;

    protected static Handler handler = new Handler();
    public static ArrayList<Song> songs;
    public static int selectedIndex;


    @NonNull
    @Override
    public String toString() {
        return "<" + rank + ">" + title + "/" + artist;
    }

    public Bitmap getThumbnailBitmap(Context context) {
        try {
            String filename = String.format(Locale.ENGLISH, "thumbnails/cover_%03d.jpg", this.rank);
            AssetManager assets = context.getAssets();
            return BitmapFactory.decodeStream(assets.open(filename));
        } catch (Exception e) {
            Resources res = context.getResources();
            return BitmapFactory.decodeResource(res, R.mipmap.default_thumbnail);
        }
    }

    private void prepareMediaPlayer(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        String filename = String.format(Locale.ENGLISH, "mp3/s%03d.mp3", rank);
        AssetFileDescriptor afd = assetManager.openFd(filename);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(afd);
        mediaPlayer.prepare();
    }
    public void play(Context context) {
        stop();
        try {
            prepareMediaPlayer(context);
            mediaPlayer.start();
            Log.d(TAG, "Play: " + this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void playDemo(Context context) {
        try {
            prepareMediaPlayer(context);
            if (demoStart > 0) {
                MediaPlayer mp = mediaPlayer;
                mp.seekTo(demoStart);
                handler.postDelayed(()->{
                    mp.stop();
                    if (mp == mediaPlayer) {
                        mediaPlayer = null;
                    }
                }, demoEnd - demoStart);
            }
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            Log.d(TAG, "Stopping " + this);
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    public void pause() {
        if (mediaPlayer == null) return;
        mediaPlayer.pause();
    }

    public void resume() {
        if (mediaPlayer == null) return;
        mediaPlayer.start();
    }
}
