package kr.ac.tukorea.ge.spgp2025.a2dg.framework.util;

import android.graphics.RectF;

public class RectUtil {

    // 반지름을 기준으로 새로운 RectF 생성
    public static RectF newRectF(float x, float y, float radius) {
        return new RectF(x - radius, y - radius, x + radius, y + radius);
    }

    // 반지름을 기준으로 RectF 설정
    public static void setRect(RectF rect, float x, float y, float radius) {
        rect.set(x - radius, y - radius, x + radius, y + radius);
    }

    // 너비와 높이를 기준으로 새로운 RectF 생성
    public static RectF newRectF(float x, float y, float width, float height) {
        float half_width = width / 2;
        float half_height = height / 2;
        return new RectF(x - half_width, y - half_height, x + half_width, y + half_height);
    }

    // 너비와 높이를 기준으로 RectF 설정
    public static void setRect(RectF rect, float x, float y, float width, float height) {
        float half_width = width / 2;
        float half_height = height / 2;
        rect.set(x - half_width, y - half_height, x + half_width, y + half_height);
    }

    // RectF에 스케일링을 적용하는 메서드 -> 새로 추가
    public static void scaleRect(RectF rect, float scaleX, float scaleY) {
        // 기존 크기 가져오기
        float centerX = rect.centerX();
        float centerY = rect.centerY();
        float halfWidth = rect.width() / 2;
        float halfHeight = rect.height() / 2;

        // 새로운 크기 계산
        float newHalfWidth = halfWidth * scaleX;
        float newHalfHeight = halfHeight * scaleY;

        // RectF 업데이트
        rect.set(centerX - newHalfWidth, centerY - newHalfHeight,
                centerX + newHalfWidth, centerY + newHalfHeight);
    }
}
