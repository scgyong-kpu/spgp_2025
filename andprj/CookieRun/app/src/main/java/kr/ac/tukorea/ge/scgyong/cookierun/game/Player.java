package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;

public class Player extends Sprite {
    public Player() {
        super(R.mipmap.cookie_player);
        setPosition(200f, 700f, 200f, 200f);
    }
}
