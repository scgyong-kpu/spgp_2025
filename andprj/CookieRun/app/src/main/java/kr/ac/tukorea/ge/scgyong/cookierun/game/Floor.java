package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.Bitmap;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;

public class Floor extends MapObject {
    public enum Type {
        T_10x2, T_2x2, T_3x1;
        public static final int COUNT = values().length;

        Bitmap bitmap() { return BitmapPool.get(resId()); }
        int resId() { return resIds[this.ordinal()]; }
        int width() { return sizes[this.ordinal()][0]; }
        int height() { return sizes[this.ordinal()][1]; }
        static final int[] resIds = {
                R.mipmap.cookierun_floor_480x48,
                R.mipmap.cookierun_floor_124x120,
                R.mipmap.cookierun_floor_120x40,
        };
        static final int[][] sizes = {
                { 1000, 200 }, { 200, 200 }, { 300, 100 }
        };
    }
    public Floor() {}
    public static Floor get(Type type, float left, float top) {
        //return Scene.top().getRecyclable(Floor.class).init(type, left, top);
        return new Floor().init(type, left, top);
    }

    private Floor init(Type type, float left, float top) {
        bitmap = type.bitmap();
        width = type.width();
        height = type.height();
        dstRect.set(left, top, left + width, top + height);
        return this;
    }
    @Override
    public MainScene.Layer getLayer() {
        return MainScene.Layer.floor;
    }
}
