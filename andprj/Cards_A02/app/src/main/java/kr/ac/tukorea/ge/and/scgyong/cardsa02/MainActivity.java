package kr.ac.tukorea.ge.and.scgyong.cardsa02;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import kr.ac.tukorea.ge.and.scgyong.cardsa02.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private @NonNull ActivityMainBinding ui;
    private ImageButton previousCardButton; // 바로 전에 뒤집은 카드
    private ImageButton[] cardImageButtons;
    // 지역변수였던 buttons를 멤버 변수로 옮긴다 --> scope가 넓어졌으므로 변수 이름도 길게 바꿨다.

    // 카드 이미지 리소스 ID 배열 (짝을 맞추기 위해 같은 카드 2장씩 있음)
    // 상수는 함수 밖으로 빼낸다.
    private int[] cardResIds = new int[] {
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd,
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd,
    };
    private int flips; // 뒤집은 횟수 카운트
    private int openCardCount; // 남은 카드 수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Binding 객체를 이용하여 화면 로딩
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        // 16개의 카드 버튼 배열
        cardImageButtons = new ImageButton[]{
                ui.card00, ui.card01, ui.card02, ui.card03,
                ui.card10, ui.card11, ui.card12, ui.card13,
                ui.card20, ui.card21, ui.card22, ui.card23,
                ui.card30, ui.card31, ui.card32, ui.card33,
        };

        startGame();
    }

    private void startGame() {
        shuffleCards();

        for (int i = 0; i < cardResIds.length; i++) {
            // 버튼에 각각 리소스 id를 tag로 달아놓고 눌렀을 때 보여준다
            // 각 카드에 리소스 ID를 tag로 저장 => [ 각 카드의 앞면 리소스 정보 ]
            // 리소스 인덱스를 순서대로 태그로 지정한 이유는, 각 카드에 대해 버튼을 눌렀을 때
            // 해당 카드에 해당하는 리소스를 빠르고 효율적으로 추적하기 위해서

            Integer resId = cardResIds[i];

            // 재시작시 디자인 타임의 상태로 되돌리는 코드
            cardImageButtons[i].setVisibility(View.VISIBLE);
            cardImageButtons[i].setImageResource(R.mipmap.card_blue_back);
            cardImageButtons[i].setTag(resId);
        }

        // Flips, Prev도 초기화
        setFlips(0);
        previousCardButton = null;
        openCardCount = cardResIds.length;
    }

    // Fisher-Yates Shuffle Algorithm
    private void shuffleCards() {
        Random rand = new Random();
        for (int i = 0; i < cardResIds.length; i++) {
            int r = rand.nextInt(cardResIds.length);
            int resId = cardResIds[i];
            cardResIds[i] = cardResIds[r];
            cardResIds[r] = resId;
        }
        //rand.nextInt(RES_IDS.length)는 0부터 RES_IDS.length - 1 사이의 랜덤한 정수를 반환
    }

    public void setFlips(int flips) {
        // score_fmt를 string resource로부터 얻는다.
        // scoreTextView에 "Flips: 3" 같은 형식의 문자열을 설정하는 것
        // Flips: %d라는 형식의 문자열에서 %d 부분이 flips 값으로 변환되어 UI에 표시
        this.flips = flips;
        Resources res = getResources();  // 리소스 접근 객체 얻기

        // getString( fmt_id, .. ) fmt는 format(포맷) 의 약자 문자열 포맷팅
        String text = res.getString(R.string.score_fmt, flips);

        ui.scoreTextView.setText(text);
    }

    // 카드 버튼 클릭 시 동작
    public void onBtnCard(View view) {
        // Log Cat으로 로그 보기
        Log.d("MainActivity", "Btn ID=" + view.getId());

        // Toast 사용
        Toast.makeText(this, "Btn ID=" + view.getId(), Toast.LENGTH_SHORT).show();

        // Toast.LENGTH_SHORT → 메시지 표시 시간(짧게).show() → 실제로 화면에 표시.
        // -> 같은거 클릭했을 경우.
        ImageButton btn = (ImageButton) view;
        if (btn == previousCardButton) {
            Toast.makeText(this, R.string.toast_same_card, Toast.LENGTH_SHORT).show();
            return;
        }

        int previousResourceId = 0;

        // 이전에 눌린 카드가 있었을 때에만 뒷면전환한다.
        if (previousCardButton != null) {
            // 먼저 선택해서 앞면으로 뒤집어 놓은 카드는 다사 뒷면으로 설정
            previousCardButton.setImageResource(R.mipmap.card_blue_back);
            previousResourceId = (Integer) previousCardButton.getTag();
        }

        // 현재 선택한 카드(버튼)
        //  이 리소스 ID는 카드의 원래 이미지를 나타내는 값 (앞면 -> 스페이드3 이런거)
        int resId = (Integer) btn.getTag();
        btn.setImageResource(resId);

        setFlips(flips + 1);

        if (previousResourceId == resId) {
            previousCardButton.setVisibility(View.INVISIBLE);
            btn.setVisibility(View.INVISIBLE);
            previousCardButton = null; // -> 드러나지 않는 버그를 막는다.

            // 카드가 모두 오픈되어 게임오버가 되어도 재시작을 묻자
            openCardCount -= 2;
            if (openCardCount == 0) {
                askRestart();
            }
        } else {
            previousCardButton = btn;
        }
    }

    public void onBtnRestart(View view) {
        askRestart();
    }

    // 3월 26일 - Android에서 AlertDialog(알림 대화상자) 를 만드는 코드
    private void askRestart() {
        // 다이얼로그(AlertDialog)를 만드는 빌더 클래스.
        //this: 현재 Activity(또는 Context)를 의미
        new AlertDialog.Builder(this)

                // Restart Dialog에 사용되는 문자열을 모두 strings.xml로 빼낸다.

                // 제목(title)과 메시지(message) 설정
                .setTitle(R.string.restart_dlg_title)
                .setMessage(R.string.restart_dlg_message)

                // 예(확인) 버튼 추가 -> 버튼을 누르면 startGame(); 실행 (즉, 게임을 다시 시작)
                .setPositiveButton(R.string.restart_dlg_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame();
                    }
                })
                // "아니오(취소)" 버튼을 추가 (R.string.restart_dlg_no → "아니오")
                //null이므로 아무 동작도 안 함.
                .setNegativeButton(R.string.restart_dlg_no, null)
                // 다이얼로그를 생성(create())하고 화면에 표시(show())
                .create()
                .show();
    }

}