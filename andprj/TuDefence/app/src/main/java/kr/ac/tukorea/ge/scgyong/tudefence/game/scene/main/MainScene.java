package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene {
    enum Layer {
        bg, enemy, cannon, controller,
    }
    public MainScene() {
        initLayers(Layer.values().length);
        add(Layer.bg, new TiledBackground("map/desert.tmj", 100, 100));
        add(Layer.controller, new WaveGen(this, 2.0f));
        add(Layer.cannon, new Cannon());
    }
}
