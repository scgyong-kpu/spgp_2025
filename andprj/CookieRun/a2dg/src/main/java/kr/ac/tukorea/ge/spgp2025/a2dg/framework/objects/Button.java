package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ITouchable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;


// Sprite를 상속받아 이미지 기반 버튼을 만들고, 터치 이벤트를 처리할 수 있게 만든 거
public class Button extends Sprite implements ITouchable {
    public interface OnTouchListener {
        public boolean onTouch(boolean pressed);
        // pressed가 true면 눌림 상태, false면 뗀 상태
    }
    protected OnTouchListener listener;
    private static final String TAG = Button.class.getSimpleName();

    public Button(int bitmapResId, float cx, float cy, float width, float height, OnTouchListener listener) {
        super(bitmapResId, cx, cy, width, height);
        this.listener = listener;
        // 이미지 리소스 ID와 위치(cx, cy), 크기(width, height)를 받아 Sprite 초기화
        // 터치 이벤트 콜백 리스너 등록
    }
    protected boolean captures;
    // 버튼이 현재 터치를 "캡처"하고 있는지
    // (즉, 터치가 버튼 안에서 시작되어서 계속 눌려있는 상태인지) 저장

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();

        //Log.d(TAG, "onTouch:" + this + " action=" + action);
        if (action == MotionEvent.ACTION_DOWN) {
            float[] pts = Metrics.fromScreen(e.getX(), e.getY());
            float x = pts[0], y = pts[1];
            if (!dstRect.contains(x, y)) {
                return false;
            }
            captures = true;
            return listener.onTouch(true);
            // ACTION_DOWN : 화면 터치가 시작될 때, 터치 위치가 버튼 범위 내인지 검사 후
            // 범위 안이면 captures를 true로 설정하고 눌림 콜백 호출
            // (버튼이 눌렸다고 알림)

        } else if (action == MotionEvent.ACTION_UP) {
            captures = false;
            return listener.onTouch(false);
            // ACTION_UP : 터치가 끝났을 때, captures를 false로 설정하고 뗌 콜백 호출
        }

        // 그 외 이벤트 (예: ACTION_MOVE)는 버튼이
        // 터치를 잡고 있는지(captures) 여부를 반환
        return captures;
    }
}
