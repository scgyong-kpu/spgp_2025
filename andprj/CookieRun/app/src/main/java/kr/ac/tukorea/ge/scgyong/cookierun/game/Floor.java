package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;

public class Floor extends Sprite {
    public Floor() {
        super(R.mipmap.cookierun_floor_480x48);
        setPosition(600f, 800f, 1000f, 200f);
    }
}
