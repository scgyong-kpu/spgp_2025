package kr.ac.tukorea.ge.spgp2025.a2dg.framework.util;

import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;

public class CollisionHelper {
    public static boolean collides(IBoxCollidable obj1, IBoxCollidable obj2) {
        RectF r1 = obj1.getCollisionRect();
        RectF r2 = obj2.getCollisionRect();
        return collides(r1, r2);
    }
    public static boolean collides(RectF r1, RectF r2) {
        if (r1.left > r2.right) return false;
        if (r1.top > r2.bottom) return false;
        if (r1.right < r2.left) return false;
        if (r1.bottom < r2.top) return false;
        return true;
    }

    public static boolean collidesRadius(Sprite s1, Sprite s2) {
        double dx = s1.getX() - s2.getX();
        double dy = s1.getY() - s2.getY();
        double dist_sq = dx * dx + dy * dy;
        double sum_of_two_radii = s1.getRadius() + s2.getRadius();
        double sum_sq = sum_of_two_radii * sum_of_two_radii;
        return dist_sq < sum_sq;
    }
}
