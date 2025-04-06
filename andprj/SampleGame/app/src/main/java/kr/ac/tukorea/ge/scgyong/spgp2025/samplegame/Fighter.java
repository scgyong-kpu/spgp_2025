package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

public class Fighter {
    private static final String TAG = Fighter.class.getSimpleName();
    private final Bitmap bitmap;
    private float x, y, angle;
    private final RectF dstRect = new RectF();

    public Fighter(Bitmap bitmap) {
        this.bitmap = bitmap;
        setPositionOnly(5.0f, 12.0f);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle); // intentional bug here
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }

    public void setPositionOnly(float x, float y) {
        float r = 1.25f;
        dstRect.set(x-r, y-r, x+r, y+r);
        this.x = x;
        this.y = y;
        //Log.d(TAG, "x=" + x + " y=" + y + " rect=" + dstRect);
    }
    public void setPosition(float x, float y) {
        float dx = x - this.x;
        float dy = y - this.y;
        double radian = Math.atan2(dy, dx);
        angle = (float) Math.toDegrees(radian); // intentional bug here
        Log.d(TAG, "angle=" + angle);
        setPositionOnly(x, y);
    }
}
