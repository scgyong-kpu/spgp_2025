package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;


// 움직이는(프레임 전환되는) 장애물을 표현하기 위한 객체
// Obstacle을 상속 → 기본적인 위치, 충돌 판정, 그리기 방식 사용
// AnimObstacle은 프레임 단위로 비트맵을 교체하는 애니메이션 기능을 추가함
public class AnimObstacle extends Obstacle {
    public static Obstacle get(int type, float left, float top) {
        return Scene.top().getRecyclable(AnimObstacle.class).init(type, left, top);
    }

    private static final float FPS = 8.0f;
    // 	초당 프레임 수 (8.0f)

    private int resIndex;
    // 어떤 애니메이션 타입인지 식별하는 인덱스

    private float time = 0;

    // 애니메이션 프레임에 해당하는 리소스 배열들
    private static final int[][] RES_ID_ARRAYS = new int[][] {
            new int[] {
                    R.mipmap.epn01_tm01_jp1up_01,
                    R.mipmap.epn01_tm01_jp1up_02,
                    R.mipmap.epn01_tm01_jp1up_03,
                    R.mipmap.epn01_tm01_jp1up_04,
            },
            new int[]{
                    R.mipmap.epn01_tm01_jp2up_01,
                    R.mipmap.epn01_tm01_jp2up_02,
                    R.mipmap.epn01_tm01_jp2up_03,
                    R.mipmap.epn01_tm01_jp2up_04,
                    R.mipmap.epn01_tm01_jp2up_05,
            },
    };

    // 장애물별 충돌 판정 영역 비율 배열
    private static final float[][] COLLISION_INSETS_ARRAYS = {
            { 0.2f, 0.6f, 0.2f, 0.0f },
            { 0.2f, 0.4f, 0.2f, 0.0f },
    };

    private Obstacle init(int type, float left, float top) {
        resIndex = type;
        // resIndex에 애니메이션 종류 저장

        time = 0;
        setImageResourceId(RES_ID_ARRAYS[resIndex][0]);
        setObstaclePosition(left, top);
        // 위치 설정 (setObstaclePosition)

        // 처음엔 R.mipmap.trans_00p 로 빈 이미지를 설정
        // → 이후 update()에서 실제 비트맵이 설정됨
        setImageResourceId(R.mipmap.trans_00p);
        collisionInsets = COLLISION_INSETS_ARRAYS[resIndex];
        return this;
    }

    @Override
    public void update() {
        super.update();
        // dstRect.left가 1000보다 클 경우(즉, 오른쪽에 있어 화면에 안 보이는 상태)에는
        // 프레임 갱신 생략 (성능 최적화)

        if (dstRect.left >= 1000f) return;
        // x 좌표가 어느정도 줄었을 때에만 시간합산을 진행한다
        // x 는 사용하지 않으니 dstRect.left 로 체크한다

        // frameIndex는 시간 기반으로 계산
        // 예: 0.5초 경과 → 0.5 * 8 = 4 → 프레임 4
        time += GameView.frameTime;

        // 장애물 타입별로 여러 프레임의 비트맵 ID가 들어 있음
        // resIndex가 0이면 첫 번째 배열 사용
        int[] resIds = RES_ID_ARRAYS[resIndex];
        int frameIndex = Math.round(time * FPS);
        if (frameIndex >= resIds.length) {
            frameIndex = resIds.length - 1; // 마지막 프레임으로 유지한다.
        }
        int resId = resIds[frameIndex];
        bitmap = BitmapPool.get(resId);
    }
}

