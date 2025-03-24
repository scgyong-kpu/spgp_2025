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
        paint.setStrokeWidth(10);
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

        drawSmiley(canvas, l, t, contentWidth,contentHeight);


        // Calculate the center, radius, and eye position of the first smiley
        int cx = l + contentWidth / 2;
        int cy = t + contentHeight / 2;
        int radius = Math.min(contentWidth, contentHeight) / 2;
        int eyeRadius = radius / 4;
        int leftEyeX = cx - radius / 3;
        int eyeY = cy - radius / 4;

        // Calculate parameters for the second smiley based on the left eye of the first smiley
        int secondSmileyLeft = leftEyeX - eyeRadius;
        int secondSmileyTop = eyeY - eyeRadius;
        int secondSmileySize = eyeRadius * 2; // The diameter of the eye is the size for the second smiley

        drawSmiley(canvas, secondSmileyLeft, secondSmileyTop, secondSmileySize, secondSmileySize);

        int rightSmileyLeft = cx + radius / 3 - eyeRadius;
        drawSmiley(canvas, rightSmileyLeft, secondSmileyTop, secondSmileySize, secondSmileySize);
    }

    private void drawSmiley(Canvas canvas, int left, int top, int width, int height) {
        int cx = left + width / 2, cy = top + height / 2;
        int radius = Math.min(width, height) / 2;
        canvas.drawCircle(cx, cy, radius, paint);

        int leftEyeX = cx - radius / 3, rightEyeX = cx + radius / 3;
        int eyeY = cy - radius / 4;
        int eyeRadius = radius / 4;

        Log.d(TAG, "Radius=" + radius);
        if (radius > 100) {
            drawSmiley(canvas, leftEyeX - eyeRadius, eyeY - eyeRadius, 2 * eyeRadius, 2 * eyeRadius);
            drawSmiley(canvas, rightEyeX - eyeRadius, eyeY - eyeRadius, 2 * eyeRadius, 2 * eyeRadius);
        } else {
            canvas.drawCircle(leftEyeX, eyeY, eyeRadius, paint);
            canvas.drawCircle(rightEyeX, eyeY, eyeRadius, paint);
        }
        
        int mouthX1 = cx - radius / 2, mouthX2 = cx + radius / 2;
        int mouthY = cy + radius / 2;
        canvas.drawArc(mouthX1, eyeY, mouthX2, mouthY, 15, 150, false, paint);
    }
}