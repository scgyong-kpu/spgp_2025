package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Fighter extends Sprite {
    private static final float RADIUS = 100f;
    private static final float SPEED = 300f;
    private final JoyStick joyStick;

    public Fighter(JoyStick joyStick) {
        super(R.mipmap.fighter);
        setPosition(Metrics.width / 2, Metrics.height - 200, RADIUS);
        this.joyStick = joyStick;
    }

    @Override
    public void update() {
        int way = 0;
        if (joyStick.power > 0) {
            way = 1 - 2 * (int)Math.round(joyStick.angle_radian / Math.PI);
        }
        dx = SPEED * way;
        super.update();
    }
}
