package kr.ac.tukorea.ge.scgyong.cookierun.game;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.HorzScrollBackground;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    public enum Layer {
        bg; // enum case 외에 다른 멤버가 있으므로, 마지막에 semi-colon (;) 을 써야 한다.
        public static final int COUNT = values().length; // COUNT 의 정의는 항상 복사해서 붙여넣자.
    }

    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_1, 100f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_2, 200f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.cookie_run_bg_3, 300f));
    }
}
