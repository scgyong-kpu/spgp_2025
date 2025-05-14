package kr.ac.tukorea.ge.and.scgyong.smoothingpath;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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

    private ArrayList<PointF> points = new ArrayList<>();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        points.add(new PointF(x, y));
        Log.d(TAG, "TouchEvent: action=" + event.getAction() + " pos=" + x + "," + y + " now points count=" + points.size());
        return super.onTouchEvent(event);
    }
}
