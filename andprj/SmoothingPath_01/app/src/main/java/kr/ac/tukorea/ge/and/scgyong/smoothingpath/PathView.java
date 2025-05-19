package kr.ac.tukorea.ge.and.scgyong.smoothingpath;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PathView extends View {
    private boolean closesPath;
    private Bitmap bitmap;

    public interface CallBack {
        public void onPathChanged(int count);
    }

    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    private CallBack callback;

    private static final String TAG = PathView.class.getSimpleName();
    private Path path;
    private Paint paint = new Paint();
    private PointF planePos = new PointF();

    public PathView(Context context) {
        super(context);
        init();
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void startPathAnimation() {
        PathMeasure pm = new PathMeasure(path, closesPath);
        float length = pm.getLength();
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, length);
        animator.setDuration((long)length);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                float[] pos = new float[2];
                float[] tan = new float[2];
                pm.getPosTan(value, pos, tan);
                planePos.set(pos[0], pos[1]);
                invalidate();
                Log.d(TAG, "Anim value = " + value + " x=" + pos[0] + " y=" + pos[1]);
            }
        });
        animator.start();
    }

    private ArrayList<PointF> points = new ArrayList<>();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action != MotionEvent.ACTION_DOWN && action != MotionEvent.ACTION_MOVE) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        points.add(new PointF(x, y));
        if (points.size() == 1) {
            planePos.set(x, y);
        }
        buildPath();
        if (callback != null) {
            callback.onPathChanged(points.size());
        }
        Log.d(TAG, "Points count=" + points.size());
        invalidate();

        return true;
        //return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        int count = points.size();
        if (count == 0) return;

        float px = planePos.x - bitmap.getWidth() / 2.0f;
        float py = planePos.y - bitmap.getHeight() / 2.0f;
        canvas.drawBitmap(bitmap, px, py, null);

        if (count == 1) {
            PointF first = points.get(0);
            canvas.drawCircle(first.x, first.y, 5.0f, paint);
            return;
        }

        canvas.drawPath(path, paint);
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(2.0f);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.plane_240);
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

        if (closesPath) {
            path.close();
        }
    }

    public void closePath(boolean closes) {
        closesPath = closes;
        buildPath();
        invalidate();
    }

    public void clearPoints() {
        points.clear();
        invalidate();
    }

    public int getPointCount() {
        return points.size();
    }
}

