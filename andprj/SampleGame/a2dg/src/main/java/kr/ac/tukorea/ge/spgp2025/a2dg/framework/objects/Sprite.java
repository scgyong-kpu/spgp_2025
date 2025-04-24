package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.RectUtil;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;


// Sprite는 게임 화면에 그릴 수 있는 그래픽 오브젝트를 표현해
// IGameObject를 구현해서 게임 루프에서 update()와 draw()가 호출될 수 있어
public class Sprite implements IGameObject {
    protected Bitmap bitmap;
    // 실제로 화면에 그릴 이미지
    protected Rect srcRect = null;
    // 비트맵의 어느 부분을 그릴지 설정 (null이면 전체 사용)
    protected final RectF dstRect = new RectF();
    // 비트맵을 실제로 화면에 그릴 위치와 크기 (float로 정밀함)

    protected float x, y, dx, dy;
    // x,y 객체 중심 좌표
    // dx,dy 이동 속도 (프레임 시간과 곱해서 이동 거리 계산)
    protected float width, height, radius;
    // 객체의 크기
    // 중심 기준 반지름

    // 비트맵의 스케일링 비율 (디폴트 1.0f, 즉 크기를 변경하지 않음)
    protected float scaleX = 1.0f;
    protected float scaleY = 1.0f;


    public Sprite(int mipmapId) {
        // BitmapPool은 메모리 낭비를 줄이기 위해 이미지 재사용하는 시스템.
        // mipmapId는 이미지 리소스 ID (R.mipmap.soccer_ball_240 같은 것)
        if (mipmapId != 0) {
            bitmap = BitmapPool.get(mipmapId);
        }
    }

    // 1. 중심 좌표와 반지름으로 설정 -> 주로 원형 객체
    public void setPosition(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.width = this.height = 2 * radius;

        RectUtil.setRect(dstRect, x, y, radius);
        // 중심 좌표를 기준으로 크기를 계산해서 dstRect 설정.
    }

    // 2. 중심 좌표와 너비/높이로 설정 -> 주로 사각형 객체
    public void setPosition(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        radius = Math.min(width, height) / 2;
        // 반지름은 가로, 세로 중 더 짧은 쪽의 반절로 계산

        RectUtil.setRect(dstRect, x, y, width, height);
    }

    // 3. 스케일을 적용하는 메서드 추가
    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;

        // scaleRect를 사용하여 dstRect의 크기를 변경
        RectUtil.scaleRect(dstRect, scaleX, scaleY);
    }

    @Override
    public void update() {
        float timedDx = dx * GameView.frameTime;
        float timedDy = dy * GameView.frameTime;

        x += timedDx;
        y += timedDy;
        // 현재 속도에 프레임 시간을 곱해서 실제 이동 거리 계산.
        // 위치 좌표와 dstRect를 동시에 이동시켜서 자연스럽게 움직임 처리.

        dstRect.offset(timedDx, timedDy);
        // 내가 넣어준 변화량(timedDx, timedDy)만큼 단순히 사각형을 옮겨주는 함수
    }

    // 비트맵 이미지를 화면에 그리는 부분.
    // srcRect가 null이면 비트맵 전체를 사용.
    // dstRect 위치에 그려짐
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
