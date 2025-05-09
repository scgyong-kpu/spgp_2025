package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.app.AlertDialog;
import android.content.DialogInterface;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class PauseScene extends Scene {
    public enum Layer {
        bg, title, touch
    }
    protected float angle = -(float)Math.PI / 2;
    public PauseScene() {
        initLayers(Layer.values().length);
        float w = Metrics.width, h = Metrics.height;
        add(Layer.bg, new Sprite(R.mipmap.trans_50b, w/2, h/2, w, h));
        add(Layer.bg, new Sprite(R.mipmap.bg_city_landscape, w/2, h/2, 1200f, 675f));
        add(Layer.title, new Sprite(R.mipmap.cookie_run_title, w/2, h/2, 369f, 136f) {
            @Override
            public void update() {
                super.update();
                angle -= (float) (GameView.frameTime * Math.PI / 4);
                float x = (float) (800f + 400f * Math.cos(angle));
                float y = (float) (450f + 200f * Math.sin(angle));
                setPosition(x, y, width, height);
            }
        });
        add(Layer.touch, new Button(R.mipmap.btn_resume_n, 1450f, 100f, 200f, 75f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                pop();
                return false;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.btn_exit_n, 800f, 550f, 267f, 100f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                new AlertDialog.Builder(GameView.view.getContext())
                        .setTitle("Confirm")
                        .setMessage("Do you really want to exit the game?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                popAll();
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        }));
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    // Overridables
    @Override
    public boolean isTransparent() {
        return true;
    }
}
