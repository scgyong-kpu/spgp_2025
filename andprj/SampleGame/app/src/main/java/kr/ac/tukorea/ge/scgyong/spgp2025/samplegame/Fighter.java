package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

public class Fighter {
    private static final String TAG = Fighter.class.getSimpleName();
    private static final float SPEED = 8.0f;
    private final Bitmap bitmap;
    private float x, y, angle;
    private float tx, ty;
    private final RectF dstRect = new RectF();

    public Fighter(Bitmap bitmap) {
        this.bitmap = bitmap;
        setPosition(5.0f, 12.0f);
        tx = x;
        ty = y;
    }

    public void update() {
        if (tx == x && ty == y) { return; }
        double radian = Math.toRadians(angle - 90);
        float dx = SPEED * (float) Math.cos(radian);
        float dy = SPEED * (float) Math.sin(radian);
        float x = this.x + dx * GameView.frameTime;
        float y = this.y + dy * GameView.frameTime;
        if ((dx > 0 && x > tx) || (dx < 0 && x < tx)) {
            x = tx;
        }
        if ((dy > 0 && y > ty) || (dy < 0 && y < ty)) {
            y = ty;
        }
        setPosition(x, y);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }

    public void setTargetPosition(float x, float y) {
        float dx = x - this.x;
        float dy = y - this.y;
        double radian = Math.atan2(dy, dx);
        angle = (float) Math.toDegrees(radian) + 90;
        tx = x;
        ty = y;
    }
    public void setPosition(float x, float y) {
        float r = 1.25f;
        dstRect.set(x-r, y-r, x+r, y+r);
        this.x = x;
        this.y = y;
        //Log.d(TAG, "x=" + x + " y=" + y + " rect=" + dstRect);
    }
}
