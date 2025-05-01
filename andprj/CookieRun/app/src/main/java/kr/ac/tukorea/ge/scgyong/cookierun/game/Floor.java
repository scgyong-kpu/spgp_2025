package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;

public class Floor extends Sprite {
    public enum Type {
        T_10x2, T_2x2, T_3x1
    }
    protected static int[] resIds = {
            R.mipmap.cookierun_floor_480x48,
            R.mipmap.cookierun_floor_124x120,
            R.mipmap.cookierun_floor_120x40,
    };
    protected static int[][] sizes = {
            { 1000, 200 }, { 200, 200 }, { 300, 100 }
    };
    public Floor(Type type, float left, float top) {
        super(0);
        int ord = type.ordinal();
        bitmap = BitmapPool.get(resIds[ord]);
        width = sizes[ord][0];
        height = sizes[ord][1];
        dstRect.set(left, top, left + width, top + height);
    }
}
