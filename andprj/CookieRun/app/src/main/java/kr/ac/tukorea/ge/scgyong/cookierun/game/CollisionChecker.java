package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.Sound;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;


// CollisionChecker 클래스는 플레이어와
// 게임 내 다른 오브젝트 간의 충돌을 검사하고 처리하는 역할을 담당
public class CollisionChecker implements IGameObject {
    private final MainScene scene;
    // 충돌 검사할 대상이 있는 현재 씬(scene)
    private final Player player;

    public CollisionChecker(MainScene mainScene, Player player) {
        this.scene = mainScene;
        this.player = player;
    }

    @Override
    public void update() {

        // scene.objectsAt(Layer.item)에서 모든 아이템 오브젝트를 가져와
        // JellyItem 인스턴스만 필터링 후 충돌 검사
        ArrayList<IGameObject> items = scene.objectsAt(MainScene.Layer.item);
        for (int i = items.size() - 1; i >= 0; i--) {
            IGameObject gobj = items.get(i);
            if (!(gobj instanceof JellyItem)) {
                continue;
            }
            JellyItem item = (JellyItem) gobj;
            if (CollisionHelper.collides(player, item)) {
                Sound.playEffect(item.getSoundResId()); // 젤리 획득 효과음 재생
                if (item.index == 26) {           // 특정 젤리(index 26)라면
                    player.magnify(true); // 플레이어 강화 효과 부여
                }
                scene.remove(item);               // 젤리 씬에서 제거 (먹힌 처리)
            }
        }

        // Layer.obstacle에 있는 모든 장애물과 충돌 검사
        // 충돌 시 플레이어 hurt() 메소드 호출 → 피해 처리
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
