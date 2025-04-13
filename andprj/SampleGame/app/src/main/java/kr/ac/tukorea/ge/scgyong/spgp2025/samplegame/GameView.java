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
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    private final ArrayList<IGameObject> gameObjects = new ArrayList<>();
    private Fighter fighter;
    private static long previousNanos;
    public static float frameTime;

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

        Bitmap fighterBitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
        fighter = new Fighter(fighterBitmap);

        for (int i = 0; i < 5; i++) {
            gameObjects.add(new BouncingCircle());
        }
        for (int i = 0; i < 10; i++) {
            gameObjects.add(Ball.random());
        }
        gameObjects.add(fighter);

        scheduleUpdate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Metrics.onSize(w, h);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        Metrics.concat(canvas);
        // 반드시 성공적인 빌드가 진행된 후에 BuildConfig.java 가 생성되므로
        // 아래 코드가 문제가 되면 잠시 삭제해서 빌드만 성공시키고 다시 살려두어도 된다.
        if (BuildConfig.DEBUG) {
            drawDebugBackground(canvas);
        }
        for (IGameObject gobj : gameObjects) {
            gobj.draw(canvas);
        }
        canvas.restore();
        if (BuildConfig.DEBUG) {
            drawDebugInfo(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
        case MotionEvent.ACTION_MOVE:
            float[] xy = Metrics.fromScreen(event.getX(), event.getY());
            fighter.setTargetPosition(xy[0], xy[1]);
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void scheduleUpdate() {
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long nanos) {
        //Log.d(TAG, "Nanos = " + nanos + " frameTime=" + frameTime);
        if (previousNanos != 0) {
            frameTime = (nanos - previousNanos) / 1_000_000_000f;
            update();
            invalidate();
        }
        previousNanos = nanos;
        if (isShown()) {
            scheduleUpdate();
        }
    };

    private void update() {
        for (IGameObject gobj : gameObjects) {
            gobj.update();
        }
    }

    private RectF borderRect;
    private Paint borderPaint, gridPaint, fpsPaint;
    private void drawDebugBackground(@NonNull Canvas canvas) {
        if (borderRect == null) {
            borderRect = new RectF(0, 0, Metrics.SCREEN_WIDTH, Metrics.SCREEN_HEIGHT);

            borderPaint = new Paint();
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(10f);
            borderPaint.setColor(Color.RED);

            gridPaint = new Paint();
            gridPaint.setStyle(Paint.Style.STROKE);
            gridPaint.setStrokeWidth(1f);
            gridPaint.setColor(Color.GRAY);
        }

        canvas.drawRect(borderRect, borderPaint);
        for (float x = Metrics.GRID_UNIT; x < Metrics.SCREEN_WIDTH; x += Metrics.GRID_UNIT) {
            canvas.drawLine(x, 0, x, Metrics.SCREEN_HEIGHT, gridPaint);
        }
        for (float y = Metrics.GRID_UNIT; y < Metrics.SCREEN_HEIGHT; y += Metrics.GRID_UNIT) {
            canvas.drawLine(0, y, Metrics.SCREEN_WIDTH, y, gridPaint);
        }
    }
    private void drawDebugInfo(Canvas canvas) {
        if (fpsPaint == null) {
            fpsPaint = new Paint();
            fpsPaint.setColor(Color.BLUE);
            fpsPaint.setTextSize(100f);
        }

        int fps = (int) (1.0f / frameTime);
        canvas.drawText("FPS: " + fps, 100f, 200f, fpsPaint);
    }
}
