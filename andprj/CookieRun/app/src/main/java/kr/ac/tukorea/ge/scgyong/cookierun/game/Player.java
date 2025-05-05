package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class Player extends SheetSprite {
    public enum State {
        running, jump, doubleJump, falling
    }
    protected State state = State.running;
    private final float ground;
    private float jumpSpeed;
    private static final float JUMP_POWER = 900f;
    private static final float GRAVITY = 1700f;
    protected static Rect[][] srcRectsArray = {
            makeRects(100, 101, 102, 103), // State.running
            makeRects(7, 8),               // State.jump
            makeRects(1, 2, 3, 4),         // State.doubleJump
            makeRects(0),                  // State.falling
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
        setPosition(200f, 510f, 386, 386f);
        setState(State.running);
        ground = y;
    }

    @Override
    public void update() {
        if (state == State.jump || state == State.doubleJump) {
            float dy = jumpSpeed * GameView.frameTime;
            jumpSpeed += GRAVITY * GameView.frameTime;
            if (y + dy >= ground) {
                dy = ground - y;
                setState(State.running);
            }
            y += dy;
            setPosition(x, y, width, height);
        }
    }

    private void setState(State state) {
        this.state = state;
        srcRects = srcRectsArray[state.ordinal()];
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

    public boolean onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            jump();
        }
        return false;
    }
}
