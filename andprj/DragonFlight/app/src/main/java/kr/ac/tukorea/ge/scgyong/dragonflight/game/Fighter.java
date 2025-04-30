package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.RectUtil;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Fighter extends Sprite {
    private static final String TAG = Fighter.class.getSimpleName();
    private static final float PLANE_WIDTH = 175f;
    private static final int PLANE_SRC_WIDTH = 80;
    private static final float SPEED = 300f;
    private float targetX;

    private static final float FIRE_INTERVAL = 0.25f;
    private float fireCoolTime = FIRE_INTERVAL;
    private static final float BULLET_OFFSET = 80f;

    private static final float SPARK_OFFSET = 66f;
    private static final float SPARK_DURATION = 0.1f;
    private static final float SPARK_WIDTH = 115f;
    private static final float SPARK_HEIGHT = SPARK_WIDTH * 3 / 5;
    private RectF sparkRect = new RectF();
    private Bitmap sparkBitmap;
    private static final float MAX_ROLL_TIME = 0.4f;
    private float rollTime;

    public Fighter() {
        super(R.mipmap.fighters);
        setPosition(Metrics.width / 2, Metrics.height - 200, PLANE_WIDTH, PLANE_WIDTH);
        targetX = x;

        sparkBitmap = BitmapPool.get(R.mipmap.laser_spark);
        srcRect = new Rect();
    }

    @Override
    public void update() {
        if (targetX < x) {
            dx = -SPEED;
        } else if (x < targetX) {
            dx = SPEED;
        } else {
            dx = 0;
        }
        super.update();
        float adjx = x;
        if ((dx < 0 && x < targetX) || (dx > 0 && x > targetX)) {
            adjx = targetX;
        } else {
            adjx = Math.max(radius, Math.min(x, Metrics.width - radius));
        }
        if (adjx != x) {
            setPosition(adjx, y, PLANE_WIDTH, PLANE_WIDTH);
        }
        fireBullet();
        updateRoll();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (FIRE_INTERVAL - fireCoolTime < SPARK_DURATION) {
            RectUtil.setRect(sparkRect, x, y - SPARK_OFFSET, SPARK_WIDTH, SPARK_HEIGHT);
            canvas.drawBitmap(sparkBitmap, null, sparkRect, null);
        }
    }

    private void fireBullet() {
        fireCoolTime -= GameView.frameTime;
        if (fireCoolTime > 0) {
            return;
        }
        fireCoolTime = FIRE_INTERVAL;
        MainScene scene = (MainScene) Scene.top();
        if (scene == null) return;

        int score = scene.getScore();
        int power = 10 + score / 1000;
        Bullet bullet = Bullet.get(x, y - BULLET_OFFSET, power);
        scene.add(bullet);
    }

    private void updateRoll() {
        //boolean wasZero = rollTime == 0; // for debug log
        int sign = targetX < x ? -1 : x < targetX ? 1 : 0; // roll 을 변경시킬 부호를 정한다
        if (x == targetX) {                         // 비행기가 멈췄을 때
            if (rollTime > 0) sign = -1;         // 오른쪽으로 움직이고 있었다면 감소시킨다
            else if (rollTime < 0) sign = 1;     // 왼쪽으로 움직이고 있었다면 증가시킨다
        }
        rollTime += sign * GameView.frameTime;
        if (x == targetX) {                           // 비행기가 멈췄을 때
            if (sign < 0 && rollTime < 0) rollTime = 0; // 감소중이었는데 0 을 지나쳤다면 0으로
            if (sign > 0 && rollTime > 0) rollTime = 0; // 증가중이었는데 0 을 지나쳤다면 0으로
        }
        if (rollTime < -MAX_ROLL_TIME) rollTime = -MAX_ROLL_TIME;    // 최대 MAX_ROLL_TIME 까지만
        else if (rollTime > MAX_ROLL_TIME) rollTime = MAX_ROLL_TIME;

        //if (!wasZero || rollTime != 0) {
        //    Log.v(TAG, "RollTime = " + rollTime);
        //}

        int rollIndex = 5 + (int)(rollTime * 5 / MAX_ROLL_TIME);
        srcRect.set(rollIndex * PLANE_SRC_WIDTH, 0, (rollIndex + 1) * PLANE_SRC_WIDTH, PLANE_SRC_WIDTH);
    }
    private void setTargetX(float x) {
        targetX = Math.max(radius, Math.min(x, Metrics.width - radius));
    }
    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                float[] pts = Metrics.fromScreen(event.getX(), event.getY());
                setTargetX(pts[0]);
                return true;

        }
        return false;
    }
}
