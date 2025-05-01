package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;

public class MapObject extends Sprite implements IRecyclable {
    public MapObject() {
        super(0);
    }

    @Override
    public void onRecycle() {
    }
}
