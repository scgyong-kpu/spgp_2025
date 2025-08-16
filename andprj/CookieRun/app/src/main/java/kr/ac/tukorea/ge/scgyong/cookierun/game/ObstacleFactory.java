package kr.ac.tukorea.ge.scgyong.cookierun.game;


//  **문자(mapChar)**에 따라 알맞은 장애물(Obstacle)
//  객체를 생성해 주는 **장애물 생성기(팩토리 클래스)**
public class ObstacleFactory {
    // mapChar에 따라 어떤 장애물(Obstacle, AnimObstacle, FallingObstacle)을 생성할지 결정
    //
    //생성 위치는 left, top으로 전달됨
    //장애물은 재사용 가능한 객체(get으로 반환)
    public static Obstacle get(char mapChar, float left, float top) {
        switch (mapChar) {
            case 'X':
                return Obstacle.get(left, top);
                // Y/Z 문자 하나로 서로 다른 애니메이션 장애물을 선택함
            case 'Y': case 'Z':
                return AnimObstacle.get(mapChar - 'Y', left, top);

                //  떨어지는 장애물 FallingObstacle 생
            case 'W':
                return FallingObstacle.get(left, top);
            default: // 위 case 가 아니라면 이 mapChar 는 장애물이 아니다
                return null;
        }
    }
}
