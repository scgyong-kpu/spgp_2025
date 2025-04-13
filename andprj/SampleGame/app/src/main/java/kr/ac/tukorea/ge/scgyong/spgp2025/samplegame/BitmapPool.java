package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.HashMap;

public class BitmapPool {
    private static final String TAG = BitmapPool.class.getSimpleName();
    private static final HashMap<Integer, Bitmap> bitmaps = new HashMap<>();

    public static Bitmap get(int mipmapResId) {
        Bitmap bitmap = bitmaps.get(mipmapResId);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(GameView.view.getResources(), mipmapResId);
            Log.d(TAG, "Bitmap ID " + mipmapResId + " : " + bitmap.getWidth() + "x" + bitmap.getHeight());
            bitmaps.put(mipmapResId, bitmap);
        }
        return bitmap;
    }
}
