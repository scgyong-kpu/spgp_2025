package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

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

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.map.Converter;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.map.MapLayer;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.map.TiledMap;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.map.Tileset;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class TiledBackground implements IGameObject {
    private static final String TAG = TiledBackground.class.getSimpleName();
    protected final TiledMap map;
    protected final String assetPath;
    protected Tileset tileset;
    protected MapLayer layer;
    protected Bitmap bitmap;
    protected float tileWidth;
    protected float tileHeight;

    protected final Rect srcRect = new Rect();
    protected final RectF dstRect = new RectF();
    protected float scrollX, scrollY;

    public void scrollTo(float x, float y) {
        scrollX = x;
        scrollY = y;
    }
    public boolean doesWrap() {
        return wraps;
    }

    public void setWraps(boolean wraps) {
        this.wraps = wraps;
    }

    protected boolean wraps;

    public TiledBackground(String mapAssetFile, float tileWidth, float tileHeight) {
        map = loadMap(mapAssetFile);
        assetPath = getDirectory(mapAssetFile);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        Log.d(TAG, "Map file " + mapAssetFile + " has " + map.getLayers().length + " layer(s) and " + map.getTilesets().length + " tileset(s).");
        setActiveTileset(0);
        setActiveLayer(0);

        //setWraps(true);
    }

    protected TiledMap loadMap(String fileName) {
        try {
            String json = loadAssetAsString(fileName);
            return Converter.fromJsonString(json);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    protected String loadAssetAsString(String fileName) throws IOException {
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
    protected Bitmap loadBitmapAsset(String fileName) {
        Context context = GameView.view.getContext();
        AssetManager assets = context.getAssets();
        try {
            InputStream inputStream = assets.open(fileName);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected static String getDirectory(String assetFilename) {
        int slash = assetFilename.lastIndexOf('/');
        if (slash < 0) {
            return "./";
        }
        return assetFilename.substring(0, slash + 1);
    }

    public void setActiveTileset(int index) {
        tileset = map.getTilesets()[index];
        String file = assetPath + tileset.getImage();
        bitmap = loadBitmapAsset(file);
    }


    public void setActiveLayer(int index) {
        layer = map.getLayers()[index];
    }

    public void setTileSize(float width, float height) {
        this.tileWidth = width;
        this.tileHeight = height;
    }
    public Tileset getActiveTileset() {
        return tileset;
    }
    public MapLayer getActiveLayer() {
        return layer;
    }

    public float getFullWidth() {
        return map.getWidth() * tileWidth;
    }
    public float getFullHeight() {
        return map.getHeight() * tileHeight;
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        float scroll_x = scrollX, scroll_y = scrollY;
        if (wraps) {
            float fullWidth = map.getWidth() * tileWidth;
            scroll_x %= fullWidth;
            if (scroll_x < 0) scroll_x += fullWidth;

            float fullHeight = map.getHeight() * tileHeight;
            scroll_y %= fullHeight;
            if (scroll_y < 0) scroll_y += fullHeight;
        }
        int layer_width = (int) layer.getWidth();
        int layer_height = (int) layer.getHeight();
        float start_dx = -(scroll_x % tileWidth);
        int start_sx = (int) (scroll_x / tileWidth);
        float start_dy = -(scroll_y % tileHeight);
        int sy = (int) (scroll_y / tileHeight);
        float dy = start_dy;
        while (dy < Metrics.height) {
            int sx = start_sx;
            float dx = start_dx;
            for (; dx < Metrics.width; dx += tileWidth, sx = (sx+1) % layer_width) {
                int tileNo = layer.tileAt(sx, sy);
                //Log.v(TAG, "sx=" + sx + " sy=" + sy + " tile=" + tileNo);
                if (tileNo < 0) {
                    continue;
                }
                tileset.getRect(srcRect, tileNo);
                dstRect.set(dx, dy, dx + tileWidth, dy + tileHeight);
                canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            }
            //Log.d(TAG, "--- sy = " + sy + " ---");
            sy = (sy + 1) % layer_height;
            dy += tileHeight;
        }
        //Log.i(TAG, "------------------------------");
    }
}
