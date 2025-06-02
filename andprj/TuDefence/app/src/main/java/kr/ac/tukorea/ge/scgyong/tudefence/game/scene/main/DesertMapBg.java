package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.TiledBackground;

public class DesertMapBg extends TiledBackground {
    public static final int TILE_INDEX_BRICK = 10;
    private static final String TAG = DesertMapBg.class.getSimpleName();

    public DesertMapBg() {
        super("map/desert.tmj", 100, 100);
    }
    public boolean canInstallAt(int x, int y) {
        int tile = layer.tileAt(x, y); // layer = Current Active Layer
        if (tile != TILE_INDEX_BRICK) return false;
        tile = layer.tileAt(x + 1, y);
        if (tile != TILE_INDEX_BRICK) return false;
        tile = layer.tileAt(x, y + 1);
        if (tile != TILE_INDEX_BRICK) return false;
        tile = layer.tileAt(x + 1, y + 1);
        if (tile != TILE_INDEX_BRICK) return false;
        return true;
    }
}
