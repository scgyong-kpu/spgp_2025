package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.tukorea.ge.scgyong.spgp2025.framework.BitmapPool;
import kr.ac.tukorea.ge.scgyong.spgp2025.framework.GameView;
import kr.ac.tukorea.ge.scgyong.spgp2025.framework.IGameObject;
import kr.ac.tukorea.ge.scgyong.spgp2025.framework.Metrics;
import kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.R;

public class Ball implements IGameObject {
    private final RectF dstRect = new RectF();
    private static final float BALL_RADIUS = 100f;
    private static final float SPEED = 700f; // 초당 700 unit 을 움직이는 속도.
    private float dx, dy;

    private static final Random random = new Random();
    public static Ball random() {
        return new Ball(random.nextFloat() * Metrics.width,
                random.nextFloat() * Metrics.height,
                random.nextFloat() * 360);
    }
    public Ball(float centerX, float centerY, float angle_degree) {
        dstRect.set(centerX - BALL_RADIUS, centerY - BALL_RADIUS,
                centerX + BALL_RADIUS, centerY + BALL_RADIUS);
        //double radian = Math.PI * angle_degree / 180;
        double radian = Math.toRadians(angle_degree);
        this.dx = SPEED * (float) Math.cos(radian);
        this.dy = SPEED * (float) Math.sin(radian);

        bitmap = BitmapPool.get(R.mipmap.soccer_ball_240);
    }
    private final Bitmap bitmap;

    public void update() {
        float timedDx = dx * GameView.frameTime;
        float timedDy = dy * GameView.frameTime;
        dstRect.offset(timedDx, timedDy);
        if (dx > 0) {
            if (dstRect.right > Metrics.width) { // Alt+Enter -> Make GameView.SCREEN_WIDTH public
                dx = -dx;
            }
        } else {
            if (dstRect.left < 0) {
                dx = -dx;
            }
        }
        if (dy > 0) {
            if (dstRect.bottom > Metrics.height) {
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
