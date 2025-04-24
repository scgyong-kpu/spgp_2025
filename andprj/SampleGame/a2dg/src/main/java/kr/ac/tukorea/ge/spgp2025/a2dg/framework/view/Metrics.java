package kr.ac.tukorea.ge.spgp2025.a2dg.framework.view;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;


// 게임 좌표계와 화면(View) 좌표계 사이의 변환을 담당하는 유틸리티 클래스예요.
// 게임 화면이 다양한 해상도에서 올바른 비율로 보이도록 해주는 중요한 역할을 하죠
//
// 게임 좌표(900x1600)를
// 실제 핸드폰 해상도(예: 1080x2400 등)에 비율을 유지하면서 꽉 차게(또는 가운데 정렬되게) 보여주는 것.
//
// 1. 핸드폰의 화면 크기(w, h)를 받음
// 2. 게임 해상도(Metrics.width, Metrics.height)와 비교
// 3. 더 좁은 쪽 기준으로 스케일을 정함
// 4. 여백이 생기는 쪽은 가운데 정렬
// 5. 화면 비율이 유지된 상태로 스케일 행렬(transformMatrix) 설정
//
 //
 // 크기 관련 코드는 모드 Metrics에서 담당하도록 옮김-> 객체를 만들지 않고, static member만 가지도록 한다.
public class Metrics {
    private static final String TAG = Metrics.class.getSimpleName();
    public static float width = 900f;
    public static float height = 1600f;
    // 게임 좌표계: 게임 내부에서 사용하는 좌표 기준 (기본 width: 900, height: 1600)
    //
    // 뷰 좌표계 (스크린 좌표계): 실제 안드로이드 디바이스의 화면 픽셀 기준
    //
    //이 두 좌표계 사이를 **Matrix**를 이용해 변환해줍니다.

    public static final float GRID_UNIT = 100f;
    // 그리드 단위 크기 (게임에 따라 UI나 배치 단위로 사용 가능)
    public static final RectF borderRect = new RectF(0, 0, Metrics.width, Metrics.height);
    // 전체 게임 월드 영역을 담는 사각형
    public static final RectF screenRect = new RectF();
    // 변환된 실제 스크린 상의 게임 영역

    private static final Matrix transformMatrix = new Matrix();
    // 게임 좌표 → 화면 좌표 변환용 행렬
    private static final Matrix invertedMatrix = new Matrix();
    // 화면 좌표 → 게임 좌표 변환용 역행렬
    private static final float[] pointsBuffer = new float[2];
    // 좌표 변환을 위한 임시 배열 (성능 최적화용)


    // 게임 좌표계의 너비와 높이를 설정
    public static void setGameSize(float width, float height) {
        Metrics.width = width;
        Metrics.height = height;

        borderRect.right = width;
        borderRect.bottom = height;
    }

    // 실제 디바이스의 너비(w)와 높이(h)를 받아서 스크린 비율에 따라 스케일과 위치 조정
    // 가로/세로 비율에 따라 게임 화면이 찌그러지지 않도록 적절히 비율 유지하며 중앙 정렬
    public static void onSize(int w, int h) {

        float view_ratio = (float)w / (float)h;  // 예: 1080 / 2400 = 0.45
        float game_ratio = Metrics.width / Metrics.height;  // 900 / 1600 = 0.5625

        // 2. 화면이 더 세로로 길다 (view_ratio < game_ratio)
        // 여백이 생긴 쪽을 중앙으로 이동시키고, 짧은 쪽은 화면이 꽉 차도록 조정하는 방식
        //
        if (view_ratio > game_ratio) {
            float scale = h / Metrics.height;  // 1080 / 900 = 1.2
            // → 게임 크기(900x1600)에 이 스케일을 곱하면 실제 보여지는 크기 = 1080 x 1920
            // → 그런데 실제 높이는 2400이라 세로로 여백이 생김
            // → 그래서 세로 방향 중앙 정렬:

            transformMatrix.setTranslate((w - h * game_ratio) / 2, 0);
            // float topMargin = (h - height * scale) / 2; (2400 - 1600*1.2) / 2 = 240px
            transformMatrix.preScale(scale, scale);
        } else {
            float scale = w / Metrics.width;
            transformMatrix.setTranslate(0, (h - w / game_ratio) / 2);
            transformMatrix.preScale(scale, scale);
        }

        transformMatrix.invert(invertedMatrix);
        // transformMatrix: 게임 좌표 → 화면 좌표 로 바꾸는 행렬
        // invertedMatrix: 그 반대인 화면 좌표 → 게임 좌표 변환 행렬
        //👉 이걸로 터치 좌표를 게임 좌표로 바꿀 수 있어

        screenRect.set(0, 0, w, h);
        invertedMatrix.mapRect(screenRect);
        Log.d(TAG, "Screen Rect = " + screenRect);
    }

    // 스크린(화면) 좌표를 → 게임 좌표계로 변환
    // 예: 사용자가 화면을 터치했을 때, 그 위치를 게임 내부의 좌표로 변환
    public static float[] fromScreen(float x, float y) {
        pointsBuffer[0] = x;
        pointsBuffer[1] = y;

        invertedMatrix.mapPoints(pointsBuffer);
        return pointsBuffer;
    }

    // 게임 좌표계를 → 실제 스크린 좌표계로 변환
    public static float[] toScreen(float x, float y) {
        pointsBuffer[0] = x;
        pointsBuffer[1] = y;

        transformMatrix.mapPoints(pointsBuffer);
        return pointsBuffer;
    }
    // 💡 Matrix는 2D 변환(스케일, 회전, 이동 등)을 표현하는 클래스야.
    //mapPoints()는 그 변환을 실제 좌표에 적용해서,
    //
    //새로운 위치를 계산해줘

    // 현재 캔버스에 transformMatrix를 곱해줌으로써, 이후에 그리는 모든 그래픽이 게임 좌표 기준으로 되도록 합니다.
    // 캔버스에 transformMatrix를 적용해서 이후의 draw()들이 자동으로 게임 좌표 → 화면 좌표로 변환되게 해줌
    // ->  게임 논리적 좌표(예: 월드 좌표, 게임 내에서의 물체의 위치)가 화면 좌표(화면에 그려질 실제 위치)로 변환되어 그려지게 됩니다.
    public static void concat(Canvas canvas) {
        // concat은 일반적으로 **행렬 연산(Matrix transformation)**을 수행하는 메서드입니다.
        // 이 메서드는 그래픽스 관련 API에서 **좌표 변환을 결합(또는 합성)**하는 데 사용
        //
         // canvas.concat(matrix)는 canvas의 기존 변환 행렬에 다른 변환 행렬을 합성하는 작업을 합니다.
        canvas.concat(transformMatrix);
        // -> "행렬 합성"
    }
    // // 예시: matrix는 2D 변환 행렬
    //Matrix matrix = new Matrix();
    //matrix.setRotate(45);  // 45도 회전 변환 설정
    //canvas.concat(matrix); // 기존 canvas 변환 행렬에 회전 변환을 합성
}
