package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MapLoader implements IGameObject {
    private final MainScene scene;
    private final Random random = new Random();
    private float floor_x, item_x;
    public MapLoader(MainScene mainScene) {
        this.scene = mainScene;
        loadStage(GameView.view.getContext(), 1);
    }
    private void loadStage(Context context, int stage) {
        AssetManager assets = context.getAssets();
        try {
            String file = String.format("stage_%02d.tmj", stage);
            InputStream is = assets.open(file);
            InputStreamReader jsr = new InputStreamReader(is);
            JsonReader jr = new JsonReader(jsr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void update() {
        floor_x += MapObject.SPEED * GameView.frameTime;
        while (floor_x < Metrics.width) {
            Floor.Type type = random.nextBoolean() ? Floor.Type.T_10x2 : Floor.Type.T_2x2;
            Floor floor = Floor.get(type, floor_x, 700); //Metrics.height - 200);
            scene.add(floor);
            floor_x += floor.getWidth();
        }
        item_x += MapObject.SPEED * GameView.frameTime;
        while (item_x < Metrics.width) {
            int y = (random.nextInt(6) + 1) * 100;
            int count = 3;
            if (y < 500) {
                Floor floor = Floor.get(Floor.Type.T_3x1, item_x, y+100);
                scene.add(floor);
            } else {
                count = random.nextInt(5) + 1;
            }
            if (y <= 300) {
                int index = random.nextInt(ObstacleFactory.COUNT);
                Obstacle obstacle = ObstacleFactory.get(index, item_x, 600);
                scene.add(obstacle);
            }
            for (int i = 0; i < count; i++) {
                int idx = random.nextInt(JellyItem.JELLY_COUNT);
                int y2 = y - random.nextInt(3) * 100;
                JellyItem jellyItem = JellyItem.get(idx, item_x, y2);
                scene.add(jellyItem);
                item_x += jellyItem.getWidth();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
