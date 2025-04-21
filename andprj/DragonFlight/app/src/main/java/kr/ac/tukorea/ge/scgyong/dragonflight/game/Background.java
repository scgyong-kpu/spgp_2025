package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Background extends Sprite {
    private final float speed;
    private final float height;
    public Background(int bitmapResId, float speed) {
        super(bitmapResId);
        this.height = bitmap.getHeight() * Metrics.width / bitmap.getWidth();
        setPosition(Metrics.width / 2, Metrics.height / 2, Metrics.width, height);
        this.speed = speed;
    }
    @Override
    public void update() {
        this.y += speed * GameView.frameTime;
        if (this.y > Metrics.height) {
            this.y = 0;
        }
        setPosition(x, y, Metrics.width, height);
    }
}
