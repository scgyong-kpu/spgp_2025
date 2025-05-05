package kr.ac.tukorea.ge.scgyong.cookierun.game;

public class ObstacleFactory {
    public static final int COUNT = 4;

    public static Obstacle get(int index, float left, float top) {
        switch (index) {
            case 0:
                return Obstacle.get(left, top);
            case 1: case 2:
                return AnimObstacle.get(index - 1, left, top);
            case 3:
                return FallingObstacle.get(left, top);
            default:
                break;
        }
        return null;
    }
}
