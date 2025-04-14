package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.game;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.ge.scgyong.spgp2025.samplegame.R;

public class Fighter extends Sprite {
    private static final String TAG = Fighter.class.getSimpleName();
    private static final float SPEED = 800f;
    private static final float RADIUS = 125f;
    private final JoyStick joyStick;
    private float angle;

    private static final float BULLET_INTERVAL = 1.0f / 3.0f;
    private float bulletCoolTime;

    public Fighter(JoyStick joyStick) {
        super(R.mipmap.plane_240);
        this.joyStick = joyStick;
        setPosition(Metrics.width / 2, 2 * Metrics.height / 3, RADIUS);
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
        setPosition(x, y, RADIUS);
        angle = (float) Math.toDegrees(joyStick.angle_radian);
    }

    // 회전해서 그리므로 구현해야 한다
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle + 90, x, y);
        //canvas.drawBitmap(bitmap, null, dstRect, null);
        super.draw(canvas); // 직접 그려도 되고 super 를 불러도 된다.
        canvas.restore();
    }
}
