package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Gauge {
    private final Paint fgPaint = new Paint();
    private final Paint bgPaint = new Paint();
    public Gauge() {
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(0.2f);
        bgPaint.setColor(Color.YELLOW);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(0.1f);
        fgPaint.setColor(Color.BLUE);
        fgPaint.setStrokeCap(Paint.Cap.ROUND);
    }
    public void draw(Canvas canvas, float progress) {
        canvas.drawLine(0, 0, 1.0f, 0, bgPaint);
        if (progress > 0) {
            canvas.drawLine(0, 0, progress, 0, fgPaint);
        }
    }
}
