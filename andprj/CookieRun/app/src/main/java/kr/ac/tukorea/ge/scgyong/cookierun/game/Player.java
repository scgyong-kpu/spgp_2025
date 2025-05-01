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
            new Rect[] {
                    new Rect(72 + 0 * 272, 404, 72+140 + 0 * 272, 404+140),
                    new Rect(72 + 1 * 272, 404, 72+140 + 1 * 272, 404+140),
                    new Rect(72 + 2 * 272, 404, 72+140 + 2 * 272, 404+140),
                    new Rect(72 + 3 * 272, 404, 72+140 + 3 * 272, 404+140)
            },
            new Rect[] {
                    new Rect(72 + 7 * 272, 132, 72+140 + 7 * 272, 132+140),
                    new Rect(72 + 8 * 272, 132, 72+140 + 8 * 272, 132+140),
            },
    };
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
