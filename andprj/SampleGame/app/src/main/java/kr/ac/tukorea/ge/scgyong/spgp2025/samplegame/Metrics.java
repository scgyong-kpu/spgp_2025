package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.graphics.Canvas;
import android.graphics.Matrix;

public class Metrics {
    public static final float SCREEN_WIDTH = 9.0f;
    public static final float SCREEN_HEIGHT = 16.0f;
    private static final Matrix transformMatrix = new Matrix();
    private static final Matrix invertedMatrix = new Matrix();
    private static final float[] pointsBuffer = new float[2];

    public static void onSize(int w, int h) {

        float view_ratio = (float)w / (float)h;
        float game_ratio = Metrics.SCREEN_WIDTH / Metrics.SCREEN_HEIGHT;

        if (view_ratio > game_ratio) {
            float scale = h / Metrics.SCREEN_HEIGHT;
            transformMatrix.setTranslate((w - h * game_ratio) / 2, 0);
            transformMatrix.preScale(scale, scale);
        } else {
            float scale = w / Metrics.SCREEN_WIDTH;
            transformMatrix.setTranslate(0, (h - w / game_ratio) / 2);
            transformMatrix.preScale(scale, scale);
        }
        transformMatrix.invert(invertedMatrix);
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
