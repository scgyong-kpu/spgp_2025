package kr.ac.tukorea.ge.spgp2025.a2dg.framework.util;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.res.ResourcesCompat;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

// Gauge 클래스는 게임 UI에서 진행 상태(progress)를
// 막대 형태로 표시하는 게이지(Bar) 표시기를 구현한 클래스
public class Gauge {
    private final Paint fgPaint = new Paint();
    private final Paint bgPaint = new Paint();
    // 게이지의 배경과 진행 바 색상을 각각 관리하는 Paint 객체

    public Gauge(float width, int fgColorResId, int bgColorResId) {
        Resources res = GameView.view.getResources();

        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(width);
        bgPaint.setColor(ResourcesCompat.getColor(res, bgColorResId, null));
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        // 게이지의 두께(width)와 색상(전경 fgColorResId, 배경 bgColorResId)을 받아 Paint 객체를 초기화

        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(width / 2);
        fgPaint.setColor(ResourcesCompat.getColor(res, fgColorResId, null));
        fgPaint.setStrokeCap(Paint.Cap.ROUND);
        //  선 끝을 둥글게 처리
    }

    public void draw(Canvas canvas, float x, float y, float scale, float value) {
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(scale, scale);
        draw(canvas, value);
        canvas.restore();
    }

    // 게이지의 배경선(전체 길이 1.0f)을 그린다
    //진행도(progress)가 0보다 크면, 진행선(길이 progress)을 그려서 게이지를 채움
    public void draw(Canvas canvas, float progress) {
        canvas.drawLine(0, 0, 1.0f, 0, bgPaint);
        if (progress > 0) {
            canvas.drawLine(0, 0, progress, 0, fgPaint);
        }
    }
}
