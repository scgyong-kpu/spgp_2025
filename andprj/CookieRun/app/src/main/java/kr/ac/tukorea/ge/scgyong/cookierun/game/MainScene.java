package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
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
    public MainScene(int stage, int cookieId) {
        initLayers(Layer.COUNT);

        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_1, -50));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_2, -100f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_3, -150f));

        player = new Player(cookieId);
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
        add(Layer.touch, new Button(R.mipmap.btn_pause, 1500f, 100f, 100f, 100f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                new PauseScene().push();
                return false;
            }
        }));

        add(Layer.controller, new MapLoader(this, stage));
        add(Layer.controller, new CollisionChecker(this, player));
    }

    private void pauseAnimations() {
        for (IGameObject obj : objectsAt(Layer.obstacle)) {
            ((MapObject)obj).pause();
        }
    }
    private void resumeAnimations() {
        for (IGameObject obj : objectsAt(Layer.obstacle)) {
            ((MapObject)obj).resume();
        }
    }

    // Overridables


    @Override
    public boolean onBackPressed() {
        new PauseScene().push();
        return true;
    }

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
        pauseAnimations();
    }

    @Override
    public void onResume() {
        resumeAnimations();
        Sound.resumeMusic();
    }
    @Override
    public void onExit() {
        Sound.stopMusic();
    }
}
