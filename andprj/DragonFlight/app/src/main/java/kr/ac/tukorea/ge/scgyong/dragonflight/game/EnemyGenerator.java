package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class EnemyGenerator implements IGameObject {
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    private final Random random = new Random();
    public static final float GEN_INTERVAL = 5.0f;
    private final MainScene scene;
    private float enemyTime = 0;
    private int wave;

    public EnemyGenerator(MainScene mainScene) {
        this.scene = mainScene;
    }

    @Override
    public void update() {
        enemyTime -= GameView.frameTime;
        if (enemyTime < 0) {
            generate();
            enemyTime = GEN_INTERVAL;
        }
    }

    private void generate() {
        wave++;

        //StringBuilder enemies = new StringBuilder(); // for debug

        for (int i = 0; i < 5; i++) {
            int level = (wave + 8) / 10 - random.nextInt(3);
            if (level < 0) level = 0;
            if (level > Enemy.MAX_LEVEL) level = Enemy.MAX_LEVEL;
            scene.add(Enemy.get(level, i));
            //enemies.append(level); // for debug
        }
        //Log.v(TAG, "Generating: wave " + wave + " : " + enemies.toString());
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
