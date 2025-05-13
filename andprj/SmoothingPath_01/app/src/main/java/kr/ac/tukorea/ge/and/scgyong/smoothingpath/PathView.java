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
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private ArrayList<PointF> points = new ArrayList<>();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action != MotionEvent.ACTION_DOWN) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        points.add(new PointF(x, y));
        Log.d(TAG, "Points count=" + points.size());

        return super.onTouchEvent(event);
    }
}

