package kr.ac.tukorea.ge.spgp2025.a2dg.framework.view;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;

public class Metrics {
    private static final String TAG = Metrics.class.getSimpleName();
    public static float width = 900f;
    public static float height = 1600f;
    public static final float GRID_UNIT = 100f;
    public static final RectF borderRect = new RectF(0, 0, Metrics.width, Metrics.height);
    public static final RectF screenRect = new RectF();
    private static final Matrix transformMatrix = new Matrix();
    private static final Matrix invertedMatrix = new Matrix();
    private static final float[] pointsBuffer = new float[2];


    public static void setGameSize(float width, float height) {
        Metrics.width = width;
        Metrics.height = height;
        borderRect.right = width;
        borderRect.bottom = height;
    }
    public static void onSize(int w, int h) {

        float view_ratio = (float)w / (float)h;
        float game_ratio = Metrics.width / Metrics.height;

        if (view_ratio > game_ratio) {
            float scale = h / Metrics.height;
            transformMatrix.setTranslate((w - h * game_ratio) / 2, 0);
            transformMatrix.preScale(scale, scale);
        } else {
            float scale = w / Metrics.width;
            transformMatrix.setTranslate(0, (h - w / game_ratio) / 2);
            transformMatrix.preScale(scale, scale);
        }
        transformMatrix.invert(invertedMatrix);

        screenRect.set(0, 0, w, h);
        invertedMatrix.mapRect(screenRect);
        Log.d(TAG, "Screen Rect = " + screenRect);
    }

    public static float[] fromScreen(float x, float y) {
        pointsBuffer[0] = x;
        pointsBuffer[1] = y;
        invertedMatrix.mapPoints(pointsBuffer);
        return pointsBuffer;
    }
    public static float[] toScreen(float x, float y) {
        pointsBuffer[0] = x;
        pointsBuffer[1] = y;
        transformMatrix.mapPoints(pointsBuffer);
        return pointsBuffer;
    }

    public static void concat(Canvas canvas) {
        canvas.concat(transformMatrix);
    }
}
