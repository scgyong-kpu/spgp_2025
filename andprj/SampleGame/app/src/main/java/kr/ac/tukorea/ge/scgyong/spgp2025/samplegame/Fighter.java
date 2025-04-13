package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

public class Fighter implements IGameObject {
    private static final String TAG = Fighter.class.getSimpleName();
    private static final float SPEED = 800f;
    private final Bitmap bitmap;
    private float x, y, angle;
    private float tx, ty, dx, dy;
    private final RectF dstRect = new RectF();

    public Fighter() {
        Resources res = GameView.view.getResources();
        bitmap = BitmapPool.get(R.mipmap.plane_240);
        float x = Metrics.width / 2;
        float y = 2 * Metrics.height / 3;
        setPosition(x, y);
        tx = x;
        ty = y;
    }

    public void update() {
        if (tx == x && ty == y) { return; }
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
        this.dx = SPEED * (float) Math.cos(radian);
        this.dy = SPEED * (float) Math.sin(radian);
    }
    public void setPosition(float x, float y) {
        float r = 125f;
        dstRect.set(x-r, y-r, x+r, y+r);
        this.x = x;
        this.y = y;
        //Log.d(TAG, "x=" + x + " y=" + y + " rect=" + dstRect);
    }
}
