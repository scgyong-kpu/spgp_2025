package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View implements Choreographer.FrameCallback {
    public static final float SCREEN_WIDTH = 9.0f;
    public static final float SCREEN_HEIGHT = 16.0f;
    private static final String TAG = GameView.class.getSimpleName();
    private final Matrix transformMatrix = new Matrix();

    private final ArrayList<Ball> balls = new ArrayList<>();

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 실질적 생성자 역할

        Resources res = getResources();
        Bitmap ballBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
        Ball.setBitmap(ballBitmap);

        balls.add(Ball.random());
        balls.add(Ball.random());

        scheduleUpdate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float view_ratio = (float)w / (float)h;
        float game_ratio = SCREEN_WIDTH / SCREEN_HEIGHT;

        transformMatrix.reset();
        if (view_ratio > game_ratio) {
            float scale = h / SCREEN_HEIGHT;
            transformMatrix.preTranslate((w - h * game_ratio) / 2, 0);
            transformMatrix.preScale(scale, scale);
        } else {
            float scale = w / SCREEN_WIDTH;
            transformMatrix.preTranslate(0, (h - w / game_ratio) / 2);
            transformMatrix.preScale(scale, scale);
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.setMatrix(transformMatrix);
        drawDebugBackground(canvas);
        for (Ball ball : balls) {
            ball.draw(canvas);
        }
    }

    private void scheduleUpdate() {
        Choreographer.getInstance().postFrameCallback(this);    }

    @Override
    public void doFrame(long nanos) {
        update();
        invalidate();
        if (isShown()) {
            scheduleUpdate();
        }
    };

    private void update() {
        for (Ball ball : balls) {
            ball.update();
        }
    }

    private RectF borderRect;
    private Paint borderPaint, gridPaint;
    private void drawDebugBackground(@NonNull Canvas canvas) {
        if (borderRect == null) {
            borderRect = new RectF(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

            borderPaint = new Paint();
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(0.1f);
            borderPaint.setColor(Color.RED);

            gridPaint = new Paint();
            gridPaint.setStyle(Paint.Style.STROKE);
            gridPaint.setStrokeWidth(0.01f);
            gridPaint.setColor(Color.GRAY);
        }

        canvas.drawRect(borderRect, borderPaint);
        for (float x = 1.0f; x < SCREEN_WIDTH; x += 1.0f) {
            canvas.drawLine(x, 0, x, SCREEN_HEIGHT, gridPaint);
        }
        for (float y = 1.0f; y < SCREEN_HEIGHT; y += 1.0f) {
            canvas.drawLine(0, y, SCREEN_WIDTH, y, gridPaint);
        }
    }
}
