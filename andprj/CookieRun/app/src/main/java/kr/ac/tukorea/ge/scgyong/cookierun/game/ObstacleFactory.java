package kr.ac.tukorea.ge.scgyong.cookierun.game;

public class ObstacleFactory {
    public static final int COUNT = 6; // 임시로 FallingObstacle 이 많이 만들어지도록 해 본다

    public static Obstacle get(int index, float left, float top) {
        switch (index) {
            case 0:
                return Obstacle.get(left, top);
            case 1: case 2:
                return AnimObstacle.get(index - 1, left, top);
            case 3: default:
                return FallingObstacle.get(left, top);
        }
    }
}
