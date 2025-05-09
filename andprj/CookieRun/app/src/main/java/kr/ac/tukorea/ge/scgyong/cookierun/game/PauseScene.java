package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class PauseScene extends Scene {
    public enum Layer {
        bg, title, touch
    }
    public PauseScene() {
        initLayers(Layer.values().length);
        float w = Metrics.width, h = Metrics.height;
        add(Layer.bg, new Sprite(R.mipmap.bg_city_landscape, w/2, h/2, w, h));
        add(Layer.bg, new Sprite(R.mipmap.cookie_run_title, w/2, h/2, 3.69f, 1.36f));
        add(Layer.touch, new Button(R.mipmap.btn_resume_n, 1450f, 100f, 200f, 75f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                pop();
                return false;
            }
        }));
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }
}
