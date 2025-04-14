package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.RectUtil;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;

public class JoyStick implements IGameObject {
    private static final String TAG = JoyStick.class.getSimpleName();
    private final Bitmap bgBitmap;
    private final Bitmap thumbBitmap;

    private float x; // = 200f;
    private float y; //CENTER_Y = 1400f;
    private float bg_radius; //BG_RADIUS = 200f;
    private float thumb_radius; //THUMB_RADIUS = 60f;
    private float move_radius; //MOVE_RADIUS = BG_RADIUS - THUMB_RADIUS;
    private final RectF bgRect;
    private final RectF thumbRect;

    private boolean visible;
    private float startX, startY;
    public float power, angle_radian;
    public JoyStick(int bgBmpId, int thumbBmpId, float x, float y, float bg_radius, float thumb_radius, float move_radius) {
        this.x = x; this.y = y;
        this.bg_radius = bg_radius;
        this.thumb_radius = thumb_radius;
        this.move_radius = move_radius;
        bgBitmap = BitmapPool.get(bgBmpId);
        thumbBitmap = BitmapPool.get(thumbBmpId);
        bgRect = RectUtil.newRectF(x, y, bg_radius);
        thumbRect = RectUtil.newRectF(x, y, thumb_radius);
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
                RectUtil.setRect(thumbRect, x, y, thumb_radius);
                power = 0;
                return true;
            case MotionEvent.ACTION_MOVE:
                pts = Metrics.fromScreen(event.getX(), event.getY());
                float dx = Math.max(-bg_radius, Math.min(pts[0] - startX, bg_radius));
                float dy = Math.max(-bg_radius, Math.min(pts[1] - startY, bg_radius));
                double radius = Math.sqrt(dx * dx + dy * dy);
                angle_radian = (float) Math.atan2(dy, dx);
                if (radius > move_radius) {
                    dx = (float) (move_radius * Math.cos(angle_radian));
                    dy = (float) (move_radius * Math.sin(angle_radian));
                    radius = move_radius;
                }
                power = (float) (radius / move_radius);
                float cx = x + dx, cy = y + dy;
                //Log.d(TAG, "sx="+startX+" sy="+startY+" dx="+dx + " dy=" + dy + " x=" + x + " y=" + y + " cx=" + cx + " cy=" + cy);
                Log.d(TAG, "angle=" + (int)Math.toDegrees(angle_radian) + "° power=" + String.format("%.2f", power));
                RectUtil.setRect(thumbRect, cx, cy, thumb_radius);
                break;

            case MotionEvent.ACTION_UP:
                visible = false;
                power = 0;
                return true;
        }
        return false;
    }
}
