package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;

public class Cannon extends Sprite {
    private Bitmap barrelBitmap;
    private final RectF barrelRect = new RectF();
    public Cannon() {
        super(R.mipmap.f_01_01);
        barrelBitmap = BitmapPool.get(R.mipmap.tank_barrel);
        setPosition(500, 700, 200, 200);
        barrelRect.set(dstRect);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(barrelBitmap, null, barrelRect, null);
    }
}
