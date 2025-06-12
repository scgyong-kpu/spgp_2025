package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.pause;

import android.widget.Toast;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class PauseScene extends Scene {
    enum PauseLayer {
        bg, ui, COUNT
    }

    private long createdOn;
    private final Toast toast;

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

        createdOn = System.currentTimeMillis();
        toast = Toast.makeText(GameView.view.getContext(), R.string.back_press_msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    // Overridables


    @Override
    public boolean onBackPressed() {
        long now = System.currentTimeMillis();
        if (now - createdOn < 500) {
            Scene.popAll();
            toast.cancel();
            return true;
        }
        return super.onBackPressed(); // pop this scene
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return PauseLayer.ui.ordinal();
    }
}
