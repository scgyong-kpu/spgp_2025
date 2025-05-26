package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
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
        path = new Path();
        path.moveTo(-120f, 1828f);
        path.cubicTo(288f, 1788f, 644f, 1724f, 808f, 1388f);
        path.cubicTo(972f, 1052f, 88f, 1292f, 256f, 988f);
        path.cubicTo(424f, 684f, 1064f, 68f, 1268f, 264f);
        path.cubicTo(1472f, 460f, 1200f, 1664f, 1712f, 1476f);
        path.cubicTo(2224f, 1288f, 1952f, 536f, 2208f, 356f);
        path.cubicTo(2464f, 176f, 2824f, 132f, 3040f, 388f);
        path.cubicTo(3256f, 644f, 2952f, 1592f, 3272f, 1932f);


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
