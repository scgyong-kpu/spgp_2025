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
    private final JoyStick joyStick;
    private float x, y, angle;
    private final RectF dstRect = new RectF();

    public Fighter(JoyStick joyStick) {
        this.joyStick = joyStick;
        Resources res = GameView.view.getResources();
        bitmap = BitmapPool.get(R.mipmap.plane_240);
        float x = Metrics.width / 2;
        float y = 2 * Metrics.height / 3;
        setPosition(x, y);
    }

    public void update() {
        if (joyStick.power <= 0) {
            return;
        }
        float distance = SPEED * GameView.frameTime;
        x += (float) (distance * Math.cos(joyStick.angle_radian));
        y += (float) (distance * Math.sin(joyStick.angle_radian));
        setPosition(x, y);
        angle = (float) Math.toDegrees(joyStick.angle_radian) + 90;
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }

    public void setPosition(float x, float y) {
        float r = 125f;
        dstRect.set(x-r, y-r, x+r, y+r);
        this.x = x;
        this.y = y;
        //Log.d(TAG, "x=" + x + " y=" + y + " rect=" + dstRect);
    }
}
