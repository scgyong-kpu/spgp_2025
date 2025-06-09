package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;

import androidx.core.graphics.PathParser;

import java.util.Random;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.SheetSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.Gauge;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class Fly extends SheetSprite implements IRecyclable {
    private static final String TAG = Fly.class.getSimpleName();
    public enum Type {
        boss, red, blue, cyan, dragon;
        float getMaxHealth() {
            return HEALTHS[ordinal()];
        }
        static final float[] HEALTHS = { 150, 50, 30, 20, 10 };
        static final int[] POSSIBILITIES = { 0, 10, 20, 30, 40 };
        static int POSSIBILITY_SUM;
        static {
            POSSIBILITY_SUM = 0;
            for (int p : POSSIBILITIES) {
                POSSIBILITY_SUM += p;
            }
        }
        static Type random() {
            int value = rand.nextInt(Type.POSSIBILITY_SUM);
            int rv = value;
            for (int i = 0; i < Type.POSSIBILITIES.length; i++) {
                value -= Type.POSSIBILITIES[i];
                if (value < 0) {
                    Type type = Type.values()[i];
                    // Log.d(TAG, "RandomValue=" + rv + " type=" + type + " i=" + i);
                    return type;
                }
            }
            return dragon;
        }
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
    public static Fly get(boolean boss, float speedRatio) {
        Fly.Type type = boss ? Type.boss : Fly.Type.random();
        float size = rand.nextFloat() * 100 + 150;
        if (boss) {
            size *= 1.5f;
        }
        float speed = speedRatio * (rand.nextFloat() * 50 + 75);
        return Scene.top().getRecyclable(Fly.class).init(type, size, speed);
    }
    public Fly init(Type type, float size, float speed) {
        srcRects = rects_array[type.ordinal()];
        setPosition(0, 0, size, size);
        distance = 0;
        dx = dy = 0;
        this.speed = speed;
        life = maxLife = displayLife = type.getMaxHealth() * (0.9f + rand.nextFloat() * 0.2f);
        update();
        return this;
    }

    private static final Random rand = new Random();
    private static final PathMeasure pm;
    private static final float pathLength;
    private static final Path path;
//    private static final Paint paint;
    static {
        path = PathParser.createPathFromPathData(
            "M -128,1817.6\n" +
            "C 198.4,1785.6 384,1558.4 384,1280\n" +
            "C 384,1001.6 86.4,956.8 89.6,656\n" +
            "C 92.8,355.2 332.8,54.4 640,51.2\n" +
            "C 947.2,48 1177.6,339.2 1184,649.6\n" +
            "C 1190.4,960 988.8,924.8 992,1251.2\n" +
            "C 995.2,1577.6 1440,1692.8 1609.6,1692.8\n" +
            "C 1779.2,1692.8 2217.6,1516.8 2220.8,1264\n" +
            "C 2224,1011.2 1993.6,940.8 1993.6,662.4\n" +
            "C 1993.6,384 2236.8,83.2 2576,86.4\n" +
            "C 2915.2,89.6 3120,419.2 3110.4,675.2\n" +
            "C 3100.8,931.2 2816,1078.4 2819.2,1340.8\n" +
            "C 2822.4,1603.2 3155.2,1782.4 3360,1766.4\n"
        );

        pm = new PathMeasure(path, false);
        pathLength = pm.getLength();

//        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(10f);
//        paint.setColor(Color.MAGENTA);
    }

//    public static void drawPath(Canvas canvas) {
//        canvas.drawPath(path, paint);
//    }

    private static Rect[][] rects_array;
    private float distance, speed, angle;
    private float life, maxLife, displayLife;
    private static Gauge gauge;

    public boolean decreaseLife(float power) {
        life -= power;
        return life <= 0;
    }
    public int score() {
        return Math.round(maxLife / 10) * 10;
    }
    private float dx, dy;
    private final float[] pos = new float[2];
    private final float[] tan = new float[2];

    @Override
    public void update() {
        if (life != displayLife) {
            float step = maxLife / 50;
            float diff = life - displayLife;
            if (diff < -step) {
                displayLife -= step;
            } else if (diff > step) {
                displayLife += step;
            } else {
                displayLife = life;
            }
        }
        distance += speed * GameView.frameTime; // * 5; 파리만 빠르게 움직이게 하고 싶다면
        if (distance > pathLength) {
            Scene.top().remove(MainScene.Layer.enemy, this);
            return;
        }
        float maxDiff = width / 5;
        dx += (2 * maxDiff * rand.nextFloat() - maxDiff) * GameView.frameTime;
        if (dx < -maxDiff) dx = -maxDiff;
        else if (dx > maxDiff) dx = maxDiff;
        dy += (2 * maxDiff * rand.nextFloat() - maxDiff) * GameView.frameTime;
        if (dy < -maxDiff) dy = -maxDiff;
        else if (dy > maxDiff) dy = maxDiff;

        pm.getPosTan(distance, pos, tan);
        setPosition(pos[0] + dx, pos[1] + dy);
        angle = (float) Math.toDegrees(Math.atan2(tan[1], tan[0]));
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, x, y);
        super.draw(canvas);
        canvas.restore();
        float barSize = width * 2 / 3;
        if (gauge == null) {
            gauge = new Gauge(0.2f, R.color.fly_health_fg, R.color.fly_health_bg);
        }
        gauge.draw(canvas, x - barSize / 2, y + barSize / 2, barSize, displayLife / maxLife);
    }
    @Override
    public void onRecycle() {}
}
