package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Rect;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.SheetSprite;

public class Fly extends SheetSprite {
    public Fly() {
        super(R.mipmap.galaga_flies, 2.0f);
        int size = bitmap.getHeight();
        srcRects = new Rect[] {
            new Rect(0, 0, size, size),
            new Rect(size, 0, 2 * size, size),
        };

        dstRect.set(1000, 100, 1200, 300);
    }
}
