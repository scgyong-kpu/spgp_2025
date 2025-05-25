package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractList;

import kr.ac.tukorea.ge.scgyong.tudefence.game.map.Converter;
import kr.ac.tukorea.ge.scgyong.tudefence.game.map.Layer;
import kr.ac.tukorea.ge.scgyong.tudefence.game.map.TiledMap;
import kr.ac.tukorea.ge.scgyong.tudefence.game.map.Tileset;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class TiledBackground implements IGameObject {
    private static final String TAG = TiledBackground.class.getSimpleName();
    private final TiledMap map;
    private final String assetPath;
    private Tileset tileset;
    private Layer layer;
    private Bitmap bitmap;
    private final float tileWidth, tileHeight;

    private final Rect srcRect = new Rect();
    private final RectF dstRect = new RectF();

    public TiledBackground(String mapAssetFile, float tileWidth, float tileHeight) {
        map = loadMap(mapAssetFile);
        assetPath = getDirectory(mapAssetFile);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        Log.d(TAG, "Map file " + mapAssetFile + " has " + map.getLayers().length + " layer(s) and " + map.getTilesets().length + " tileset(s).");
        setActiveTileset(0);
        setActiveLayer(0);
    }

    private TiledMap loadMap(String fileName) {
        try {
            String json = loadAssetAsString(fileName);
            return Converter.fromJsonString(json);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String loadAssetAsString(String fileName) throws IOException {
        Context context = GameView.view.getContext();
        AssetManager assets = context.getAssets();
        InputStream inputStream = assets.open(fileName);
        InputStreamReader in = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(in);
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        reader.close();
        inputStream.close();
        return builder.toString();
    }
    private Bitmap loadBitmapAsset(String fileName) {
        Context context = GameView.view.getContext();
        AssetManager assets = context.getAssets();
        try {
            InputStream inputStream = assets.open(fileName);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String getDirectory(String assetFilename) {
        int slash = assetFilename.lastIndexOf('/');
        if (slash < 0) {
            return "./";
        }
        return assetFilename.substring(0, slash + 1);
    }

    private void setActiveTileset(int index) {
        tileset = map.getTilesets()[index];
        String file = assetPath + tileset.getImage();
        bitmap = loadBitmapAsset(file);
    }


    private void setActiveLayer(int index) {
        layer = map.getLayers()[index];
    }
    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {

            int sx = 0;
            float dx = 0;
            while (dx < Metrics.width) {
                int tileNo = layer.tileAt(sx, 0);
                tileset.getRect(srcRect, tileNo);
                dstRect.set(dx, 0, dx + tileWidth, tileHeight);
                canvas.drawBitmap(bitmap, srcRect, dstRect, null);

                dx += tileWidth;
                sx += 1;
            }
    }
}
