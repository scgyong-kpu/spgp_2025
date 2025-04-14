package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    private final Fighter fighter;

    public MainScene() {
        this.fighter = new Fighter();
        add(fighter);
    }
}
