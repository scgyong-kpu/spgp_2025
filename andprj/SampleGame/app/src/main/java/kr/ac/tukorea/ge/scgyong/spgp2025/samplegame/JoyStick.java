package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public class JoyStick implements IGameObject {
    private static final String TAG = JoyStick.class.getSimpleName();
    private final Bitmap bgBitmap;
    private final Bitmap thumbBitmap;

    private static final float CENTER_X = 200f;
    private static final float CENTER_Y = 1400f;
    private static final float BG_RADIUS = 200f;
    private static final float THUMB_RADIUS = 60f;
    private static final float MOVE_RADIUS = BG_RADIUS - THUMB_RADIUS;
    private final RectF bgRect;
    private final RectF thumbRect;

    private boolean visible;
    private float startX, startY;

    public JoyStick() {
        bgBitmap = BitmapPool.get(R.mipmap.joystick_bg);
        thumbBitmap = BitmapPool.get(R.mipmap.joystick_thumb);
        bgRect = RectUtil.newRectF(CENTER_X, CENTER_Y, BG_RADIUS);
        thumbRect = RectUtil.newRectF(CENTER_X, CENTER_Y, THUMB_RADIUS);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        if (!visible) return;
        canvas.drawBitmap(bgBitmap, null, bgRect, null);
        canvas.drawBitmap(thumbBitmap, null, thumbRect, null);
    }

    public boolean onTouch(MotionEvent event) {
        float[] pts;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                visible = true;
                pts = Metrics.fromScreen(event.getX(), event.getY());
                startX = pts[0];
                startY = pts[1];
                RectUtil.setRect(thumbRect, CENTER_X, CENTER_Y, THUMB_RADIUS);

                return true;
            case MotionEvent.ACTION_MOVE:
                pts = Metrics.fromScreen(event.getX(), event.getY());
                float dx = Math.max(-BG_RADIUS, Math.min(pts[0] - startX, BG_RADIUS));
                float dy = Math.max(-BG_RADIUS, Math.min(pts[1] - startY, BG_RADIUS));
                double radius = Math.sqrt(dx * dx + dy * dy);
                if (radius > MOVE_RADIUS) {
                    double radian = Math.atan2(dy, dx);
                    dx = (float) (MOVE_RADIUS * Math.cos(radian));
                    dy = (float) (MOVE_RADIUS * Math.sin(radian));
                }
                float cx = CENTER_X + dx, cy = CENTER_Y + dy;
                Log.d(TAG, "sx="+startX+" sy="+startY+" dx="+dx + " dy="+dy);
                thumbRect.set(cx - THUMB_RADIUS, cy - THUMB_RADIUS, cx + THUMB_RADIUS, cy + THUMB_RADIUS);
                break;

            case MotionEvent.ACTION_UP:
                visible = false;
                return true;
        }
        return false;
    }
}
