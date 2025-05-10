package kr.ac.tukorea.ge.spgp2025.a2dg.framework.res;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.HashMap;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class BitmapPool {
    private static final String TAG = BitmapPool.class.getSimpleName();
    private static final HashMap<Integer, Bitmap> bitmaps = new HashMap<>();
    private static BitmapFactory.Options opts;
    public static Bitmap get(int mipmapResId) {
        Bitmap bitmap = bitmaps.get(mipmapResId);
        if (bitmap == null) {
            if (opts == null) {
                opts = new BitmapFactory.Options();
                opts.inScaled = false;
            }
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, mipmapResId, opts);
            Log.d(TAG, "Bitmap " + res.getResourceEntryName(mipmapResId) + "(" + mipmapResId + ") : " + bitmap.getWidth() + "x" + bitmap.getHeight());
            bitmaps.put(mipmapResId, bitmap);
        }
        return bitmap;
    }
    public static void clear() {
        opts = null;
        bitmaps.clear();
    }
}
