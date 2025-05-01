package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.HorzScrollBackground;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    public enum Layer {
        bg, floor, player;
        public static final int COUNT = values().length;
    }
    private final Player player;

    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_1, 100f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_2, 200f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_3, 300f));

        player = new Player();
        add(Layer.player, player);

        add(Layer.floor, new Floor(Floor.Type.T_10x2, 0, 700));
        add(Layer.floor, new Floor(Floor.Type.T_2x2, 1000, 700));
        add(Layer.floor, new Floor(Floor.Type.T_10x2, 1200, 700));
        add(Layer.floor, new Floor(Floor.Type.T_3x1, 800, 300));
        add(Layer.floor, new Floor(Floor.Type.T_3x1, 1100, 400));
    }

    // Overridables
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return player.onTouch(event);
    }
}
