package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.Bitmap;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

// 쿠키런 스타일의 런 게임에서 사용되는 발판(바닥 타일) 객체를 정의한 클래스입니다.
// 각기 다른 형태의 바닥을 재사용(리사이클링)하고, 게임 내에서 충돌 판정이나 이동 판정에 활용
public class Floor extends MapObject {
    public enum Type {
        T_10x2, T_2x2, T_3x1;
        public static final int COUNT = values().length;

        Bitmap bitmap() { return BitmapPool.get(resId()); }
        // 타입에 해당하는 비트맵 반환

        int resId() { return resIds[this.ordinal()]; }
        // 리소스 ID 반환

        // 폭, 높이 반환
        int width() { return sizes[this.ordinal()][0]; }
        int height() { return sizes[this.ordinal()][1]; }


        // mipmap 리소스 ID
        static final int[] resIds = {
                R.mipmap.cookierun_floor_480x48,
                R.mipmap.cookierun_floor_124x120,
                R.mipmap.cookierun_floor_120x40,
        };

        // width, height 배열
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
        // 'O' → T_10x2, 'P' → T_2x2, 'Q' → T_3x1 바닥을 선택 가능
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
        // T_3x1 바닥만 통과 가능(true)
        return type == Type.T_3x1;
    }
}
