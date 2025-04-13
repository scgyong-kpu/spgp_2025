package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.scgyong.spgp2025.framework.BitmapPool;
import kr.ac.tukorea.ge.scgyong.spgp2025.framework.GameView;
import kr.ac.tukorea.ge.scgyong.spgp2025.framework.IGameObject;
import kr.ac.tukorea.ge.scgyong.spgp2025.framework.JoyStick;
import kr.ac.tukorea.ge.scgyong.spgp2025.framework.Metrics;
import kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.R;
import kr.ac.tukorea.ge.scgyong.spgp2025.framework.Scene;

public class Fighter implements IGameObject {
    private static final String TAG = Fighter.class.getSimpleName();
    private static final float SPEED = 800f;
    private final Bitmap bitmap;
    private final JoyStick joyStick;
    private float x, y, angle;
    private final RectF dstRect = new RectF();

    private static final float BULLET_INTERVAL = 1.0f / 3.0f;
    private float bulletCoolTime;

    public Fighter(JoyStick joyStick) {
        this.joyStick = joyStick;
        Resources res = GameView.view.getResources();
        bitmap = BitmapPool.get(R.mipmap.plane_240);
        float x = Metrics.width / 2;
        float y = 2 * Metrics.height / 3;
        setPosition(x, y);
        angle = -90;
    }

    public void update() {
        bulletCoolTime -= GameView.frameTime;
        if (bulletCoolTime <= 0) {
            Bullet bullet = new Bullet(x, y, (float) Math.toRadians(angle));
            Scene.top().add(bullet);
            bulletCoolTime = BULLET_INTERVAL;
        }

        if (joyStick.power <= 0) {
            return;
        }

        // 옵션 1-1 : power 를 적용하지 않는 경우
        //float distance = SPEED * GameView.frameTime;

        // 옵션 1-2 : power 를 적용하는 경우
        float distance = SPEED * joyStick.power * GameView.frameTime;

        // 옵션 2-1 : 8방향인 경우
        //final int way = 8;
        //final double TWO_PI = Math.PI * 2;
        //float eightWayAngle = (float) (Math.round(way * joyStick.angle_radian / TWO_PI) * TWO_PI / way);
        //x += (float) (distance * Math.cos(eightWayAngle));
        //y += (float) (distance * Math.sin(eightWayAngle));
        //setPosition(x, y);
        //angle = (float) Math.toDegrees(eightWayAngle);

        // 옵션 2-2 : 360° 인 경우
        x += (float) (distance * Math.cos(joyStick.angle_radian));
        y += (float) (distance * Math.sin(joyStick.angle_radian));
        setPosition(x, y);
        angle = (float) Math.toDegrees(joyStick.angle_radian);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle + 90, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }

    public void setPosition(float x, float y) {
        float r = 125f;
        dstRect.set(x-r, y-r, x+r, y+r);
        this.x = x;
        this.y = y;
        //Log.d(TAG, "x=" + x + " y=" + y + " rect=" + dstRect);
    }
}
