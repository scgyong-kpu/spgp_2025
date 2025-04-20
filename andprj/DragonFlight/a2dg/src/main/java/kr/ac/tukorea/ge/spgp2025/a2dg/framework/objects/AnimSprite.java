package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

public class AnimSprite extends Sprite {
    protected final float fps;
    protected final int frameCount;
    public AnimSprite(int mipmapId, float fps, int frameCount) {
        super(mipmapId);
        this.fps = fps;
        this.frameCount = frameCount;
    }
}
