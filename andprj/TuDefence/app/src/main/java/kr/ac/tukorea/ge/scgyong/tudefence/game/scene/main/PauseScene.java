package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class PauseScene extends Scene {
    enum PauseLayer {
        bg, ui, COUNT
    }

    public PauseScene() {
        initLayers(PauseLayer.values().length);
        Sprite bg = new Sprite(R.mipmap.trans_50b);
        float w = Metrics.width, h = Metrics.height;
        bg.setPosition(w / 2, h / 2, w, h);
        add(PauseLayer.bg, bg);

        float btn_y1 = h / 2 - 120;
        float btn_y2 = h / 2 + 120;
        add(PauseLayer.ui, new Button(R.mipmap.btn_resume_n, w / 2, btn_y1, 512, 192, pressed ->
        {
            Scene.pop();
            return false;
        }));
        add(PauseLayer.ui, new Button(R.mipmap.btn_exit_n, w / 2, btn_y2, 512, 192, pressed ->
        {
            Scene.popAll();
            return true;
        }));
    }

    // Overridables
    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return PauseLayer.ui.ordinal();
    }
}
