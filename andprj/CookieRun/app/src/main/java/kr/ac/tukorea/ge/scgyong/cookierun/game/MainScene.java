package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.HorzScrollBackground;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.Sound;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    public enum Layer {
        bg, floor, item, obstacle, player, ui, touch, controller;
        public static final int COUNT = values().length;
    }
    private final Player player;
    private static final String TAG = MainScene.class.getSimpleName();
    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_1, 100f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_2, 200f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_3, 300f));

        player = new Player();
        add(Layer.player, player);

        add(Layer.touch, new Button(R.mipmap.btn_slide_n, 150f, 800f, 200f, 75f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                //Log.d(TAG, "Button: Slide - pressed:" + pressed);
                player.slide(pressed);
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.btn_jump_n, 1450f, 770f, 200f, 75f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                //Log.d(TAG, "Button: Jump");
                player.jump();
                return false;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.btn_fall_n, 1450f, 850f, 200f, 75f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                //Log.d(TAG, "Button: Fall");
                player.fall();
                return false;
            }
        }));

        add(Layer.controller, new MapLoader(this));
        add(Layer.controller, new CollisionChecker(this, player));
    }

    // Overridables
    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    @Override
    public void onEnter() {
        Sound.playMusic(R.raw.main);
    }
    @Override
    public void onPause() {
        Sound.pauseMusic();
    }

    @Override
    public void onResume() {
        Sound.resumeMusic();
    }
    @Override
    public void onExit() {
        Sound.stopMusic();
    }
}
