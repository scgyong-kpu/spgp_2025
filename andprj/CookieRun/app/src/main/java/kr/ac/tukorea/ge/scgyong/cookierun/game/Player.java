package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;

public class Player extends AnimSprite {
    public enum State {
        running, jump
    }
    protected State state = State.jump;
    public Player() {
        super(R.mipmap.cookie_player_jump, 8);
        setPosition(200f, 700f, 200f, 200f);
    }
    public void jump() {
        if (state == State.running) {
            setImageResourceId(R.mipmap.cookie_player_jump, 8, 2);
            setPosition(x, y, 180f, 200f);
            state = State.jump;
        } else {
            setImageResourceId(R.mipmap.cookie_player_run, 8, 4);
            setPosition(x, y, 200f, 200f);
            state = State.running;
        }
    }
    public boolean onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            jump();
        }
        return false;
    }
}
