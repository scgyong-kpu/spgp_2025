package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;

public class Player extends AnimSprite {
    public Player() {
        super(R.mipmap.cookie_player_run, 8);
        setPosition(200f, 700f, 200f, 200f);
    }
}
