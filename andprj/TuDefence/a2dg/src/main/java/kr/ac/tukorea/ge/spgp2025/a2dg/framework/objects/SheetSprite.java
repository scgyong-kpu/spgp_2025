package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.graphics.Canvas;
import android.graphics.Rect;

public class SheetSprite extends AnimSprite {
    protected Rect[] srcRects;
    public SheetSprite(int mipmapResId, float fps) {
        super(mipmapResId, fps);
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int index = Math.round(time * fps) % srcRects.length;
        canvas.drawBitmap(bitmap, srcRects[index], dstRect, null);
    }
}
