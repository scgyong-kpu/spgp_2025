package kr.ac.tukorea.ge.scgyong.taptu.app;

import android.os.Bundle;
import android.util.Log;

import kr.ac.tukorea.ge.scgyong.taptu.BuildConfig;
import kr.ac.tukorea.ge.scgyong.taptu.data.Song;
import kr.ac.tukorea.ge.scgyong.taptu.game.MainScene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class MainGameActivity extends GameActivity {
    private static final String TAG = MainGameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
        super.onCreate(savedInstanceState);
        int songIndex = Song.selectedIndex;
        Song song = Song.songs.get(songIndex);
        Log.d(TAG, "Index=" + songIndex + " song=" + song);
        new MainScene().push();
    }
}