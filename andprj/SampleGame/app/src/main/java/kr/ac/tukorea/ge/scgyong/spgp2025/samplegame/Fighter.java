package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

public class Fighter {
    private static final String TAG = Fighter.class.getSimpleName();
    private final Bitmap bitmap;
    private final RectF dstRect = new RectF();

    public Fighter(Bitmap bitmap) {
        this.bitmap = bitmap;
        setPosition(5.0f, 12.0f);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public void setPosition(float x, float y) {
        float r = 1.25f;
        dstRect.set(x-r, y-r, x+r, y+r);
        //Log.d(TAG, "x=" + x + " y=" + y + " rect=" + dstRect);
    }
}
