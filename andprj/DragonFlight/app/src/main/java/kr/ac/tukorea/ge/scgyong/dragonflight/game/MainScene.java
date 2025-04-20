package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    private final Fighter fighter;
    public MainScene() {
        this.fighter = new Fighter();
        add(fighter);
        add(new EnemyGenerator());

        AnimSprite animSprite = new AnimSprite(R.mipmap.enemy_01, 10);
        animSprite.setPosition(450f, 450f, 90f);
        add(animSprite);
    }

    // Overridables

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return fighter.onTouch(event);
    }
}
