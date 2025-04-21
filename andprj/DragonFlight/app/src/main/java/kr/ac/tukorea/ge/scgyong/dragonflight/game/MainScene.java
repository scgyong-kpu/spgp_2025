package kr.ac.tukorea.ge.scgyong.dragonflight.game;

import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.scgyong.dragonflight.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Fighter fighter;
    public MainScene() {
        this.fighter = new Fighter();
        add(fighter);
        add(new EnemyGenerator());

//        AnimSprite animSprite = new AnimSprite(R.mipmap.enemy_01, 10);
//        animSprite.setPosition(450f, 450f, 90f);
//        add(animSprite);
    }

    // Overridables

    @Override
    public void update() {
        super.update();
        checkCollision();
    }

    private void checkCollision() {
        int count = gameObjects.size();
        for (int i1 = count - 1; i1 >= 0; i1--) {
            count = gameObjects.size();
            if (i1 >= count) {
                i1 = count - 1; // enemy 와 bullet 이 모두 삭제된 경우에는 count 가 더 많이 줄었을 수도 있다.
            }
            IGameObject o1 = gameObjects.get(i1);
            if (!(o1 instanceof Enemy)) {
                continue;
            }
            Enemy enemy = (Enemy) o1;
//            boolean removed = false;
            count = gameObjects.size();
            for (int i2 = count - 1; i2 >= 0; i2--) {
                IGameObject o2 = gameObjects.get(i2);
                if (!(o2 instanceof Bullet)) {
                    continue;
                }
                Bullet bullet = (Bullet) o2;
                if (CollisionHelper.collides(enemy, bullet)) {
                    Log.d(TAG, "Collision !! : Bullet@" + System.identityHashCode(bullet) + " vs Enemy@" + System.identityHashCode(enemy));
                    remove(bullet);
                    remove(enemy);
//                    removed = true;
                    break;
                }
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return fighter.onTouch(event);
    }
}
