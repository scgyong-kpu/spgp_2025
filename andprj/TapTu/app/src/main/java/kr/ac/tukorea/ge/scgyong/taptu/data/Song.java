package kr.ac.tukorea.ge.scgyong.taptu.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import java.util.Locale;

import kr.ac.tukorea.ge.scgyong.taptu.R;

public class Song {
    public int rank;
    public String title;
    public String artist;
    public String album;
    //public String thumbnail;

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
}
