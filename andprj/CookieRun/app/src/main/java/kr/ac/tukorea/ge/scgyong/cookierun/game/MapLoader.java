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
        if (random.nextInt(100) == 0) { // 1% 확률
            Floor floor = Floor.get(Floor.Type.T_2x2, 1600, 700); //Metrics.height - 200);
            scene.add(floor);
        }

        if (random.nextInt(100) == 0) {
            int idx = random.nextInt(JellyItem.JELLY_COUNT);
            int y = random.nextInt(7) * 100;
            JellyItem jellyItem = JellyItem.get(idx, 1600, y);
            scene.add(jellyItem);
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
