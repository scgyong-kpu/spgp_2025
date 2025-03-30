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
    private ImageButton previousCardButton;
    private ImageButton[] cardImageButtons;

    private int[] cardResIds = new int[] {
            // 버튼에 각각 리소스 id를 tag로 달아놓고 눌렀을 때 보여준다
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd,
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd,
    };
    private int flips;
    private int openCardCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Binding 객체를 이용하여 화면 로딩
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        cardImageButtons = new ImageButton[]{
                ui.card00, ui.card01, ui.card02, ui.card03,
                ui.card10, ui.card11, ui.card12, ui.card13,
                ui.card20, ui.card21, ui.card22, ui.card23,
                ui.card30, ui.card31, ui.card32, ui.card33,
        };

        startGame();
    }

    private void startGame() {
        //shuffleCards();

        for (int i = 0; i < cardResIds.length; i++) {
            // 버튼에 각각 리소스 id를 tag로 달아놓고 눌렀을 때 보여준다
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
    }

    public void setFlips(int flips) {
        // score_fmt를 string resource로부터 얻는다.
        // scoreTextView에 "Flips: 3" 같은 형식의 문자열을 설정하는 것
        // Flips: %d라는 형식의 문자열에서 %d 부분이 flips 값으로 변환되어 UI에 표시
        this.flips = flips;
        Resources res = getResources();
        // getString( fmt_id, .. ) fmt는 format(포맷) 의 약자 문자열 포맷팅
        String text = res.getString(R.string.score_fmt, flips);
        ui.scoreTextView.setText(text);
    }

    public void onBtnCard(View view) {
        // Log Cat으로 로그 보기
        Log.d("MainActivity", "Btn ID=" + view.getId());

        // Toast 사용
        Toast.makeText(this, "Btn ID=" + view.getId(), Toast.LENGTH_SHORT).show();

        // 같은 버튼이 눌렸다면 아무것도 안하기 Toast.LENGTH_SHORT → 메시지 표시 시간(짧게).show() → 실제로 화면에 표시.
        ImageButton btn = (ImageButton) view;
        if (btn == previousCardButton) {
            Toast.makeText(this, R.string.toast_same_card, Toast.LENGTH_SHORT).show();
            return;
        }

        int previousResourceId = 0;

        // 눌린 카드가 있었을 때에만 뒷면전환한다. - 첫번째 클릭땐 안해야한다
        if (previousCardButton != null) {
            previousCardButton.setImageResource(R.mipmap.card_blue_back);
            previousResourceId = (Integer) previousCardButton.getTag();
        }

        int resId = (Integer) btn.getTag();
        btn.setImageResource(resId);

        setFlips(flips + 1);

        if (previousResourceId == resId) {
            previousCardButton.setVisibility(View.INVISIBLE);
            btn.setVisibility(View.INVISIBLE);
            previousCardButton = null;

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