package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class WaveGen implements IGameObject {
    private final MainScene scene;
    private final float interval;
    private float time;

    public WaveGen(MainScene scene, float interval) {
        this.scene = scene;
        this.interval = interval;
    }

    @Override
    public void update() {
        time += GameView.frameTime;
        if (time >= interval) {
            spawn();
            time -= interval;
        }
    }

    private void spawn() {
        scene.add(MainScene.Layer.enemy, Fly.get());
    }


    @Override
    public void draw(Canvas canvas) {
//        Fly.drawPath(canvas);
    }
}
