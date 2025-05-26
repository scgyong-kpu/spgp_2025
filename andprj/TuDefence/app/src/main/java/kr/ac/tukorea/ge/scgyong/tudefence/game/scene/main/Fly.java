package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;

import androidx.core.graphics.PathParser;

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
    public static Fly get(Type type, float size, float speed) {
        return Scene.top().getRecyclable(Fly.class).init(type, size, speed);
    }
    public Fly init(Type type, float size, float speed) {
        srcRects = rects_array[type.ordinal()];
        setPosition(0, 0, size, size);
        distance = 0;
        this.speed = speed;
        update();
        return this;
    }

    private static final PathMeasure pm;
    private static final float pathLength;
    private static final Path path;
    private static final Paint paint;
    static {
        path = PathParser.createPathFromPathData(
            "M -120,1828\n" +
            "C 288,1788 644,1724 808,1388\n" +
            "C 972,1052 88,1292 256,988\n" +
            "C 424,684 1064,68 1268,264\n" +
            "C 1472,460 1200,1664 1712,1476\n" +
            "C 2224,1288 1952,536 2208,356\n" +
            "C 2464,176 2824,132 3040,388\n" +
            "C 3256,644 2952,1592 3272,1932"
        );

        pm = new PathMeasure(path, false);
        pathLength = pm.getLength();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);
        paint.setColor(Color.MAGENTA);
    }

    public static void drawPath(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    private static Rect[][] rects_array;
    private float distance, speed;
    private final float[] pos = new float[2];
    private final float[] tan = new float[2];

    @Override
    public void update() {
        distance += speed * GameView.frameTime;
        if (distance > pathLength) {
            Scene.top().remove(MainScene.Layer.enemy, this);
            return;
        }
        pm.getPosTan(distance, pos, tan);
        setPosition(pos[0], pos[1]);
    }

    @Override
    public void onRecycle() {}
}
