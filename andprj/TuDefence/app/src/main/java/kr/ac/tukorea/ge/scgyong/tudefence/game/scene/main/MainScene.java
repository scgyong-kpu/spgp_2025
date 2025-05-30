package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    enum Layer {
        bg, enemy, cannon, shell, controller,
    }
    public MainScene() {
        initLayers(Layer.values().length);
        add(Layer.bg, new TiledBackground("map/desert.tmj", 100, 100));
        add(Layer.controller, new WaveGen(this, 2.0f));
        add(Layer.cannon, new Cannon(1, 400, 600));
        add(Layer.cannon, new Cannon(2, 1500, 500));
        add(Layer.cannon, new Cannon(5, 700, 1600));
        add(Layer.cannon, new Cannon(10, 2600, 800));
    }

    @Override
    public boolean onBackPressed() {
        new Scene() {
            // Overridables
            @Override
            public boolean isTransparent() {
                return true;
            }
        }.push();
        return true;
    }
}
