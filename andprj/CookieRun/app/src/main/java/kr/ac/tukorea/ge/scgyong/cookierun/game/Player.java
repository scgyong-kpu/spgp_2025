package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Player extends SheetSprite implements IBoxCollidable {
    public enum State {
        running, jump, doubleJump, falling
    }
    protected State state = State.running;
    private float jumpSpeed;
    private final RectF collisionRect = new RectF();
    private static final float JUMP_POWER = 900f;
    private static final float GRAVITY = 1700f;
    protected static Rect[][] srcRectsArray = {
            makeRects(100, 101, 102, 103), // State.running
            makeRects(7, 8),               // State.jump
            makeRects(1, 2, 3, 4),         // State.doubleJump
            makeRects(0),                  // State.falling
    };
    protected static float[][] edgeInsetRatios = {
            { 0.3f, 0.5f, 0.3f, 0.0f }, // State.running
            { 0.3f, 0.6f, 0.3f, 0.0f }, // State.jump
            { 0.3f, 0.6f, 0.3f, 0.0f }, // State.doubleJump
            { 0.3f, 0.5f, 0.3f, 0.0f }, // State.falling
    };
    protected static Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = 2 + (idx % 100) * 272;
            int t = 2 + (idx / 100) * 272;
            rects[i] = new Rect(l, t, l + 270, t + 270);
        }
        return rects;
    }
    public Player() {
        super(R.mipmap.cookie_player_sheet, 8);
        setPosition(200f, 200f, 386, 386f);
        setState(State.running);
    }

    @Override
    public void update() {
        switch (state) {
        case jump:
        case doubleJump:
        case falling:
            float dy = jumpSpeed * GameView.frameTime;
            jumpSpeed += GRAVITY * GameView.frameTime;
            if (jumpSpeed >= 0) { // 낙하하고 있다면 발밑에 땅이 있는지 확인한다
                float foot = collisionRect.bottom;
                float floor = findNearestFloorTop(foot);
                if (foot + dy >= floor) {
                    dy = floor - foot;
                    setState(State.running);
                }
            }
            y += dy;
            setPosition(x, y, width, height);
            updateCollisionRect();
            break;
        case running:
            float foot = collisionRect.bottom;
            float floor = findNearestFloorTop(foot);
            if (foot < floor) {
                // 달리는 중에 발밑 floor 좌표가 발보다 아래에 있다면 떨어지자
                setState(State.falling);
                jumpSpeed = 0; // 자유낙하이므로 속도가 0 부터 시작한다.
            }
            break;
        }
    }
    private float findNearestFloorTop(float foot) {
        // 플레이어 발의 y 좌표에서 아래쪽으로 가장 가까운 floor 의 좌표를 찾는다.
        Floor platform = findNearestFloor(foot);
        if (platform == null) return Metrics.height;
        return platform.getCollisionRect().top;
    }
    private Floor findNearestFloor(float foot) {
        // 플레이어 발의 y 좌표에서 아래쪽으로 가장 가까운 floor 를 찾는다.
        Floor nearest = null;
        MainScene scene = (MainScene) Scene.top();
        if (scene == null) return null;
        ArrayList<IGameObject> floors = scene.objectsAt(MainScene.Layer.floor);
        float top = Metrics.height; // 못 찾으면 디폴트 값은 화면 아래이다.
        for (IGameObject obj: floors) {
            Floor floor = (Floor) obj;
            RectF rect = floor.getCollisionRect();
            if (rect.left > x || x > rect.right) {
                // floor 의 좌우 좌표 범위가 player 의 x 좌표를 포함하지 않으면 대상에서 제외한다.
                continue;
            }
            //Log.d(TAG, "foot:" + foot + " floor: " + rect);
            if (rect.top < foot) {
                // 발보다 위에 있는 floor 는 대상에서 제외한다
                continue;
            }
            if (top > rect.top) {
                // 더 가까운 것을 찾았다.
                top = rect.top;
                nearest = floor;
            }
            //Log.d(TAG, "top=" + top + " gotcha:" + floor);
        }
        return nearest;
    }
    private void updateCollisionRect() {
        float[] insets = edgeInsetRatios[state.ordinal()];
        collisionRect.set(
                dstRect.left + width * insets[0],
                dstRect.top + height * insets[1],
                dstRect.right - width * insets[2],
                dstRect.bottom - height * insets[3]);
    }

    private void setState(State state) {
        this.state = state;
        srcRects = srcRectsArray[state.ordinal()];
        updateCollisionRect();
    }

    public void jump() {
        if (state == State.running) {
            jumpSpeed = -JUMP_POWER;
            setState(State.jump);
        } else if (state == State.jump) {
            jumpSpeed = -JUMP_POWER;
            //jumpSpeed -= JUMP_POWER;
            setState(State.doubleJump);
        }
    }
    public void fall() {
        if (state != State.running) return;
        float foot = collisionRect.bottom;
        Floor floor = findNearestFloor(foot);
        if (floor == null) return;
        if (!floor.canPass()) return;
        y += 0.1f; // 아래로 아주 약간 내려준다.
        dstRect.offset(0, 0.1f); // y 좌표와 dstRect 를 함께 내려준다.
        setState(State.falling); // collisinRect 는 이곳에서 update 되므로 추가작업하지 않아도 된다.
        jumpSpeed = 0;
    }
    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    public boolean onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            jump();
        }
        return false;
    }
}
