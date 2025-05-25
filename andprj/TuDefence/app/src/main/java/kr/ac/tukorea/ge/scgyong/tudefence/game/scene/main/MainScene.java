package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    enum Layer {
        bg
    }
    public MainScene() {
        initLayers(Layer.values().length);
        add(Layer.bg, new TiledBackground("map/desert.tmj", 300, 300));
    }
}
