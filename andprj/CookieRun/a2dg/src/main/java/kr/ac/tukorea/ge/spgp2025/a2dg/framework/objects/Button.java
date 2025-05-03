package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ITouchable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Button extends Sprite implements ITouchable {
    private static final String TAG = Button.class.getSimpleName();
    public Button(int bitmapResId, float cx, float cy, float width, float height) {
        super(bitmapResId, cx, cy, width, height);
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float[] pts = Metrics.fromScreen(e.getX(), e.getY());
        float x = pts[0], y = pts[1];
        if (!dstRect.contains(x, y)) {
            return false;
        }
        Log.d(TAG, "onTouch: " + this + ", " + e.getAction() + ", " + (int)x + ", " + (int)y);
        return false;
    }
}
