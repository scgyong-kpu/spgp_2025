package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import kr.ac.tukorea.ge.scgyong.tudefence.game.map.Converter;
import kr.ac.tukorea.ge.scgyong.tudefence.game.map.TiledMap;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class TiledBackground implements IGameObject {
    private static final String TAG = TiledBackground.class.getSimpleName();
    private final TiledMap map;

    public TiledBackground(String mapAssetFile) {
        map = loadMap(mapAssetFile);
        Log.d(TAG, "Map file " + mapAssetFile + " has " + map.getLayers().length + " layer(s) and " + map.getTilesets().length + " tileset(s).");
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
    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
