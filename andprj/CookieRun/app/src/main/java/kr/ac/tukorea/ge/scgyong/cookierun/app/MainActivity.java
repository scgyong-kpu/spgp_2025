package kr.ac.tukorea.ge.scgyong.cookierun.app;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.scgyong.cookierun.databinding.ActivityMainBinding;
import kr.ac.tukorea.ge.scgyong.cookierun.game.Player;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding ui;
    // ui: View Binding을 통해 레이아웃의 뷰들과 연결합니다.
    private int stage, cookieIndex;
    // cookieIndex: 현재 선택된 쿠키 캐릭터 인덱스.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        Player.load(this);
        // Player 데이터 로드: Player.load(this)로 쿠키 정보 로딩

        setStage(1);
        setCookieIndex(0);
        // 기본값 설정

        //if (BuildConfig.DEBUG) {
        //    startActivity(new Intent(this, CookieRunActivity.class));
        //}
    }

    public void onBtnStartGame(View view) {
        startGame();
    }

    private void startGame() {
        // 선택된 stage와 cookieId를 인텐트로 전달
        Intent intent = new Intent(this, CookieRunActivity.class);
        intent.putExtra(CookieRunActivity.KEY_STAGE, stage);
        intent.putExtra(CookieRunActivity.KEY_COOKIE_ID, Player.COOKIE_IDS[cookieIndex]);
        startActivity(intent);
    }

    private void setStage(int stage) {
        this.stage = stage;
        // 스테이지 텍스트 뷰를 업데이트
        String text = getString(R.string.title_stage_fmt, stage);
        ui.stageTextView.setText(text);

        // 이전/다음 버튼 활성화 여부를 설정
        ui.prevButton.setEnabled(stage > 1);
        ui.nextButton.setEnabled(stage < 3);
    }
    private void setCookieIndex(int index) {
        this.cookieIndex = index;
        try {
            int cookieId = Player.COOKIE_IDS[index];
            AssetManager assets = getAssets();
            //현재 선택한 쿠키의 이미지와 이름을 표시
            String fileName = "cookies/" + cookieId + "_icon.png";
            InputStream is = assets.open(fileName);
            Bitmap bmp = BitmapFactory.decodeStream(is);
            ui.cookieImageView.setImageBitmap(bmp);

            // 쿠키 이름은 Player.cookieInfoMap에서 조회
            Player.CookieInfo cookieInfo = Player.cookieInfoMap.get(cookieId);
            if (cookieInfo != null) {
                ui.cookieNameTextView.setText(cookieInfo.name);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ui.prevCookieButton.setEnabled(index > 0);
        ui.nextCookieButton.setEnabled(index < Player.COOKIE_IDS.length - 1);
    }

    // 이전 스테이지로 이동
    public void onBtnPrevious(View view) {
        setStage(stage - 1);
    }

    // 	다음 스테이지로 이동
    public void onBtnNext(View view) {
        setStage(stage + 1);
    }
    public void onBtnPreviousCookie(View view) {
        setCookieIndex(cookieIndex - 1);
    }
    public void onBtnNextCookie(View view) {
        setCookieIndex(cookieIndex + 1);
    }
}