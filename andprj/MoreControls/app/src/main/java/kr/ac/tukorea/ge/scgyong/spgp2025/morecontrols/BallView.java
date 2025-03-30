package kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
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

        if (view_ratio > game_ratio) {
            float scale = h / SCREEN_HEIGHT;
            transformMatrix.postScale(scale, scale);
            transformMatrix.postTranslate((w - h * game_ratio) / 2, 0);
        } else {
            float scale = w / SCREEN_WIDTH;
            transformMatrix.postScale(scale, scale);
            transformMatrix.postTranslate(0, (h - w / game_ratio) / 2);
        }
        // 변환(이동, 확대/축소, 회전 등)을 적용하는 순서는 결과에 큰 영향을 미칩니다. 왜냐하면 행렬 연산은 교환 법칙이 적용되지 않기 때문입니다.
        // 순서가 중요합니다. Canvas의 메소드들을 직접 사용할 때, 예를 들어 canvas.translate() 다음에 canvas.scale()을 호출하는 것과 같이
        // 특정 순서로 변환을 적용하면, 캔버스의 현재 변환 행렬에 변환이 누적됩니다. Matrix 객체를 직접 사용하여 변환을 적용할 때는,
        // Canvas 메소드를 사용할 때와 같은 시각적 결과를 얻기 위해 사용했던 연산의 순서를 반대로 적용하는 것이 중요합니다.

        // Matrix를 사용하여 같은 효과를 얻으려면, 그리기 시 행렬 연산이 적용되는 방식 때문에 연산의 순서를 뒤집어야 합니다.
        // 즉, 확대/축소를 행렬에 먼저 적용한 다음 이동을 적용합니다. 그러나 행렬 연산의 특성상 이렇게 하면 이동이 확대된 좌표 공간에서
        // 실제로 발생하게 되어, 캔버스 연산을 직접 사용했을 때의 기대했던 동작과 직접 일치하지 않을 수 있습니다.

        // 이 조정이 필요한 이유는 Matrix를 직접 사용할 때 먼저 확대/축소를 적용하면, 그 다음에 적용되는 이동은 확대된 좌표 공간에서 이루어집니다.
        // 확대/축소는 이동이 요소를 이동시키는 "거리"를 줄이거나 늘립니다. 확대/축소로 인한 시각적 효과가 canvas.translate() 다음에
        // canvas.scale()을 사용할 때와 유사하게 되도록 이동 값(transX, transY에 scale을 조정함으로써 이를 보상합니다.
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.concat(transformMatrix);
        canvas.drawBitmap(bgBitmap, null, bgRect, null);
        canvas.drawBitmap(ballBitmap, null, ballRect, null);
        canvas.restore();
        // setMatrix()를 사용하는 대신, 캔버스의 현재 행렬과 지정된 행렬을 결합하여 새로운 변환을 적용할 수 있는
        // concat() 메소드를 사용하는 것이 더 직관적일 수 있습니다. 이 메소드는 캔버스의 현재 행렬과 지정된 행렬을
        // 연결(concatenate)합니다. 이는 이후의 그리기 작업에 결합된 변환을 적용합니다. 이 접근 방식은 현재 상태의
        // 캔버스에 추가 변환을 적용하고자 할 때 종종 더 직관적입니다.
    }
}
