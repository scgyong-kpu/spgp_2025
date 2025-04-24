package kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;

// 게임에서 씬(장면)을 관리하는 역할을 합니다.
// 씬은 게임의 각 상태나 레벨을 나타낼 수 있으며, 게임 루프에서 중요한 역할을 합니다.
// 이 클래스는 게임 객체들의 업데이트, 그리기, 씬 전환 등을 담당합니다.
public class Scene {
    private static final String TAG = Scene.class.getSimpleName();

    protected final ArrayList<IGameObject> gameObjects = new ArrayList<>();
    // IGameObject 인터페이스를 구현하는 게임 객체들을 담는 리스트입니다.
    // 게임 씬 내에 존재하는 모든 게임 객체들을 관리합니다.
    //
     // IGameObject는 각 게임 객체가 반드시 구현해야 하는 인터페이스일 가능성이 높습니다.
    // 이 인터페이스는 update(), draw() 등의 메서드를 정의하고 있을 것입니다.

    //////////////////////////////////////////////////
    // Game Object Management
    public void add(IGameObject gameObject) {
        gameObjects.add(gameObject);
        //Log.d(TAG, gameObjects.size() + " objects in " + this);
    }

    //////////////////////////////////////////////////
    // Game Loop Functions

    public void update() {
        // count는 게임 객체의 개수를 저장
        int count = gameObjects.size();

        // 역순으로 호출하는 이유는 게임 객체들이 리스트에서 제거될 수 있기 때문
        // ConcurrentModificationException을 해결하는 한 가지 방법. 하지만 이 방법으로 모든게 해결되는 것은
        // 아니다. delay 시키는 방법도 있고, post runnable 시키는 방법도 있다. 이 중 몇 가지는 객체의 갯수가
        // 맞지 않게 되는 문제도 발생한다. 거꾸로 카운트의 경우에도 자신을 삭제하거나 이미 지난 인덱스를 삭제하는건
        // 괜찮지만 아직 루프에 다다르지 않은 것을 삭제할 때는 여전히 문제이다. 간단한 방법으로 이번 프로젝트/
        // 프레임워크에서 사용하기로 한다.
        for (int i = count - 1; i >= 0; i--) {
            IGameObject gobj = gameObjects.get(i);
            gobj.update();
        }
    }
    public void draw(Canvas canvas) {
        for (IGameObject gobj : gameObjects) {
            gobj.draw(canvas);
        }
    }

    //////////////////////////////////////////////////
    // Scene Stack Functions

    public void push() {
        // 현재 씬을 씬 스택에 추가하는 메소드입니다.
        // GameView.view.pushScene(this)를 호출하여 이 씬을 씬 스택에 푸시합니다.
        GameView.view.pushScene(this);
    }
    public static Scene pop() {
        // 씬 스택에서 마지막 씬을 제거하고 반환하는 메소드입니다.
        return GameView.view.popScene();
    }
    public static Scene top() {
        // 씬 스택에서 가장 위에 있는 씬을 반환하는 메소드입니다.
        return GameView.view.getTopScene();
    }

    //////////////////////////////////////////////////
    // Overridables
    public boolean onTouchEvent(MotionEvent event) {
        // 기본적으로 터치 이벤트를 처리하는 메소드입니다. 반환 값 false는 이벤트가 처리되지 않았음을 의미합니다.
        return false;
    }

    public void onEnter() {
        // 씬이 활성화될 때 호출됩니다. 씬이 전환될 때(이 씬이 화면에 등장할 때) 실행됩니다.
        // 로그 메시지를 출력하여 진입을 추적할 수 있습니다.
        Log.v(TAG, "onEnter: " + getClass().getSimpleName());
    }
    public void onPause() {
        // 씬이 일시 중지될 때 호출됩니다. 예를 들어 다른 씬이 전면에 올 때 호출됩니다.
        Log.v(TAG, "onPause: " + getClass().getSimpleName());
    }
    public void onResume() {
        //씬이 다시 활성화될 때 호출됩니다.
        Log.v(TAG, "onResume: " + getClass().getSimpleName());
    }
    public void onExit() {
        // 씬이 종료될 때 호출됩니다. 씬이 더 이상 보이지 않게 되면 호출됩니다.
        Log.v(TAG, "onExit: " + getClass().getSimpleName());
    }
    public boolean onBackPressed() {
        // 뒤로 가기 버튼이 눌렸을 때 호출되는 메소드입니다. 기본 구현은 false를 반환합니다.
        //
         // 특정 Scene에서 Pop이 되지 않게 하려면 Scene.BackPressed를 구현해서 RETURN TRUE를 하면 된다

        return false;
    }
}
