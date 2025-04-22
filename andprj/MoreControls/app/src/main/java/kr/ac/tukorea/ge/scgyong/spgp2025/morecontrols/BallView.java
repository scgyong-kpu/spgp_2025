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
    private Bitmap ballBitmap, bgBitmap; // 축구공 이미지 저장할 '비트맵 객체'
    private static final float SCREEN_WIDTH = 9.0f;
    private static final float SCREEN_HEIGHT = 16.0f; // 게임 화면의 가로/세로 비율을 설정한 상수
    private final RectF ballRect = new RectF(4.0f, 7.5f, 5.0f, 8.5f);
    private final RectF bgRect = new RectF(0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT);
    // 각각 축구공과 배경 이미지의 '위치와 크기'를 정의한 RectF 객체
    private final Matrix transformMatrix = new Matrix();
    // 화면 비율에 맞게 이미지를 변형하기 위해 사용하는 '변환 행렬(Matrix)'


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

        // setSystemUiVisibility(): 이 코드로 시스템 UI(상태바, 네비게이션 바)를 숨겨서 전체 화면 모드로 전환해.
        setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
        // NavigationBar 를 없앤다

        Resources res = getResources();
        // 이건 앱의 리소스(=리소스 폴더의 이미지 등)를 Bitmap 객체로 불러오는 코드야.
        // getResources()는 **현재 Context(=현재 화면, Activity 등)**에서 앱의 리소스에 접근할 수 있게 해주는 메소드

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        // BitmapFactory.decodeResource():
        // ballBitmap과 bgBitmap에 각각 축구공과 배경 이미지를 로드해.
        //
        // inScaled = false 옵션을 설정해서 이미지를 자동으로 크기 조정하지 않게 해.
        //
         // BitmapFactory.decodeResource()는 이미지 리소스를 읽어서 Bitmap 객체로 변환해주는 함수
        ballBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240, options);
        bgBitmap = BitmapFactory.decodeResource(res, R.mipmap.block_9x16, options);
    }


    // 뷰의 크기가 바뀔 때마다 호출된다
    // 뷰의 크기(w, h)에 맞춰서 **이미지 비율을 맞추고 변환 행렬**을 설정한다.
    //
    // w, h: View의 실제 픽셀 크기 (onSizeChanged에서 전달됨)
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float view_ratio = (float)w / (float)h; // 현재 뷰의 가로 세로 비율
        float game_ratio = SCREEN_WIDTH / SCREEN_HEIGHT; // 정의한 게임 화면의 가로 세로 비율 (9/16)

        // 게임 화면 뷰를 9:16이라고 했을 때
        //
        // 만약 뷰의 비율이 게임화면비율보다 크면, 세로 크기에 맞게 이미지를 스케일하고,
        // 나머지 공간은 좌우 여백으로 맞추고, 반대로 가로 크기에 맞춰 이미지를 스케일하고 상하 여백을 맞춘다.
        transformMatrix.reset();
        if (view_ratio > game_ratio) {
            // 뷰의 비율이 게임보다 넓다 ---> 가로가 남고 세로가 꽉 찰 것이다.

            float scale = h / SCREEN_HEIGHT;
            // -> 세로 기준으로 맞춰서 확대/축소할 비율을 정한다.

            transformMatrix.setTranslate((w - h * game_ratio) / 2, 0);
            // 가로가 남기 때문에, 양쪽 여백의 반만큼 왼쪽으로 이동해서 가운데 정렬

            transformMatrix.preScale(scale, scale);
            // scale만큼 전체 좌표계 확대 ( 게임 좌표계 -> 화면 좌표계 )
        } else {
            float scale = w / SCREEN_WIDTH;
            // → 가로 기준으로 맞춰서 확대/축소할 비율을 정함.

            transformMatrix.setTranslate(0, (h - w / game_ratio) / 2);
            // → 세로가 남기 때문에, 위아래 여백의 반만큼 아래로 이동해서 가운데 정렬시킴.

            transformMatrix.preScale(scale, scale);
        }
    }


    // 뷰가 그려질 때 호출된다 ( 배경, 공 이미지를 그려준다 )
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.concat(transformMatrix);
        // 변환 행렬을 캔버스에 적용하여 이미지를 적절하게 변형
        //
        // setMatrix()를 사용하는 대신, 캔버스의 현재 행렬과 지정된 행렬을 결합하여 새로운 변환을 적용할 수 있는
        // concat() 메소드를 사용하는 것이 더 직관적일 수 있습니다. 이 메소드는 캔버스의 현재 행렬과 지정된 행렬을
        // 연결(concatenate)합니다. 이는 이후의 그리기 작업에 결합된 변환을 적용합니다. 이 접근 방식은 현재 상태의
        // 캔버스에 추가 변환을 적용하고자 할 때 종종 더 직관적입니다.
        // 이미지를 해당 위치와 크기에 맞춰 그린다

        canvas.drawBitmap(bgBitmap, null, bgRect, null);
        canvas.drawBitmap(ballBitmap, null, ballRect, null);
        canvas.restore();
    }
}

// Avoid object allocations during draw/ layout operations
// onDraw()에서는 매번 새로운 객체(RectF 등)를 생성하지 말자는 뜻
// 메모리 성능상 좋지 않기 때문에,
//        **미리 만들어둔 객체를 재사용하는 방식(Lazy Initialization)**이 권장돼.
//        ✔ 예를 들어, RectF ballRect를 클래스 멤버로 선언하고 onDraw()에서 재사용하는 게 더 좋은 방식이야.