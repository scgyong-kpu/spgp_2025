package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ITouchable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Button extends Sprite implements ITouchable {
    public interface OnTouchListener {
        public boolean onTouch(boolean pressed);
    }
    protected OnTouchListener listener;
    private static final String TAG = Button.class.getSimpleName();
    public Button(int bitmapResId, float cx, float cy, float width, float height, OnTouchListener listener) {
        super(bitmapResId, cx, cy, width, height);
        this.listener = listener;
    }
    protected boolean captures;
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();
        //Log.d(TAG, "onTouch:" + this + " action=" + action);
        if (action == MotionEvent.ACTION_DOWN) {
            float[] pts = Metrics.fromScreen(e.getX(), e.getY());
            float x = pts[0], y = pts[1];
            if (!dstRect.contains(x, y)) {
                return false;
            }
            captures = true;
            return listener.onTouch(true);
        } else if (action == MotionEvent.ACTION_UP) {
            captures = false;
            return listener.onTouch(false);
        }
        return captures;
    }
}
