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

// MyView는 View 클래스를 상속한 커스텀 뷰
public class MyView extends View {
    private static final String TAG = MyView.class.getSimpleName();

    // Context, AttributeSet, 그리고 스타일 정보
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

    // 초기화
    private void init(AttributeSet attrs, int defStyle) {
        paint = new Paint(); // Paint 객체를 생성
        paint.setColor(Color.BLUE); // 선 색을 파란색
        paint.setStyle(Paint.Style.STROKE); // 선 스타일로 STROKE를 설정 (채우기 없이 선만 그리도록)
        paint.setStrokeWidth(10); // 선의 두께를 10px로 설정

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0.02f);
    }

    public Paint paint;
    private Rect rect;


    // Canvas를 사용해 drawSmiley 메서드를 호출하여 웃는 얼굴을 그립니다.
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 뷰의 패딩 값을 가져옵니다.
        int l = getPaddingLeft(), r = getPaddingRight();
        int t = getPaddingTop(), b = getPaddingBottom();

        // 뷰의 크기를 가져옵니다.
        int w = getWidth(), h = getHeight();

        // contentWidth와 contentHeight를 계산하여,
        // 이 값을 drawSmiley에 전달하여 웃는 얼굴을 그릴 위치와 크기를 설정합니다.
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

    private void drawSmiley(Canvas canvas, int left, int top, int width, int height) {
        int cx = left + width / 2, cy = top + height / 2;
        int radius = Math.min(width, height) / 2;
        canvas.drawCircle(cx, cy, radius, paint);
        // 1. canvas.drawCircle로 원을 그려 얼굴을 만듭니다.
        // 원의 중심은 cx, cy로 계산하며, 반지름은 radius

    private void drawSmiley(Canvas canvas, int depth) {
        canvas.drawCircle(0, 0, 1.0f, paint);

        float leftEyeX = - 1.0f / 3, rightEyeX = 1.0f / 3;
        float eyeY = - 1.0f / 4;
        float eyeRadius = 1.0f / 4;

        Log.d(TAG, "Radius=" + radius);
        if (radius > 100) {
            // 만약 얼굴의 크기(radius)가 100 이상이면,
            // 더 작은 크기의 눈을 그리기 위해 재귀적으로 drawSmiley를 호출
            drawSmiley(canvas, leftEyeX - eyeRadius, eyeY - eyeRadius, 2 * eyeRadius, 2 * eyeRadius);
            drawSmiley(canvas, rightEyeX - eyeRadius, eyeY - eyeRadius, 2 * eyeRadius, 2 * eyeRadius);

        //Log.d(TAG, "Radius=" + radius);
        if (depth > 1) {
            drawSmiley(canvas, leftEyeX, eyeY, eyeRadius, depth-1);
            drawSmiley(canvas, rightEyeX, eyeY, eyeRadius, depth-1);
        } else {

            // 왼쪽과 오른쪽 눈의 위치를 계산한 후 drawCircle로 두 개의 눈을 그립니다.
            canvas.drawCircle(leftEyeX, eyeY, eyeRadius, paint);
            canvas.drawCircle(rightEyeX, eyeY, eyeRadius, paint);
        }

        float mouthX1 = - 1.0f / 2, mouthX2 = 1.0f / 2;
        float mouthY = + 1.0f / 2;
        canvas.drawArc(mouthX1, eyeY, mouthX2, mouthY, 15, 150, false, paint);
        // drawArc를 사용해 입을 그립니다.
        // mouthX1, mouthX2, mouthY는 입의 시작과 끝 위치를 계산하며, drawArc의 각도는 15도에서 150도 사이
    }
}