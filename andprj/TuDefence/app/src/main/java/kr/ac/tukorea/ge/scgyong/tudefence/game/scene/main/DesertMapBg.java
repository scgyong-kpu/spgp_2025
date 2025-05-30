package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.util.Log;

public class DesertMapBg extends TiledBackground {
    public static final int TILE_INDEX_BRICK = 10;
    private static final String TAG = DesertMapBg.class.getSimpleName();

    public DesertMapBg() {
        super("map/desert.tmj", 100, 100);
    }
    public boolean canInstallAt(int x, int y) {
        int tile = layer.tileAt(x, y); // layer = Current Active Layer
        Log.d(TAG, "Tile @(" + x + "," + y + ") = " + tile);
        return tile == TILE_INDEX_BRICK;
    }
}
