package kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class AnotherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // 이 코드는 전체 화면 모드를 활성화하는 코드야.
        // WindowManager.LayoutParams.FLAG_FULLSCREEN 플래그를 설정하면,
        // 상태바가 숨겨지고 화면이 전체 화면으로 표시돼.
        //
        // getWindow()는 현재 액티비티의 윈도우를 반환하고, setFlags()는
        // 그 윈도우에 플래그를 설정하는 메소드야.
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // BallView가 전체 화면을 차지하는 커스텀 뷰일 때 XML을 쓰지 않아도 된다.
        BallView ballView = new BallView(this);
        setContentView(ballView);
        Log.d(AnotherActivity.class.getSimpleName(), "Root = " + ballView);
    }
}