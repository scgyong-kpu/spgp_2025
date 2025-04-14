package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Fighter extends Sprite {
    private static final float RADIUS = 100f;
    public Fighter() {
        super(R.mipmap.fighter);
        setPosition(Metrics.width / 2, Metrics.height - 200, RADIUS);
    }
}
