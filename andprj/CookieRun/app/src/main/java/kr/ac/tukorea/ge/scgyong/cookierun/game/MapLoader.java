package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.Canvas;

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
    }

    @Override
    public void update() {
        floor_x += -200.0f * GameView.frameTime;
        while (floor_x < Metrics.width) {
            int idx = random.nextInt(Floor.Type.COUNT);
            Floor.Type type = Floor.Type.values()[idx];
            Floor floor = Floor.get(type, floor_x, 700); //Metrics.height - 200);
            scene.add(floor);
            floor_x += type.width();
        }
        item_x += -200.0f * GameView.frameTime;
        while (item_x < Metrics.width) {
            int idx = random.nextInt(JellyItem.JELLY_COUNT);
            int y = random.nextInt(7) * 100;
            JellyItem jellyItem = JellyItem.get(idx, item_x, y);
            scene.add(jellyItem);
            item_x += 100;
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
