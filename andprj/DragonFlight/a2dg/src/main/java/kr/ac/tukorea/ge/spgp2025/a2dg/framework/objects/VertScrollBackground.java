package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class VertScrollBackground extends Sprite {
    private final float speed;
    private final float height;
    // 화면 비율에 맞춰 계산된 비트맵의 높이입니다.
    //→ 비트맵 원본의 높이를 Metrics.width 기준으로 스케일링
    public VertScrollBackground(int bitmapResId, float speed) {
        super(bitmapResId);
        this.height = bitmap.getHeight() * Metrics.width / bitmap.getWidth();
        // 비트맵의 가로세로 비율을 유지하면서 너비를 화면 너비에 맞추고, 거기에 맞는 높이를 계산하는 코드

        setPosition(Metrics.width / 2, Metrics.height / 2, Metrics.width, height);
        // 배경의 중앙을 화면 중앙에 맞추고, 화면 전체 너비와 계산된 높이로 크기를 지정
        this.speed = speed;
    }
    @Override
    public void update() {
        //  스크롤된 거리 = 속도 × 시간
        // 스크롤된 양의 누적값
        this.y += speed * GameView.frameTime; // y 값을 스크롤된 양으로 사용한다
    }

    @Override
    public void draw(Canvas canvas) {
        //super.draw(canvas);
        float curr = y % height;
        // y 값을 height로 나눈 나머지를 이용해 현재 화면에 어디서부터 배경을 그릴지 결정
        // curr은 현재 배경의 시작 y 좌표

        // curr > 0인 경우 -height로 보정해서 항상 배경이 위에서부터 자연스럽게 이어지도록 만듭니다.
        if (curr > 0) curr -= height;

        while (curr < Metrics.height) {
            dstRect.set(0, curr, Metrics.width, curr + height);
            // curr 위치부터 아래로 비트맵을 그리며, curr + height가 화면을 넘을 때까지 반복
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += height;
        }
    }
}
