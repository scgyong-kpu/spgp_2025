package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;


// 게임 점수를 화면에 숫자 이미지로 표시하는 역할을 하는 코드
// 점수를 점진적으로(애니메이션처럼) 변화시키며 화면에 그리기
public class Score implements IGameObject {
    private final Bitmap bitmap;
    private final float right, top, dstCharWidth, dstCharHeight;
    private final Rect srcRect = new Rect();
    private final RectF dstRect = new RectF();
    private final int srcCharWidth, srcCharHeight;
    private int score, displayScore;
    // 화면에 보여지는 점수 값 (실제 점수와 점진적으로 맞춰짐, 애니메이션 효과)

    public Score(int mipmapId, float right, float top, float width) {
        this.bitmap = BitmapPool.get(mipmapId);
        // bitmap : 0~9 숫자가 가로로 나열된 이미지 (숫자 10개가 한 줄로 이어진 비트맵)

        this.right = right;
        this.top = top;
        // 점수를 표시할 위치 (숫자 오른쪽 끝 x좌표, y좌표)

        // 화면에 그릴 각 숫자의 너비와 높이
        this.dstCharWidth = width;
        this.srcCharWidth = bitmap.getWidth() / 10;

        // 비트맵에서 잘라낼 숫자의 위치(소스 영역)
        this.srcCharHeight = bitmap.getHeight();
        this.dstCharHeight = dstCharWidth * srcCharHeight / srcCharWidth;
    }

    public void setScore(int score) {
        this.score = this.displayScore = score;
    }

    public void add(int amount) {
        score += amount;
    }

    // score와 화면에 표시할 displayScore를 비교해서 점수 변화가 부드럽게 일어나도록 조절
    // 차이가 적으면 1씩 증가/감소, 차이가 크면 1/10씩 증가/감소
    @Override
    public void update() {
        int diff = score - displayScore;
        if (diff == 0) return;
        if (-10 < diff && diff < 0) {
            displayScore--;
        } else if (0 < diff && diff < 10) {
            displayScore++;
        } else {
            displayScore += diff / 10;
        }
    }

    // 점수를 10으로 나눈 나머지로 한 자리씩 분리
    // 각 숫자에 맞는 이미지를 비트맵에서 srcRect로 잘라내서 dstRect 위치에 그림
    // 오른쪽부터 차례대로 숫자를 왼쪽으로 그려나감 (x -= dstCharWidth)
    @Override
    public void draw(Canvas canvas) {
        int value = this.displayScore;
        float x = right;
        while (value > 0) {
            int digit = value % 10;
            srcRect.set(digit * srcCharWidth, 0, (digit + 1) * srcCharWidth, srcCharHeight);
            x -= dstCharWidth;
            dstRect.set(x, top, x + dstCharWidth, top + dstCharHeight);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            value /= 10;
        }
    }

    public int getScore() {
        return score;
    }
}
