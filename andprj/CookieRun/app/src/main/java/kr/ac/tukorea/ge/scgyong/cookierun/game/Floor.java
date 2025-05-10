package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.Bitmap;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

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
    protected Type type;
    public Floor() {
        super(MainScene.Layer.floor);
    }
    public static Floor get(Type type, float left, float top) {
        return Scene.top().getRecyclable(Floor.class).init(type, left, top);
//        return new Floor().init(type, left, top);
    }
    public static Floor get(char type, float left, float top) {
        switch (type) {
            case 'O': return get(Type.T_10x2, left, top);
            case 'P': return get(Type.T_2x2, left, top);
            case 'Q': return get(Type.T_3x1, left, top);
        }
        return null;
    }

    private Floor init(Type type, float left, float top) {
        bitmap = type.bitmap();
        width = type.width();
        height = type.height();
        dstRect.set(left, top, left + width, top + height);
        this.type = type;
        return this;
    }
    public boolean canPass() {
        return type == Type.T_3x1;
    }
}
