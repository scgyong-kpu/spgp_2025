package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Ball {
    private final RectF dstRect = new RectF();
    private static final float BALL_RADIUS = 1.0f;
    private float dx, dy;

    public Ball(float centerX, float centerY, float dx, float dy) {
        dstRect.set(centerX - BALL_RADIUS, centerY - BALL_RADIUS,
                centerX + BALL_RADIUS, centerY + BALL_RADIUS);
        this.dx = dx;
        this.dy = dy;
    }
    private static Bitmap bitmap;
    public static void setBitmap(Bitmap bitmap) { // Alt+Insert -> Setter
        Ball.bitmap = bitmap;
    }

    public void update() {
        dstRect.offset(dx, dy);
        if (dx > 0) {
            if (dstRect.right > GameView.SCREEN_WIDTH) { // Alt+Enter -> Make GameView.SCREEN_WIDTH public
                dx = -dx;
            }
        } else {
            if (dstRect.left < 0) {
                dx = -dx;
            }
        }
        if (dy > 0) {
            if (dstRect.bottom > GameView.SCREEN_HEIGHT) {
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
