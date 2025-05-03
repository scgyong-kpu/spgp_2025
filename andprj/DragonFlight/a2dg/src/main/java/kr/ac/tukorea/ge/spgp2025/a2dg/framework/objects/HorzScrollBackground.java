package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class HorzScrollBackground extends Sprite {
    private final float speed;
    private final float width;
    // 비트맵을 화면 높이에 맞춰 스케일링한 후의 너비


    public HorzScrollBackground(int bitmapResId, float speed) {
        super(bitmapResId);
        this.width = bitmap.getWidth() * Metrics.height / bitmap.getHeight();
        // 이미지의 가로세로 비율을 유지하면서, 화면 높이에 맞게 스케일 조정된 너비를 계산
        // 비트맵의 세로 길이를 화면 높이(Metrics.height)에 맞게 스케일 조정하면서
        // 비율 유지를 위해 가로도 같이 조정함
        //
        // 원래 이미지 크기: 1000 x 500
        // 화면 높이: 1080
        // 스케일링된 너비 = 1000 * 1080 / 500 = 2160

        setPosition(-Metrics.width, 0, width, Metrics.height);
        // 배경의 중심 위치, 크기를 설정
        //
        // x = -Metrics.width: 처음에 화면 왼쪽 바깥에서 시작하도록 설정
        // y = 화면의 중간: 화면 수직 가운데 정렬
        // width: 계산한 스케일링된 배경 너비
        // Metrics.height: 화면 전체 높이
        this.speed = speed;
    }
    @Override
    public void update() {
        this.x -= speed * GameView.frameTime;
        // speed = 200, frameTime = 0.016초 (약 60FPS)
        // → this.x는 프레임마다 3.2픽셀씩 줄어듦
    }

    @Override
    public void draw(Canvas canvas) {
        //super.draw(canvas);
        float curr = x % width;
        // 현재 x 좌표를 배경 이미지의 너비로 나눈 나머지
        // 어디서부터 이미지를 그리기 시작할지
        //
        // x = -50, width = 200 → curr = -50
        // x = -250, width = 200 → curr = -50
        // → 이미지가 한 칸 넘게 넘어가도 잘 이어짐

        if (curr < 0) curr -= width;
        // curr이 음수일 때는 왼쪽에서 한 칸 더 당겨서 그리게 조정

        while (curr < Metrics.width) {
            // curr부터 시작해서 화면 오른쪽 끝(Metrics.width)까지 반복해서 그림
            // 배경이 짧아서 한 번으로 안 덮을 수 있으니까 여러 번 그림

            dstRect.set(curr, 0, curr + width, Metrics.height);
            // 그릴 영역(Rect)을 설정:
            // 시작 x좌표: curr
            // 끝 x좌표: curr + width
            // 위: 0 / 아래: 화면 높이
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += width;
        }
    }
}
