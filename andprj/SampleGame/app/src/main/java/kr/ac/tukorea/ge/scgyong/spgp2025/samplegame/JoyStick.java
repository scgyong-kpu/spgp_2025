package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

public class JoyStick implements IGameObject {
    private final Bitmap bgBitmap;
    private final Bitmap thumbBitmap;

    private final RectF bgRect = new RectF(0, 1200, 400, 1600);
    private final RectF thumbRect = new RectF(150, 1350, 250, 1450);

    private boolean visible;

    public JoyStick() {
        bgBitmap = BitmapPool.get(R.mipmap.joystick_bg);
        thumbBitmap = BitmapPool.get(R.mipmap.joystick_thumb);
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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                visible = true;
                return true;
            case MotionEvent.ACTION_UP:
                visible = false;
                return true;
        }
        return false;
    }
}
