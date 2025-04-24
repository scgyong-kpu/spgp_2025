package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.RectUtil;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;

// 🎮 클래스 목적 요약
//조이스틱을 그리기 (draw)
//터치 이벤트 처리 (onTouch)
//**방향(angle)**과 세기(power) 계산
public class JoyStick implements IGameObject {
    private static final String TAG = JoyStick.class.getSimpleName();

    private final Bitmap bgBitmap;
    private final Bitmap thumbBitmap;


    private float x; // = 200f;
    private float y; //CENTER_Y = 1400f;
    // x, y	조이스틱 중심 위치 (게임 좌표계)

    private float bg_radius; //BG_RADIUS = 200f;
    // 배경 원의 반지름
    private float thumb_radius; //THUMB_RADIUS = 60f;
    // 조이스틱 손잡이의 반지름
    private float move_radius; //MOVE_RADIUS = BG_RADIUS - THUMB_RADIUS;
    // 손잡이가 움직일 수 있는 최대 반경 (bg_radius - thumb_radius)

    private final RectF bgRect;
    // 배경 원의 그리기 영역 (RectF)
    private final RectF thumbRect;
    // 손잡이의 그리기 영역 (RectF)

    private boolean visible;
    // 조이스틱이 보이는지 여부 (터치 중일 때만 true)
    private float startX, startY;
    public float power, angle_radian;
    // power: 손잡이를 얼마나 멀리 당겼는지 (0 ~ 1 사이 값)
    // angle_radian: 어느 방향으로 당겼는지 (라디안 각도)


    public JoyStick(int bgBmpId, int thumbBmpId, float x, float y, float bg_radius, float thumb_radius, float move_radius) {
        this.x = x; this.y = y;
        this.bg_radius = bg_radius;
        this.thumb_radius = thumb_radius;
        this.move_radius = move_radius;

        bgBitmap = BitmapPool.get(bgBmpId);
        thumbBitmap = BitmapPool.get(thumbBmpId);
        bgRect = RectUtil.newRectF(x, y, bg_radius);
        thumbRect = RectUtil.newRectF(x, y, thumb_radius);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        if (!visible) return;
        canvas.drawBitmap(bgBitmap, null, bgRect, null);
        canvas.drawBitmap(thumbBitmap, null, thumbRect, null);
    }


    // 터치 입력을 처리해서 조이스틱을 움직이고, 방향/세기 계산도 해줘.
    public boolean onTouch(MotionEvent event) {
        float[] pts;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                // ACTION_DOWN
                // 터치 시작 시: 조이스틱을 보이게 하고 시작 좌표 저장.
                // 손잡이는 원래 자리로 초기화

                visible = true;

                pts = Metrics.fromScreen(event.getX(), event.getY());

                startX = pts[0];
                startY = pts[1];
                this.x = startX;
                this.y = startY;

                RectUtil.setRect(thumbRect, x, y, thumb_radius);
                RectUtil.setRect(bgRect, x, y, bg_radius);
                power = 0;

                return true;

            case MotionEvent.ACTION_MOVE:
                // 손가락이 움직이는 동안 조이스틱의 방향, 거리 계산
                // dx, dy는 시작점 대비 현재 위치의 변화량
                // Math.atan2(dy, dx) → 방향(라디안) 계산
                // 이동 거리가 너무 멀면 move_radius만큼으로 제한

                pts = Metrics.fromScreen(event.getX(), event.getY());

                // 이 코드 핵심은 "조이스틱 손잡이의 이동 범위를 제한"하는 것
                // dx와 dy는 -bg_radius ~ +bg_radius 사이 값만 허용해!
                // dx, dy는 손잡이의 이동 거리인데,
                // 이게 조이스틱 배경 원 반지름을 넘지 않게 제한한 거야!
                //
                // 최댓값을 bg_radius로 제한하고
                //
                //최솟값을 -bg_radius로 제한하는 거야
                float dx = Math.max(-bg_radius, Math.min(pts[0] - startX, bg_radius));
                float dy = Math.max(-bg_radius, Math.min(pts[1] - startY, bg_radius));
                double radius = Math.sqrt(dx * dx + dy * dy);

                angle_radian = (float) Math.atan2(dy, dx);

                if (radius > move_radius) {
                    dx = (float) (move_radius * Math.cos(angle_radian));
                    dy = (float) (move_radius * Math.sin(angle_radian));
                    radius = move_radius;
                }

                power = (float) (radius / move_radius);
                // 0 ~ 1
                // 이동 거리 비율 = power
                //이 power 값이 결국 **"얼마나 조이스틱을 당겼는지"**를 나타내는 값이야


                float cx = x + dx, cy = y + dy;
                //Log.d(TAG, "sx="+startX+" sy="+startY+" dx="+dx + " dy=" + dy + " x=" + x + " y=" + y + " cx=" + cx + " cy=" + cy);
                Log.d(TAG, "angle=" + (int)Math.toDegrees(angle_radian) + "° power=" + String.format("%.2f", power));
                RectUtil.setRect(thumbRect, cx, cy, thumb_radius);
                break;

            case MotionEvent.ACTION_UP:
                // 손 떼면 조이스틱 사라지고 power = 0으로 초기화

                visible = false;
                power = 0;
                return true;
                // ✅ return true
            //"이 터치 이벤트는 내가 처리할 거야!"
            //→ 이 객체가 앞으로 계속해서 터치 이벤트를 받을 수 있어.
        }
        return false;
        // ❌ return false
        //"이건 내가 처리할 일이 아니야."
        //→ 이 객체는 이후의 터치 이벤트를 더 이상 못 받아.
    }
}
