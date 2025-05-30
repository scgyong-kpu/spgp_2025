package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class Cannon extends Sprite {
    private final int level;
    private final float range;
    private final Bitmap barrelBitmap;
    private final RectF barrelRect = new RectF();
    private float angle = -90;
    private static final int[] BITMAP_IDS = {
            R.mipmap.f_01_01, R.mipmap.f_02_01,R.mipmap.f_03_01,R.mipmap.f_04_01,R.mipmap.f_05_01,
            R.mipmap.f_06_01,R.mipmap.f_07_01,R.mipmap.f_08_01,R.mipmap.f_09_01,R.mipmap.f_10_01,
    };
    public Cannon(int level, float x, float y) {
        super(BITMAP_IDS[level - 1]);
        this.level = level;
        this.range = 200 + (level * 200);
        barrelBitmap = BitmapPool.get(R.mipmap.tank_barrel);
        setPosition(x, y, 200, 200);
        barrelRect.set(dstRect);
        float barrelSize = 50f + level * 10f;
        barrelRect.inset(-barrelSize, -barrelSize);
    }

    @Override
    public void update() {
        super.update();
        Fly fly = findNearestFly();
        if (fly != null) {
            angle = (float) Math.toDegrees(Math.atan2(fly.getY() - y, fly.getX() - x));
        }
    }

    public Fly findNearestFly() {
        float dist = range;
        Fly nearest = null;
        MainScene scene = (MainScene) Scene.top();
        ArrayList<IGameObject> flies = scene.objectsAt(MainScene.Layer.enemy);
        for (IGameObject gameObject: flies) {
            if (!(gameObject instanceof Fly)) continue;
            Fly fly = (Fly) gameObject;
            float fx = fly.getX();
            float fy = fly.getY();
            float dx = x - fx;
            if (dx > dist) continue;
            float dy = y - fy;
            if (dy > dist) continue;
            float d = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist > d) {
                dist = d;
                nearest = fly;
            }
        }
        return nearest;
    }

    private static Paint rangePaint;
    private void drawRange(Canvas canvas) {
        if (rangePaint == null) {
            rangePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            rangePaint.setStyle(Paint.Style.STROKE);
            rangePaint.setStrokeWidth(10f);
            rangePaint.setPathEffect(new DashPathEffect(new float[]{10f, 20f}, 0));
            rangePaint.setColor(0x7F7F0000);
        }
        canvas.drawCircle(x, y, range, rangePaint);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // super (탱크) 를 그린 후 rotate (포신) 해야 한다
        canvas.save();
        canvas.rotate(angle, x, y);
        canvas.drawBitmap(barrelBitmap, null, barrelRect, null);
        canvas.restore();
        drawRange(canvas);
    }
}
