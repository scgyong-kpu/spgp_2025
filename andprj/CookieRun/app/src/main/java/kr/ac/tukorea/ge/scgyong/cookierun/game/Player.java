package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.cookierun.R;

public class Player extends SheetSprite {
    public enum State {
        running, jump
    }
    protected State state = State.running;
    protected static Rect[][] srcRectsArray = {
            makeRects(100, 101, 102, 103), // State.running
            makeRects(7, 8),               // State.jump
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
        setPosition(200f, 700f, 200f, 200f);
        srcRects = srcRectsArray[state.ordinal()];
    }
    public void jump() {
        if (state == State.running) {
            state = State.jump;
        } else {
            state = State.running;
        }
        srcRects = srcRectsArray[state.ordinal()];
    }

    public boolean onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            jump();
        }
        return false;
    }
}
