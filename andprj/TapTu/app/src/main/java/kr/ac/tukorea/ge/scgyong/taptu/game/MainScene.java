package kr.ac.tukorea.ge.scgyong.taptu.game;

import android.content.Context;
import android.graphics.Bitmap;

import kr.ac.tukorea.ge.scgyong.taptu.R;
import kr.ac.tukorea.ge.scgyong.taptu.data.Song;
import kr.ac.tukorea.ge.scgyong.taptu.res.BitmapBlur;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene {
    private final Song song;

    public enum Layer {
        bg,
    }
    public MainScene(Song song) {
        initLayers(Layer.values().length);

        this.song = song;
        Sprite album = new Sprite(0);
        Context context = GameView.view.getContext();
        Bitmap bitmap = song.getThumbnailBitmap(context);
        Bitmap blurredCover = BitmapBlur.blurBitmap(context, bitmap);
        album.setBitmap(blurredCover);
        float x = Metrics.width / 2, y = Metrics.height / 2;
        album.setPosition(x, y, Metrics.height, Metrics.height);
        add(Layer.bg, album);
        add(Layer.bg, new Sprite(R.mipmap.bg, x, y, Metrics.width, Metrics.height));
    }

    @Override
    public void onEnter() {
        super.onEnter();
        Context context = GameView.view.getContext();
        song.play(context);
    }

    @Override
    public void onExit() {
        song.stop();
        super.onExit();
    }
}
