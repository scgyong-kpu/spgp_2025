package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Rect;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.SheetSprite;

public class Fly extends SheetSprite {
    public Fly(int type) {
        super(R.mipmap.galaga_flies, 2.0f);
        int size = bitmap.getHeight();
        int left = type * 2 * size;
        srcRects = new Rect[] {
            new Rect(left, 0, left + size, size),
            new Rect(left + size, 0, left + 2 * size, size),
        };

        setPosition(0, 0, 200, 200);
    }
}
