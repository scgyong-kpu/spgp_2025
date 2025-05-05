package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.animation.ValueAnimator;
import android.view.animation.BounceInterpolator;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class FallingObstacle extends Obstacle {
    private static final int RES_ID = R.mipmap.epn01_tm01_sda;
    private static final String TAG = FallingObstacle.class.getSimpleName();

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

        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setDuration(2000);
        animator.setStartDelay(1000);
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            dstRect.offsetTo(dstRect.left, value);
        });
        animator.start();

        return this;
    }
}
