package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

public class Ball implements IGameObject {
    private final RectF dstRect = new RectF();
    private static final float BALL_RADIUS = 100f;
    private static final float SPEED = 700f; // 초당 700 unit 을 움직이는 속도.
    private float dx, dy;

    private static final Random random = new Random();
    public static Ball random() {
        return new Ball(random.nextFloat() * Metrics.SCREEN_WIDTH,
                random.nextFloat() * Metrics.SCREEN_HEIGHT,
                random.nextFloat() * 360);
    }
    public Ball(float centerX, float centerY, float angle_degree) {
        dstRect.set(centerX - BALL_RADIUS, centerY - BALL_RADIUS,
                centerX + BALL_RADIUS, centerY + BALL_RADIUS);
        //double radian = Math.PI * angle_degree / 180;
        double radian = Math.toRadians(angle_degree);
        this.dx = SPEED * (float) Math.cos(radian);
        this.dy = SPEED * (float) Math.sin(radian);

        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
        }
    }
    private static Bitmap bitmap;

    public void update() {
        float timedDx = dx * GameView.frameTime;
        float timedDy = dy * GameView.frameTime;
        dstRect.offset(timedDx, timedDy);
        if (dx > 0) {
            if (dstRect.right > Metrics.SCREEN_WIDTH) { // Alt+Enter -> Make GameView.SCREEN_WIDTH public
                dx = -dx;
            }
        } else {
            if (dstRect.left < 0) {
                dx = -dx;
            }
        }
        if (dy > 0) {
            if (dstRect.bottom > Metrics.SCREEN_HEIGHT) {
                dy = -dy;
            }
        } else {
            if (dstRect.top < 0) {
                dy = -dy;
            }
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
