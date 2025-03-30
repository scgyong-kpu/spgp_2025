package kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BallView extends View {
    private static final String TAG = BallView.class.getSimpleName();
    private Bitmap ballBitmap, bgBitmap;
    private static final float SCREEN_WIDTH = 9.0f;
    private static final float SCREEN_HEIGHT = 16.0f;
    private final RectF ballRect = new RectF(4.0f, 7.5f, 5.0f, 8.5f);
    private final RectF bgRect = new RectF(0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT);
    private final Matrix transformMatrix = new Matrix();
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

        setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        // NavigationBar 를 없앤다

        Resources res = getResources();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        ballBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240, options);
        bgBitmap = BitmapFactory.decodeResource(res, R.mipmap.block_9x16, options);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float view_ratio = (float)w / (float)h;
        float game_ratio = SCREEN_WIDTH / SCREEN_HEIGHT;

        transformMatrix.reset();
        if (view_ratio > game_ratio) {
            float scale = h / SCREEN_HEIGHT;
            transformMatrix.setTranslate((w - h * game_ratio) / 2, 0);
            transformMatrix.preScale(scale, scale);
        } else {
            float scale = w / SCREEN_WIDTH;
            transformMatrix.setTranslate(0, (h - w / game_ratio) / 2);
            transformMatrix.preScale(scale, scale);
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.concat(transformMatrix);
        canvas.drawBitmap(bgBitmap, null, bgRect, null);
        canvas.drawBitmap(ballBitmap, null, ballRect, null);
        canvas.restore();
    }
}
