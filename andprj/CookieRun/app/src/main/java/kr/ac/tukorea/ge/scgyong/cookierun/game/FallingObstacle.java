package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.animation.ValueAnimator;
import android.graphics.RectF;
import android.view.animation.BounceInterpolator;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

// 화면 위에서 떨어지는(슬라이드 후 바운스 되는) 장애물을 구현한 클래스
public class FallingObstacle extends Obstacle {
    private static final int RES_ID = R.mipmap.epn01_tm01_sda;
    private static final String TAG = FallingObstacle.class.getSimpleName();
    private ValueAnimator animator;
    // animator : ValueAnimator 객체, 장애물의 y좌표 애니메이션 제어용


    // 재사용 가능한 객체 풀에서 FallingObstacle 인스턴스를 얻고 초기화
    public FallingObstacle() {
        setImageResourceId(RES_ID);
    }

    public static Obstacle get(float left, float top) {
        return Scene.top().getRecyclable(FallingObstacle.class).init(left, top);
    }

    // dstRect를 위로 화면 밖으로 이동시켜 떨어지는 위치 설정
    // 애니메이터 초기화 및 애니메이션 시작
    private Obstacle init(float left, float top) {
        setObstaclePosition(left, top);
        float end = dstRect.top - 100; // slide 할 공간을 마련해 주기 위해 100 올린다.
        dstRect.offset(0, -dstRect.height()); // 화면 위로 장애물 위치 이동
        float start = dstRect.top;

        initAnimator(); // 생성이 안 되어 있다면 생성한다
        animator.setFloatValues(start, end); // 시작/끝 값을 지정한다
        animator.start(); // 시작한다

        return this;
    }


    // 애니메이터 생성 및 설정
    // BounceInterpolator로 떨어지다가 튕기는 자연스러운 효과 구현
    // 업데이트 리스너 연결
    private void initAnimator() {
        if (animator != null) return;
        // 모든 FallingObstacle 객체가 동일하게 적용받는 항목은 이곳에 적는다
        animator = new ValueAnimator();
        animator.setDuration(2000);
        animator.setStartDelay(1000);
        animator.setInterpolator(new BounceInterpolator());
        // BounceInterpolator로 떨어지다가 튕기는 자연스러운 효과 구현
        animator.addUpdateListener(animListener);
    }

    // 애니메이션 값에 따라 장애물의 y좌표 이동 (dstRect 갱신)
    private final ValueAnimator.AnimatorUpdateListener animListener = (ValueAnimator anim) -> {
        float value = (float) anim.getAnimatedValue();
        dstRect.offsetTo(dstRect.left, value);
        //Log.v(TAG, "Animated Value = " + value + " obj=" + this);
    };

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    // 재활용 시 애니메이터 종료
    @Override
    public void onRecycle() {
        super.onRecycle();
        animator.end();
    }

    @Override
    public void pause() {
        animator.pause();
    }

    @Override
    public void resume() {
        animator.resume();
    }
}
