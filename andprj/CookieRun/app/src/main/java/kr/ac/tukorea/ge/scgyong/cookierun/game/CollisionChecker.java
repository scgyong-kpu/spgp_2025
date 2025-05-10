package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.Sound;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private final MainScene scene;
    private final Player player;

    public CollisionChecker(MainScene mainScene, Player player) {
        this.scene = mainScene;
        this.player = player;
    }

    @Override
    public void update() {
        ArrayList<IGameObject> items = scene.objectsAt(MainScene.Layer.item);
        for (int i = items.size() - 1; i >= 0; i--) {
            IGameObject gobj = items.get(i);
            if (!(gobj instanceof JellyItem)) {
                continue;
            }
            JellyItem item = (JellyItem) gobj;
            if (CollisionHelper.collides(player, item)) {
                Sound.playEffect(item.getSoundResId());
                if (item.index == 26) {
                    player.magnify(true);
                }
                scene.remove(item);
            }
        }
        ArrayList<IGameObject> obstacles = scene.objectsAt(MainScene.Layer.obstacle);
        for (int i = obstacles.size() - 1; i >= 0; i--) {
            Obstacle obstacle = (Obstacle) obstacles.get(i);
            if (CollisionHelper.collides(player, obstacle)) {
                player.hurt(obstacle);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
