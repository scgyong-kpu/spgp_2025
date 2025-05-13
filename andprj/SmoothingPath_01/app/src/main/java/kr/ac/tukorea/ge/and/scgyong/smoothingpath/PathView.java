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
    private static final String TAG = PathView.class.getSimpleName();
    private Path path;
    private Paint paint = new Paint();

    public PathView(Context context) {
        super(context);
        initPaint();
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
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
        buildPath();
        Log.d(TAG, "Points count=" + points.size());
        invalidate();

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        int count = points.size();
        if (count == 0) return;

        if (count == 1) {
            PointF first = points.get(0);
            canvas.drawCircle(first.x, first.y, 5.0f, paint);
            return;
        }

        canvas.drawPath(path, paint);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(2.0f);
    }

    private void buildPath() {
        int count = points.size();
        if (count <= 1) return;

        path = new Path();
        PointF first = points.get(0);
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
}

