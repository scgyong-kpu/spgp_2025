package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.HorzScrollBackground;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    public enum Layer {
        bg, player;
        public static final int COUNT = values().length;
    }

    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_1, 100f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_2, 200f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_3, 300f));

        add(Layer.player, new Player());
    }
}
