package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class WaveGen implements IGameObject {
    private final MainScene scene;
    private final float interval;
    private static final Random rand = new Random();
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
        float x = rand.nextFloat() * Metrics.width;
        float y = rand.nextFloat() * Metrics.height;
        //float size = rand.nextFloat() * 100 + 200;
        //float speed = rand.nextFloat() * 50 + 100;
        Fly.Type[] types = Fly.Type.values();
        Fly.Type type = types[rand.nextInt(types.length)];
        Fly fly = Fly.get(type);
        fly.setPosition(x, y);
        scene.add(MainScene.Layer.enemy, fly);
    }


    @Override
    public void draw(Canvas canvas) {}
}
