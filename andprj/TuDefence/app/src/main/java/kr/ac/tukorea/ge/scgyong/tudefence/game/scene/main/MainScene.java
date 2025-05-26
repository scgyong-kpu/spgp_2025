package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene {
    enum Layer {
        bg, enemy,
    }
    public MainScene() {
        initLayers(Layer.values().length);
        add(Layer.bg, new TiledBackground("map/desert.tmj", 100, 100));
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Fly.Type type = Fly.Type.values()[i];
            Fly fly = Fly.get(type);
            float x = random.nextFloat() * Metrics.width;
            float y = random.nextFloat() * Metrics.height;
            fly.setPosition(x, y);
            add(Layer.enemy, fly);
        }
    }
}
