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
import android.view.View;

import androidx.annotation.NonNull;

/**
 * TODO: document your custom view class.
 */
public class MyView extends View {

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

    // Lazy Initialization
    public Paint paint;
    private Rect rect;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        calculateRect();
        canvas.drawRect(rect, paint);
    }

    private void calculateRect() {
        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        int w4 = contentWidth / 4;
        int h4 = contentHeight / 4;
        int x1 = paddingLeft + w4;
        int x2 = paddingLeft + contentWidth - w4;
        int y1 = paddingTop + h4;
        int y2 = paddingTop + contentHeight - h4;

        rect = new Rect(x1, y1, x2, y2);
    }
}