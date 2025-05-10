package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.animation.ValueAnimator;
import android.graphics.RectF;
import android.view.animation.BounceInterpolator;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class FallingObstacle extends Obstacle {
    private static final int RES_ID = R.mipmap.epn01_tm01_sda;
    private static final String TAG = FallingObstacle.class.getSimpleName();
    private ValueAnimator animator;

    public FallingObstacle() {
        setImageResourceId(RES_ID);
    }

    public static Obstacle get(float left, float top) {
        return Scene.top().getRecyclable(FallingObstacle.class).init(left, top);
    }

    private Obstacle init(float left, float top) {
        setObstaclePosition(left, top);
        float end = dstRect.top - 100; // slide 할 공간을 마련해 주기 위해 100 올린다.
        dstRect.offset(0, -dstRect.height());
        float start = dstRect.top;

        initAnimator(); // 생성이 안 되어 있다면 생성한다
        animator.setFloatValues(start, end); // 시작/끝 값을 지정한다
        animator.start(); // 시작한다

        return this;
    }

    private void initAnimator() {
        if (animator != null) return;
        // 모든 FallingObstacle 객체가 동일하게 적용받는 항목은 이곳에 적는다
        animator = new ValueAnimator();
        animator.setDuration(2000);
        animator.setStartDelay(1000);
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(animListener);
    }

    private final ValueAnimator.AnimatorUpdateListener animListener = (ValueAnimator anim) -> {
        float value = (float) anim.getAnimatedValue();
        dstRect.offsetTo(dstRect.left, value);
        //Log.v(TAG, "Animated Value = " + value + " obj=" + this);
    };

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

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
