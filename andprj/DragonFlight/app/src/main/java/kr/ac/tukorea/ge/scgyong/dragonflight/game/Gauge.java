package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Gauge {
    private float value = 1.0f;
    private final Paint fgPaint = new Paint();
    private final Paint bgPaint = new Paint();
    public Gauge() {
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(20f);
        bgPaint.setColor(Color.YELLOW);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(10f);
        fgPaint.setColor(Color.BLUE);
        fgPaint.setStrokeCap(Paint.Cap.ROUND);
    }
    public void draw(Canvas canvas) {
        canvas.drawLine(100, 500, 200, 500, bgPaint);
        if (value > 0) {
            canvas.drawLine(100, 500, 100 + value * 100, 500, fgPaint);
        }
    }
}
