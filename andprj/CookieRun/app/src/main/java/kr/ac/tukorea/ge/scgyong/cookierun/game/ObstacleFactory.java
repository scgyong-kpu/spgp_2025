package kr.ac.tukorea.ge.scgyong.cookierun.game;

public class ObstacleFactory {
    public static Obstacle get(char mapChar, float left, float top) {
        switch (mapChar) {
            case 'X':
                return Obstacle.get(left, top);
            case 'Y': case 'Z':
                return AnimObstacle.get(mapChar - 'Y', left, top);
            case 'W':
                return FallingObstacle.get(left, top);
            default: // 위 case 가 아니라면 이 mapChar 는 Obstacle 이 아니다
                return null;
        }
    }
}
