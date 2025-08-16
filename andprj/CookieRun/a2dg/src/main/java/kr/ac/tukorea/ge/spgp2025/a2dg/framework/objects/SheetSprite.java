package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.graphics.Canvas;
import android.graphics.Rect;


// 스프라이트 시트 이미지에서 프레임(Rect)을 나눠 저장
// 현재 시간 기준으로 어떤 프레임을 그릴지 계산
//
// AnimSprite: 기본적인 애니메이션 스프라이트 클래스 (이미지, 프레임 속도, 크기 등 보유)
// SheetSprite: 그걸 좀 더 구체적으로 확장하여 스프라이트 시트 기반으로 렌더링
public class SheetSprite extends AnimSprite {
    protected Rect[] srcRects;
    // 이미지 안의 프레임들을 미리 Rect 배열로 잘라 저장
    // 예: 한 이미지에 프레임 4개가 있으면 srcRects[0~3]은 각각 그 위치

    public SheetSprite(int mipmapResId, float fps) {
        super(mipmapResId, fps);
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        // → 객체 생성 후 몇 초가 지났는지를 초 단위로 계산

        int index = Math.round(time * fps) % srcRects.length;
        // → 초당 fps 프레임 속도로 어느 프레임을 보여줄지를 계산

        canvas.drawBitmap(bitmap, srcRects[index], dstRect, null);
        // → bitmap 이미지에서 해당 프레임만 골라, dstRect 위치에 그림
    }
}
