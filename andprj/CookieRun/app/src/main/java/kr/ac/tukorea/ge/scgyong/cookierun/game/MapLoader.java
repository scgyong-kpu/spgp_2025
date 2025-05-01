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
            Floor.Type type = random.nextBoolean() ? Floor.Type.T_10x2 : Floor.Type.T_2x2;
            Floor floor = Floor.get(type, floor_x, 700); //Metrics.height - 200);
            scene.add(floor);
            floor_x += floor.getWidth();
        }
        item_x += -200.0f * GameView.frameTime;
        while (item_x < Metrics.width) {
            int y = (random.nextInt(6) + 1) * 100;
            int count = 3;
            if (y < 500) {
                Floor floor = Floor.get(Floor.Type.T_3x1, item_x, y+100);
                scene.add(floor);
            } else {
                count = random.nextInt(5) + 1;
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
