package kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BallView extends View {
    private static final String TAG = BallView.class.getSimpleName();
    private Bitmap bitmap;
    private final RectF ballRect = new RectF(0.45f, 0.45f, 0.55f, 0.55f);
    private final PointF transformOffset = new PointF();
    private float transformScale;

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
        if (w < h) {
            transformOffset.set(0, (h - w) / 2.0f);
            transformScale = w;
        } else {
            transformOffset.set((w - h) / 2.0f, 0);
            transformScale = h;
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(transformOffset.x, transformOffset.y);
        canvas.scale(transformScale, transformScale);
        canvas.drawBitmap(bitmap, null, ballRect, null);
        canvas.restore();
    }
}
