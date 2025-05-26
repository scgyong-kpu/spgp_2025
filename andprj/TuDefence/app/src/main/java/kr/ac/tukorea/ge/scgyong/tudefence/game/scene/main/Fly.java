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
        this.speed = 0;
        update();
        this.speed = speed;
        return this;
    }

    private static final PathMeasure pm;
    private static final float pathLength;
    private static final Path path;
    private static final Paint paint;
    static {
        path = new Path();
        path.moveTo(0, 1800);
        path.lineTo(500, 1300);
        path.lineTo(100, 500);
        path.lineTo(700, 0);
        path.lineTo(1300, 500);
        path.lineTo(900, 1300);
        path.lineTo(1600, 1800);
        path.lineTo(2300, 1300);
        path.lineTo(1900, 500);
        path.lineTo(2500, 0);
        path.lineTo(3100, 500);
        path.lineTo(2700, 1300);
        path.lineTo(3200, 1800);

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
