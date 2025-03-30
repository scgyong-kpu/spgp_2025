package kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * TODO: document your custom view class.
 */
public class MyView extends View {
    private static final String TAG = MyView.class.getSimpleName();
    public MyView(Context context) {
        super(context);
        init(null, 0);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0.02f);
    }

    public Paint paint;
    private Rect rect;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int l = getPaddingLeft(), r = getPaddingRight();
        int t = getPaddingTop(), b = getPaddingBottom();
        int w = getWidth(), h = getHeight();
        int contentWidth = (w - l - r);
        int contentHeight = (h - t - b);

        int cx = l + contentWidth / 2;
        int cy = t + contentHeight / 2;
        int radius;
        if (contentWidth >= contentHeight) {
            radius = contentHeight / 2;
        } else {
            radius = contentWidth / 2;
        }

        int depth = (int) Math.ceil(Math.log(radius / 100.0) / Math.log(4)) + 1;
        Log.d(TAG, "Depth=" + depth + " for radius " + radius);

        drawSmiley(canvas, cx, cy, radius, depth);
    }
    private void drawSmiley(Canvas canvas, float x, float y, float r, int depth) {
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(r, r);
        drawSmiley(canvas, depth);
        canvas.restore();
    }

    private void drawSmiley(Canvas canvas, int depth) {
        canvas.drawCircle(0, 0, 1.0f, paint);

        float leftEyeX = - 1.0f / 3, rightEyeX = 1.0f / 3;
        float eyeY = - 1.0f / 4;
        float eyeRadius = 1.0f / 4;

        //Log.d(TAG, "Radius=" + radius);
        if (depth > 1) {
            drawSmiley(canvas, leftEyeX, eyeY, eyeRadius, depth-1);
            drawSmiley(canvas, rightEyeX, eyeY, eyeRadius, depth-1);
        } else {
            canvas.drawCircle(leftEyeX, eyeY, eyeRadius, paint);
            canvas.drawCircle(rightEyeX, eyeY, eyeRadius, paint);
        }

        float mouthX1 = - 1.0f / 2, mouthX2 = 1.0f / 2;
        float mouthY = + 1.0f / 2;
        canvas.drawArc(mouthX1, eyeY, mouthX2, mouthY, 15, 150, false, paint);
    }
}