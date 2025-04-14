package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;

public class Enemy extends Sprite {
    private static final float SPEED = 300f;

    public Enemy(int level, int index) {
        super(R.mipmap.f_01_01);
        setPosition(90 * (2 * index + 1), 300f, 160f, 160f);
        dy = SPEED;
    }
}
