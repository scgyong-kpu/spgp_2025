package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;

public class Cannon extends Sprite {
    private final int level;
    private final Bitmap barrelBitmap;
    private final RectF barrelRect = new RectF();
    private static final int[] BITMAP_IDS = {
            R.mipmap.f_01_01, R.mipmap.f_02_01,R.mipmap.f_03_01,R.mipmap.f_04_01,R.mipmap.f_05_01,
            R.mipmap.f_06_01,R.mipmap.f_07_01,R.mipmap.f_08_01,R.mipmap.f_09_01,R.mipmap.f_10_01,
    };
    public Cannon(int level, float x, float y) {
        super(BITMAP_IDS[level - 1]);
        this.level = level;
        barrelBitmap = BitmapPool.get(R.mipmap.tank_barrel);
        setPosition(x, y, 200, 200);
        barrelRect.set(dstRect);
        float barrelSize = 50f + level * 10f;
        barrelRect.inset(-barrelSize, -barrelSize);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(barrelBitmap, null, barrelRect, null);
    }
}
