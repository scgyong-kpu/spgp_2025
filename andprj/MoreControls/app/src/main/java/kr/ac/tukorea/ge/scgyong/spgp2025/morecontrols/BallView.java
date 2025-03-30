package kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BallView extends View {
    private static final String TAG = BallView.class.getSimpleName();
    private Bitmap bitmap;
    private RectF ballRect = new RectF();

    public BallView(Context context) {
        super(context);
        // Java 코드로부터 new BallView 했을 때 불린다
        init(null, 0);
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // layout xml 로부터 로드될때 속성과 함께 불린다
        init(attrs, 0);
    }

    public BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // layout xml 로부터 로드될때 속성 및 style 과 함께 불린다
        init(attrs, defStyleAttr);
    }
    private void init(AttributeSet attrs, int defStyleAttr) {
        // 모든 Constructor 들이 이곳을 통과하게 만들자.
        Resources res = getResources();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        bitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240, options);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float cx = w / 2.0f, cy = h / 2.0f;
        float ballRadius = w / 10.0f; // 화면폭의 1/10 이 되게 한다
        ballRect.set(cx - ballRadius, cy - ballRadius, cx + ballRadius, cy + ballRadius);
        Log.d(TAG, "Ball dest size=" + ballRect);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        float cx = getWidth() / 2.0f;
        float cy = getHeight() / 2.0f;

        canvas.drawBitmap(bitmap, null, ballRect, null);
    }
}
