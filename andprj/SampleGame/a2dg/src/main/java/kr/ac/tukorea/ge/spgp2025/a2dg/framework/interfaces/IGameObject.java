package kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces;

import android.graphics.Canvas;


// 인터페이스는 다른 클래스들이 구현해야 하는 메소드들의 계약을 정의합니다.
// 즉, 이 인터페이스를 구현하는 클래스는
// 반드시 update()와 draw(Canvas canvas) 메소드를 구현해야 합니다.
//
 // **IGameObject**는 게임 객체가 가져야 할 기본적인
// 두 가지 동작인 업데이트와 그리기를 강제하는 인터페이스입니다.
public interface IGameObject {
    public void update();
    public void draw(Canvas canvas);
}
