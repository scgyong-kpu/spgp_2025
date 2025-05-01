package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;

public class Player extends AnimSprite {
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
    }
    public void jump() {
        if (state == State.running) {
            state = State.jump;
        } else {
            state = State.running;
        }
    }
    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        Rect[] rects = srcRectsArray[state.ordinal()];
        int frameIndex = Math.round(time * fps) % rects.length;
        canvas.drawBitmap(bitmap, rects[frameIndex], dstRect, null);
    }
    public boolean onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            jump();
        }
        return false;
    }
}
