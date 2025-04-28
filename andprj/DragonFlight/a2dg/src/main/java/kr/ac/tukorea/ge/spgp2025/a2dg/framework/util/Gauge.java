package kr.ac.tukorea.ge.spgp2025.a2dg.framework.util;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.res.ResourcesCompat;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class Gauge {
    private final Paint fgPaint = new Paint();
    private final Paint bgPaint = new Paint();
    public Gauge(float width, int fgColorResId, int bgColorResId) {
        Resources res = GameView.view.getResources();
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(width);
        bgPaint.setColor(ResourcesCompat.getColor(res, bgColorResId, null));
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(width / 2);
        fgPaint.setColor(ResourcesCompat.getColor(res, fgColorResId, null));
        fgPaint.setStrokeCap(Paint.Cap.ROUND);
    }
    public void draw(Canvas canvas, float x, float y, float scale, float value) {
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(scale, scale);
        draw(canvas, value);
        canvas.restore();
    }
    public void draw(Canvas canvas, float progress) {
        canvas.drawLine(0, 0, 1.0f, 0, bgPaint);
        if (progress > 0) {
            canvas.drawLine(0, 0, progress, 0, fgPaint);
        }
    }
}
