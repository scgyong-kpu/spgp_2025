package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class WaveGen implements IGameObject {
    private static final float INTERVAL_INIT = 2.0f;
    private static final float INTERVAL_MIN = 0.1f;
    private static final float INTERVAL_WAVE = 30.0f;
    private final MainScene scene;
    private float time, interval, flySpeedRatio;
    private float waveTime, waveInterval;
    private int wave;
    private boolean bossPhase;

    public WaveGen(MainScene scene) {
        this.scene = scene;
        this.interval = INTERVAL_INIT;
        this.waveInterval = INTERVAL_WAVE;
        this.time = this.waveTime = 0;
        this.flySpeedRatio = 1.0f;
        this.wave = 0;
        this.bossPhase = false;
    }

    @Override
    public void update() {
        waveTime += GameView.frameTime;
        if (bossPhase) {
            if (waveTime > waveInterval || scene.objectsAt(MainScene.Layer.enemy).isEmpty()) {
                waveTime = 0;
                bossPhase = false;
                wave++;
                // 다음 wave 에서 waveInterval 이나 flySpeedRatio 등을 조정해도 좋을듯.
            }
        } else {
            time += GameView.frameTime;
            if (time > interval) {
                spawn();
                time -= interval;
                interval *= 0.995f; // 0.5% 씩 간격을 줄인다
                if (interval < INTERVAL_MIN) {
                    interval = INTERVAL_MIN;
                    // 하지만 이 이상 빨라지진 않게 한다
                }
            }
            if (waveTime > waveInterval) {
                bossPhase = true;
                spawn();
                waveTime = 0;
            }
        }
    }

    private void spawn() {
        Fly fly = Fly.get(bossPhase, flySpeedRatio);
        scene.add(MainScene.Layer.enemy, fly);
    }
    @Override
    public void draw(Canvas canvas) {
//        Fly.drawPath(canvas);
    }
}
