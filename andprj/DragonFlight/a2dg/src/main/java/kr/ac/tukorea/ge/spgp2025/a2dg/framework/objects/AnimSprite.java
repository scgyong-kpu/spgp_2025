package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimSprite extends Sprite {
    protected final float fps;
    protected final int frameCount;
    protected final int frameWidth, frameHeight;
    public AnimSprite(int mipmapId, float fps, int frameCount) {
        super(mipmapId);
        this.fps = fps;
        int imageWidth = bitmap.getWidth();
        int imageHeight = bitmap.getHeight();
        if (frameCount == 0) {
            this.frameWidth = imageHeight;
            this.frameHeight = imageHeight;
            this.frameCount = imageWidth / imageHeight;
        } else {
            this.frameWidth = imageWidth / frameCount;
            this.frameHeight = imageHeight;
            this.frameCount = frameCount;
        }
        srcRect = new Rect();
    }

    int frameIndex;
    @Override
    public void draw(Canvas canvas) {
        srcRect.set(frameIndex * frameWidth, 0, (frameIndex + 1) * frameWidth, frameHeight);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
        frameIndex = (frameIndex + 1) % frameCount;
    }
}
