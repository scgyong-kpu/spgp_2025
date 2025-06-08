package kr.ac.tukorea.ge.scgyong.taptu.game;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    public enum Layer {
        bg,
    }
    public MainScene() {
        initLayers(Layer.values().length);
    }
}
