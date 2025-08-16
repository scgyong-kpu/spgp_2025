package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

// 점수 획득, 효과 발동, 아이템 수집 등의 목적으로 등장하며,
// 이 클래스는 젤리의 외형 처리, 충돌 영역 설정, 효과음 매핑
public class JellyItem extends MapObject {
    public static final int JELLY_COUNT = 60;
    // 젤리 종류 수 (실제 스프라이트에서 총 60개 젤리 그림이 있음)

    private static final int ITEMS_IN_A_ROW = 30;
    // 스프라이트 시트에서 한 줄에 들어있는 젤리 개수

    private static final int SIZE = 66;
    // 각 젤리 이미지의 원본 크기 (px)

    private static final int BORDER = 2;
    // 이미지 사이 간격 (픽셀 여백)

    public int index;
    // Jelly의 종류를 인덱스로 구분하며,
    // 비트맵은 하나로 묶여 있음 (jelly.png 스프라이트 시트)

    // 젤리 종류에 따라 다르게 나오는 효과음 ID 배열
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
        // '1'~'8' → 일반 젤리
        // '@' → 계란 젤리(특수 젤리, index 26)

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

    // 스프라이트 시트에서 index에 해당하는 젤리 그림 부분만 잘라서 srcRect에 지정함
    private void setSrcRect(int index) {
        int x = index % ITEMS_IN_A_ROW; // x좌표 인덱스
        int y = index / ITEMS_IN_A_ROW; // y좌표 인덱스

        int left = x * (SIZE + BORDER) + BORDER;
        int top = y * (SIZE + BORDER) + BORDER;

        srcRect.set(left, top, left + SIZE, top + SIZE);
    }

    // 젤리마다 충돌 범위를 줄이기 위해 15% 크기로
    // collisionRect 설정 (좀 더 부드럽게 수집되도록)
    @Override
    public void update() {
        super.update();
        updateCollisionRect(0.15f);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    // 젤리 인덱스에 따라 적절한 효과음을 매핑
    public int getSoundResId() {
        return SOUND_IDS[index % SOUND_IDS.length];
    }
    // 인덱스	효과음 종류
    //0	일반 젤리 (jelly)
    //1	알파벳 젤리 (jelly_alphabet)
    //2	아이템 젤리 (jelly_item)
    //3	황금 젤리 (jelly_gold)
    //4	코인 젤리 (jelly_coin)
    //5	빅 코인 (jelly_big_coin)
}
