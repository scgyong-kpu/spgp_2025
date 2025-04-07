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
        setPosition(5.0f, 12.0f, false);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }

    public void setPosition(float x, float y) {
        setPosition(x, y, true);
    }
    public void setPosition(float x, float y, boolean appliesAngle) {
        if (appliesAngle) {
            float dx = x - this.x;
            float dy = y - this.y;
            double radian = Math.atan2(dy, dx);
            angle = (float) Math.toDegrees(radian) + 90;
            //Log.d(TAG, "angle=" + angle);
        }

        float r = 1.25f;
        dstRect.set(x-r, y-r, x+r, y+r);
        this.x = x;
        this.y = y;
        //Log.d(TAG, "x=" + x + " y=" + y + " rect=" + dstRect);
    }
}
