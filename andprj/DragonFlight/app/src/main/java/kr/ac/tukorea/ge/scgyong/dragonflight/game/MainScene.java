package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.view.MotionEvent;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class MainScene extends Scene {
    private final Fighter fighter;
    public MainScene() {
        this.fighter = new Fighter();
        add(fighter);
    }

    // Overridables

    private Random random = new Random();
    private float enemyTime = 0;
    @Override
    public void update() {
        super.update();
        enemyTime -= GameView.frameTime;
        if (enemyTime < 0) {
            int level = random.nextInt(10);
            int index = random.nextInt(5);
            add(new Enemy(level, index));
            enemyTime = random.nextFloat() + 0.5f;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return fighter.onTouch(event);
    }
}
