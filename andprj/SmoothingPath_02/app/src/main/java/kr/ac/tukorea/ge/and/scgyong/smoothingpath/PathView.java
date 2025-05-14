package kr.ac.tukorea.ge.and.scgyong.smoothingpath;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class PathView extends View {
    private static final String TAG = PathView.class.getSimpleName();

    public PathView(Context context) {
        super(context);
        init();
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Log.d(TAG, "TouchEvent: action=" + event.getAction() + " pos=" + x + "," + y);
        return super.onTouchEvent(event);
    }
}
