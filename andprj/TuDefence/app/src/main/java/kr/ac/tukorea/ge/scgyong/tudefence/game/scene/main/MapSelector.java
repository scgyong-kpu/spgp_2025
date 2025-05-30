package kr.ac.tukorea.ge.scgyong.tudefence.game.scene.main;

import kr.ac.tukorea.ge.scgyong.tudefence.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;

public class MapSelector extends Sprite {
    private final MainScene scene;

    public MapSelector(MainScene scene) {
        super(R.mipmap.selection);
        this.scene = scene;
        setPosition(1600, 500, 200, 200); // 임시 위치
    }
}
