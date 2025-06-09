package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class Explosion extends AnimSprite implements IRecyclable {
    protected float time;
    public Explosion() {
        super(R.mipmap.explosion, 20);
    }
    public static Explosion get(float x, float y, float radius) {
        return Scene.top().getRecyclable(Explosion.class).init(x, y, radius);
    }

    private Explosion init(float x, float y, float radius) {
        setPosition(x, y, radius);
        time = 0;
        return this;
    }

    @Override
    public void update() {
        time += GameView.frameTime;
        if (time >= 1.0) {
            Scene.top().remove(MainScene.Layer.explosion, this);
            return;
        }
        int frameIndex = (int)(time * fps) % frameCount;
        srcRect.set(frameIndex * frameWidth, 0, (frameIndex + 1) * frameWidth, frameHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        // java 에서는 super.super.draw(canvas) 와 같은 호출은 허락하지 않는다.
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    @Override
    public void onRecycle() {

    }
}
