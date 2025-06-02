package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.TiledBackground;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class ForestTiledBg extends TiledBackground {

    public static final float TILE_SIZE = 900.0f / 16.0f;
    private static final float SPEED = 20;

    public ForestTiledBg() {
        super("bg_map/earth.tmj", TILE_SIZE, TILE_SIZE);
        setWraps(true);
    }

    @Override
    public void update() {
        scrollY += -SPEED * GameView.frameTime;
    }
}
