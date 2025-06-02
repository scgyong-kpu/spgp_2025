package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Locale;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class Cannon extends Sprite {
    protected int level;
    protected float range;
    protected float interval;
    protected final Bitmap barrelBitmap;
    protected final RectF barrelRect = new RectF();
    protected float angle = -90;
    protected float time;
    private static final int[] BITMAP_IDS = {
            R.mipmap.f_01_01, R.mipmap.f_02_01,R.mipmap.f_03_01,R.mipmap.f_04_01,R.mipmap.f_05_01,
            R.mipmap.f_06_01,R.mipmap.f_07_01,R.mipmap.f_08_01,R.mipmap.f_09_01,R.mipmap.f_10_01,
    };
    public Cannon(int level, float x, float y) {
        super(0);
        barrelBitmap = BitmapPool.get(R.mipmap.tank_barrel);
        setPosition(x, y, 200, 200);
        setLevel(level);
    }
    private static final int[] COSTS = {
            10, 100, 300, 700, 1500, 3000, 7000, 15000, 40000, 100000, 100000000
    };
    public static int getInstallationCost(int level) {
        return COSTS[level - 1];
    }
    public static int getUpgradeCost(int level) {
        return Math.round((COSTS[level] - COSTS[level - 1]) * 1.1f);
    }
    public int getUpgradeCost() {
        return getUpgradeCost(level);
    }
    public static int getSellPrice(int level) {
        return COSTS[level - 1] / 2;
    }
    public int getSellPrice() {
        return getSellPrice(level);
    }
    private void setLevel(int level) {
        bitmap = BitmapPool.get(BITMAP_IDS[level - 1]);
        this.level = level;
        this.range = 200 + (level * 200);
        this.interval = 5.5f - level / 2.0f;
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
        time += GameView.frameTime;
        if (time > interval && fly != null) {
            Shell shell = Shell.get(this, fly);
            Scene.top().add(MainScene.Layer.shell, shell);
            time = 0;
        }
    }

    public Fly findNearestFly() {
        float nearest_dist_sq = range * range;
        Fly nearest = null;
        MainScene scene = (MainScene) Scene.top();
        ArrayList<IGameObject> flies = scene.objectsAt(MainScene.Layer.enemy);
        for (IGameObject gameObject: flies) {
            if (!(gameObject instanceof Fly)) continue;
            Fly fly = (Fly) gameObject;
            float fx = fly.getX();
            float fy = fly.getY();

            // 현재 탐색 중인 fly까지의 거리 제곱 계산
            float dx = x - fx;
            float dx_sq = dx * dx;
            if (dx_sq > nearest_dist_sq) continue; // x축 거리만으로 범위 초과
            float dy = y - fy;
            float dy_sq = dy * dy;
            if (dy_sq > nearest_dist_sq) continue; // y축 거리만으로 범위 초과
            float dist_sq = dx_sq + dy_sq;
            if (nearest_dist_sq > dist_sq) {
                nearest_dist_sq = dist_sq;
                nearest = fly;
            }
        }
        return nearest;
    }

    private static Paint rangePaint;
    public void drawRange(Canvas canvas) {
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
    }

    public boolean containsPoint(float x, float y) {
        return dstRect.contains(x, y);
    }

    public boolean intersectsIfInstalledAt(float x, float y) {
        float dx = Math.abs(x - this.x), dy = Math.abs(y - this.y);
        return dx <= radius && dy <= radius;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "Cannon<%d>(%d,%d)@%d",
                level, (int)x/100, (int)y/100, System.identityHashCode(this));
    }

    public boolean upgrade() {
        if (level == BITMAP_IDS.length) {
            uninstall();
            return false;
        }
        setLevel(level + 1);
        return true;
    }

    protected void uninstall() {
        Scene.top().remove(MainScene.Layer.cannon, this);
    }
}
