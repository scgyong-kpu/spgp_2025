package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Rect;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.SheetSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Fly extends SheetSprite implements IRecyclable {
    public enum Type {
        boss, red, blue, cyan, dragon,
    }
    public Fly() {
        super(R.mipmap.galaga_flies, 2.0f);
        if (rects_array == null) {
            int type_count = Type.values().length;
            //int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            rects_array = new Rect[type_count][];
            int x = 0;
            for (int i = 0; i < type_count; i++) {
                rects_array[i] = new Rect[2];
                for (int j = 0; j < 2; j++) {
                    rects_array[i][j] = new Rect(x, 0, x + h, h);
                    x += h;
                }
            }
        }
        setPosition(0, 0, 200, 200);
    }
    public static Fly get(Type type, float size) {
        return Scene.top().getRecyclable(Fly.class).init(type, size);
    }
    public Fly init(Type type, float size) {
        srcRects = rects_array[type.ordinal()];
        setPosition(0, 0, size, size);
        distance = 0;
        return this;
    }
    private static Rect[][] rects_array;
    private float distance;

    @Override
    public void update() {
        distance += 200 * GameView.frameTime;
        if (distance > Metrics.width) {
            Scene.top().remove(MainScene.Layer.enemy, this);
            return;
        }
        setPosition(distance, y);
    }

    @Override
    public void onRecycle() {}
}
