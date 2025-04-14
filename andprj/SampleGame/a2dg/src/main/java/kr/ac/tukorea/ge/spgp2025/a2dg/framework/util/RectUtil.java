package kr.ac.tukorea.ge.spgp2025.a2dg.framework.util;

import android.graphics.RectF;

public class RectUtil {
    public static RectF newRectF(float x, float y, float radius) {
        return new RectF(x - radius, y - radius, x + radius, y + radius);
    }
    public static void setRect(RectF rect, float x, float y, float radius) {
        rect.set(x - radius, y - radius, x + radius, y + radius);
    }
    public static RectF newRectF(float x, float y, float width, float height) {
        float half_width = width / 2;
        float half_height = height / 2;
        return new RectF(x - half_width, y - half_height, x + half_width, y + half_height);
    }
    public static void setRect(RectF rect, float x, float y, float width, float height) {
        float half_width = width / 2;
        float half_height = height / 2;
        rect.set(x - half_width, y - half_height, x + half_width, y + half_height);
    }
}
