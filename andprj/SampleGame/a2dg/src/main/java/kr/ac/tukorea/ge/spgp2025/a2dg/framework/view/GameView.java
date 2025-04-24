package kr.ac.tukorea.ge.spgp2025.a2dg.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

// GameView 클래스는 안드로이드에서 직접 게임 루프를 만드는 기본 뼈대
// GameView는 android.view.View를 상속한 커스텀 뷰
// Choreographer.FrameCallback을 구현해서 프레임마다 호출되는 doFrame() 게임 루프를 직접 구성
// Scene 기반 스택 구조로 씬을 전환하거나 스택에서 꺼내는 구조
public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    private static long previousNanos;
    public static float frameTime;
    // frameTime은 (이번 프레임 시간 - 이전 프레임 시간)으로 **1프레임이 걸린 시간(초 단위)**를 저장

    public static GameView view;
    // 싱글톤처럼 이 GameView 인스턴스를 어디서든 접근하기 위한 static 변수.
    public static boolean drawsDebugStuffs = false;
    // true일 경우 디버그용 그리드 및 FPS 정보 출력

    public interface OnEmptyStackListener {
        public void onEmptyStack();
    }
    private OnEmptyStackListener emptyStackListener;
    public void setEmptyStackListener(OnEmptyStackListener emptyStackListener) {
        this.emptyStackListener = emptyStackListener;
    }
    private ArrayList<Scene> sceneStack = new ArrayList<>();
    // 여러 종류의 Scene이 있을 예정이므로 재사용 가능한 공통된 기능만 Scene에 두고 특정 확면에 대한 구체적 정보는
    // MainScene에서 구현한다. GameView는 MainScene으로서가 아니고, Scene으로 바라보아야 한다
    //
    // 게임 화면을 관리할 Scene 객체들을 스택으로 저장
    // 예를 들어 PauseScene → GameScene → TitleScene 같은 식으로 쌓을 수 있어.

    public GameView(Context context) {
        super(context);
        init();
    }

    // View의 Preview를 보려면 이 2번째 Constrouctor도 있어야 한다.
    // 이건 에디터가 AttributeSet이라는 단어를 확인하기 때문이다.
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // 실질적 생성자 역할
    // view = this로 자기 자신을 static 변수로 저장하고, 게임 루프를 시작하는 scheduleUpdate()를 호출해.
    private void init() {
        GameView.view = this;
        scheduleUpdate();
    }

    // 현재 씬을 일시정지하고 새 씬을 쌓아 올림
    public void pushScene(Scene scene) {
        int last = sceneStack.size() - 1;
        if (last >= 0) {
            sceneStack.get(last).onPause();
        }
        sceneStack.add(scene);
        scene.onEnter();
    }

    // 현재 씬을 제거하고, 바로 아래 씬을 다시 활성화
    public Scene popScene() {
        int last = sceneStack.size() - 1;
        if (last < 0) {
            notifyEmptyStack();
            return null;
        }
        Scene top = sceneStack.remove(last);
        // 리스트에서 해당 씬을 제거 -> 하지만 메모리 상 존재함

        top.onExit(); // 현재 씬 종료
        if (last >= 1) {
            sceneStack.get(last - 1).onResume(); // 아래 씬 재개
        } else {
            notifyEmptyStack(); // 스택 비었을 때 처리
        }
        return top;
    }
    // 스택이 비었을 때 호출 -> 게임 종료 처리나, 앱 종료 등에 사용 가능
    private void notifyEmptyStack() {
        if (emptyStackListener != null) {
            emptyStackListener.onEmptyStack(); // <- 콜백 호출!
            // GameView가 어떤 일이 끝났을 때 emptyStackListener에게 알려줘!
            //→ 마치 "야 나 지금 스택 비었어! 이제 뭐할까?" 라고 호출하는 느낌.
        }
    }

    // 현재 씬을 제거하지 않고 새로운 씬을 바로 위에 올림
    public void changeScene(Scene scene) {
        int last = sceneStack.size() - 1;
        if (last < 0) return;
        sceneStack.get(last).onExit(); // 현재 씬 종료
        sceneStack.add(scene); // 새 씬 추가, 시작
        scene.onEnter();
    }

    // 현재 표시 중인 씬 가져오기
    public Scene getTopScene() {
        //return sceneStack.getLast();
        // Call requires API level 35 (current min is 24): java. util. ArrayList#getLast
        int last = sceneStack.size() - 1;
        if (last < 0) return null;
        return sceneStack.get(last);
    }


    // 뷰 크기가 바뀌면 Metrics.onSize() 호출해서 화면 해상도에 맞는 스케일 계산
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Metrics.onSize(w, h);
    }

    // onDraw(Canvas canvas)는 뷰가 실제로 화면에 그려질 때 호출되는 메서드
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        Metrics.concat(canvas);

        // 반드시 성공적인 빌드가 진행된 후에 BuildConfig.java 가 생성되므로
        // 아래 코드가 문제가 되면 잠시 삭제해서 빌드만 성공시키고 다시 살려두어도 된다.
        if (drawsDebugStuffs) {
            drawDebugBackground(canvas);
        }
        Scene scene = getTopScene();
        if (scene != null) {
            scene.draw(canvas);
        }
        canvas.restore();
        if (drawsDebugStuffs) {
            drawDebugInfo(canvas);
        }
    }

    // 터치 이벤트가 발생하면 현재 씬의 onTouchEvent()로 위임
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Scene scene = getTopScene();
        if (scene != null) {
            return scene.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    // 씬에 백버튼 처리 맡김 → 처리가 안되면 씬을 pop해서 이전 화면으로 돌아감
    public void onBackPressed() {
        int last = sceneStack.size() - 1;
        if (last < 0) return; // finish activity here ?

        Scene scene = sceneStack.get(last);
        boolean handled = scene.onBackPressed();
        // 현재 스택 맨 위에 있는 Scene에게 먼저 "뒤로가기 눌렸어!"라고 알려줌.

        if (handled) return;
        // 그 Scene이 직접 처리하면 그대로 끝

        popScene();
        // Scene이 처리하지 않으면 → 현재 Scene을 스택에서 제거 (popScene())
    }

    // 🌀 프레임 루프 (핵심)
    // 안드로이드의 UI 프레임 타이밍에 맞춰 콜백 등록
    //
    // this는 FrameCallback을 구현하고 있으므로 다음 프레임이 오기 전에 doFrame()이 호출됨
    private void scheduleUpdate() {
        // Android에서 **Choreographer**를 사용해서 프레임 단위로 게임 루프를 돌리는 방법을 구현한 거야.
        // 이전 postDelayed 방식보다 훨씬 부드럽고 안정적인 프레임 동기화 방식
        //
        //Choreographer는 Android에서 UI 프레임 타이밍에 맞춰 콜백을 실행해주는 클래스야.
        //
        //postFrameCallback(this)은 다음 프레임이 그려지기 직전에 doFrame()을 호출하도록 예약하는 거
        Choreographer.getInstance().postFrameCallback(this);

        // 📌 요 방식은 postDelayed보다 실제 디스플레이 프레임과 더 정밀하게 동기화돼서
        //→ 애니메이션이나 게임 루프에 더 적합한 방식
    }


    // nanos( 나노초: 10억 분의 1초 )
    // 이건 현재 프레임 시간을 나노초 단위로 알려주는 값
    // ->  이는 매우 작은 단위로, 시간 차이를 정확하게 계산하기 위해 사용됩니다
    @Override
    public void doFrame(long nanos) {
        //Log.d(TAG, "Nanos = " + nanos + " frameTime=" + frameTime);

        // previousNanos는 이전 프레임에서의 나노초 시간을 저장하는 변수
        // nanos는 현재 프레임의 나노초 시간을 저장하는 변수

        // "previousNanos가 0일 때에는 매우 큰 frameTime이 생성되므로 0일 때에는 하면 안 된다"
        //  예를 들어, nanos가 몇 억 나노초를 기록하고 있는데 previousNanos가 0이라면,
        //  frameTime은 그 값과 같거나 그보다 큰 값이 됩니다.
        //  이런 값은 잘못된 계산이며, 게임이 이상한 속도로 동작할 수 있다.
        if (previousNanos != 0) {
            frameTime = (nanos - previousNanos) / 1_000_000_000f;
            // 1초는 1,000,000,000 나노초
            update();
            invalidate(); // 화면 다시 그려달라는 요청 (→ 결국 onDraw() 호출됨)
        }

        previousNanos = nanos;
        if (isShown()) {
            // Schedule은 View가 화면에 배치되어 있을 때만 하도록 제한하자
            // 아니면 view가 화면에 사라져도 shedule이 되는 문제가 생긴다
            scheduleUpdate();
        }
    };
    private void update() {
        // 현재 씬의 게임 로직만 업데이트
        Scene scene = getTopScene();
        if (scene != null) {
            scene.update();
        }
    }


    // Debug 용 grid 를 그리는 코드
    private Paint borderPaint, gridPaint, fpsPaint;
    private void drawDebugBackground(@NonNull Canvas canvas) {

        // borderPaint는 경계선을 그리기 위한 페인트 객체
        if (borderPaint == null) {

            borderPaint = new Paint();
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(10f);
            borderPaint.setColor(Color.RED);

            gridPaint = new Paint();
            gridPaint.setStyle(Paint.Style.STROKE);
            gridPaint.setStrokeWidth(1f);
            gridPaint.setColor(Color.GRAY);
        }

        canvas.drawRect(Metrics.borderRect, borderPaint);

        // Metrics.GRID_UNIT은 그리드 한 칸의 크기
        for (float x = Metrics.GRID_UNIT; x < Metrics.width; x += Metrics.GRID_UNIT) {
            canvas.drawLine(x, 0, x, Metrics.height, gridPaint);
        }

        for (float y = Metrics.GRID_UNIT; y < Metrics.height; y += Metrics.GRID_UNIT) {
            canvas.drawLine(0, y, Metrics.width, y, gridPaint);
        }
    }

    // 화면에 FPS (프레임 per second) 정보를 출력
    private void drawDebugInfo(Canvas canvas) {
        if (fpsPaint == null) {
            // fpsPaint는 FPS 값을 화면에 표시할 때 사용할 페인트 객체
            fpsPaint = new Paint();
            fpsPaint.setColor(Color.BLUE);
            fpsPaint.setTextSize(100f);
        }

        // frameTime은 한 프레임이 그려지는 데 걸리는 시간을 나타냅니다.
        // FPS는 1초를 프레임 시간으로 나눈 값이므로 1.0f / frameTime으로 계산
        int fps = (int) (1.0f / frameTime);
        canvas.drawText("FPS: " + fps, 100f, 200f, fpsPaint);
    }
}
