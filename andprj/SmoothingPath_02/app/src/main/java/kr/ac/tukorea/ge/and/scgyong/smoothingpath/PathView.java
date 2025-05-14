package kr.ac.tukorea.ge.and.scgyong.smoothingpath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PathView extends View {
    public interface Callback {
        public void onPointsCountChange(int count);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private Callback callback;
    private static final String TAG = PathView.class.getSimpleName();
    private Paint paint;
    private Path path;

    public PathView(Context context) {
        super(context);
        init();
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(2.0f);
    }

    private ArrayList<PointF> points = new ArrayList<>();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        points.add(new PointF(x, y));
        //activity.updatePointsCount()
        if (callback != null) {
            callback.onPointsCountChange(points.size());
        }
        buildPath();
        invalidate();
        Log.d(TAG, "TouchEvent: action=" + event.getAction() + " pos=" + x + "," + y + " now points count=" + points.size());
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        int count = points.size();
        if (count == 1) {
            PointF first = points.get(0);
            canvas.drawCircle(first.x, first.y, 5.0f, paint);
            return;
        }
        if (count < 2) {
            return;
        }
        Log.v(TAG, "Drawing " + count + " points");
        canvas.drawPath(path, paint);
    }

    private void buildPath() {
        int count = points.size();
        if (count < 2) {
            return;
        }
        PointF first = points.get(0);
        path = new Path();
        path.moveTo(first.x, first.y);
        for (int i = 1; i < count; i++) {
            PointF pt = points.get(i);
            path.lineTo(pt.x, pt.y);
        }
    }

    public void clearPoints() {
        points.clear();
        invalidate();
    }

    public int getPointsCount() {
        return points.size();
    }
}
