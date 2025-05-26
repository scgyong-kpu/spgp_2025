package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Rect;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.SheetSprite;

public class Fly extends SheetSprite {
    public enum Type {
        boss, red, blue, cyan, dragon,
    }
    public Fly(Type type) {
        super(R.mipmap.galaga_flies, 2.0f);
        if (rects_array == null) {
            int type_count = Type.values().length;
            //int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            rects_array = new Rect[type_count][];
            int x = 0;
            for (int i = 0; i < type_count; i++) {
                rects_array[i] = new Rect[2];
                for (int j = 0; j < 2; j++) {
                    rects_array[i][j] = new Rect(x, 0, x+h, h);
                    x += h;
                }
            }
        }
        setPosition(0, 0, 200, 200);
        srcRects = rects_array[type.ordinal()];
    }
    private static Rect[][] rects_array;
}
