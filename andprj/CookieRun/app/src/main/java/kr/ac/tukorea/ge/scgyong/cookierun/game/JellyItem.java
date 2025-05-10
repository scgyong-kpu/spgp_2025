package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class JellyItem extends MapObject {
    public static final int JELLY_COUNT = 60;
    private static final int ITEMS_IN_A_ROW = 30;
    private static final int SIZE = 66;
    private static final int BORDER = 2;
    public int index;
    private static final int[] SOUND_IDS = {
            R.raw.jelly,
            R.raw.jelly_alphabet,
            R.raw.jelly_item,
            R.raw.jelly_gold,
            R.raw.jelly_coin,
            R.raw.jelly_big_coin,
    };
    public JellyItem() {
        super(MainScene.Layer.item);
        bitmap = BitmapPool.get(R.mipmap.jelly);
        srcRect = new Rect();
        width = height = 100;
        collisionRect = new RectF();
    }
    public static JellyItem get(int index, float left, float top) {
        return Scene.top().getRecyclable(JellyItem.class).init(index, left, top);
        //return new JellyItem().init(index, left, top);
    }
    public static JellyItem get(char mapChar, float left, float top) {
        if (mapChar == '@') {
            return get(26, left, top); // 26=계란모양
        }
        if (mapChar < '1' || mapChar >= '9') return null;
        return get(mapChar - '1', left, top);
    }
    public JellyItem init(int index, float left, float top) {
        this.index = index;
        setSrcRect(index);
        dstRect.set(left, top, left + width, top + height);
        return this;
    }
    private void setSrcRect(int index) {
        int x = index % ITEMS_IN_A_ROW;
        int y = index / ITEMS_IN_A_ROW;
        int left = x * (SIZE + BORDER) + BORDER;
        int top = y * (SIZE + BORDER) + BORDER;
        srcRect.set(left, top, left + SIZE, top + SIZE);
    }

    @Override
    public void update() {
        super.update();
        updateCollisionRect(0.15f);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    public int getSoundResId() {
        return SOUND_IDS[index % SOUND_IDS.length];
    }
}
