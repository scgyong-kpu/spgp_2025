package kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private @NonNull ActivityMainBinding ui;
    private int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // viewBinding 객체를 사용하여 화면 로드
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        // 1번째 : this, [2번째] : 멤버 변수에게, 3번째 : 즉석에서 만들기 <- OnCreate가 비대해진다
        ui.nameEditText.addTextChangedListener(nameEditTextWatcher);
        //  SeekBar( 동그라미 드래그 )의 값이 변경될 때 실행할 콜백 리스너를 등록하는 함수
        ui.moneySeekBar.setOnSeekBarChangeListener(moneySeekbarChangeListener);
        setMoney(1000);
    }


    // nameEditText의 입력이 바뀔 때마다 불린다.  onTextChanged
    // 이벤트 리스너
    // public interface TextWatcher()라는 순수가상클래스를 만들었기에 -> 정의를 해줘야한다 -> 객체를 만들 수 있도록
    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    private final TextWatcher nameEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Switch(오른쪽/왼쪽 켜는거)가 켜져있는지 확인 -> Apply Immediately
            if (ui.immediateSwitch.isChecked()) {
                doIt(); // Switch가 켜져 있으면 즉시 어떤 작업을 수행
                return;
            }
            // 입력된 이름을 가져와 양쪽 공백을 제거
            String name = ui.nameEditText.getText().toString().trim();
            // 이름 길이에 대한 메시지를 생성
            String text = getString(R.string.name_length_msg, name.length());
            // 메시지를 TextView에 설정
            ui.pageTitleTextView.setText(text);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    // SeekBar의 값이 변경될 때 특정 작업을 처리하는 리스너
    // 리스너(Listener)는 이벤트를 감지하고, 특정 객체에 의해 호출되는 콜백 함수를 포함하는 객체야.
    //즉, 콜백을 호출해주는 역할을 하는 객체
    private final SeekBar.OnSeekBarChangeListener moneySeekbarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            // SeekBar의 값이 변경되었을 때 호출
            // 값이 바뀌면 값을 설정한다.
            setMoney(seekBar.getProgress());
            if (ui.immediateSwitch.isChecked()) {
                doIt();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private void setMoney(int money) {
        this.money = money;
        // int를 string으로 바꿔서
        ui.moneyValueTextView.setText(String.valueOf(money));
    }

    // Button 눌릴 때 호출되는 함수
    public void onBtnDoIt(View view) {
        doIt();
    }

    // Button 눌릴 때 호출되는 함수
    private void doIt() {
        boolean isGood = ui.goodProgrammerCheckbox.isChecked();
        String msg;
        if (isGood) {
            msg = getString(R.string.you_get_money_fmt, money);
        } else {
            msg = getString(R.string.you_have_nothing);
        }

        // EditText로부터 사용자가 타이밍한 메시지를 얻기
        // trim -> 엔터, 공백 같은거 없애주는 함수 -> 유요한 데이터 얻게
        String name = ui.nameEditText.getText().toString().trim();
        if (name.isEmpty()) {
            name = getString(R.string.noname);
        }
        String text = getString(R.string.main_msg_fmt, name, msg);

        ui.pageTitleTextView.setText(text);
    }

    // CheckBox 누르면 불리는 함수
    // int strID -> ID인 정수를 받아서 문자열을 수정할것임
    // setText 등 거의 모든 API는 ""(CharSequence)을 받는 버전과 int (R.string.xxx)를
    // 받는 버전이 마련되어 있다
    // 리소스 ID를 의미하며, 실제로는 **정수값(int)**
    public void onCheckGoodProgrammer(View view) {
        boolean isGood = ui.goodProgrammerCheckbox.isChecked();
        int strId = isGood ? R.string.good_news : R.string.bad_news;
        ui.pageTitleTextView.setText(strId);
    }

    public void onRadioWidth(View view) {
        float width;
        int radioId = view.getId();
        if (radioId == R.id.radioWidthThin) {
            width = 5;
        } else if (radioId == R.id.radioWidthMedium) {
            width = 20;
        } else {
            width = 50;
        }
        ui.myView1.paint.setStrokeWidth(width);
        ui.myView1.invalidate();
    }

    public void onRadioCap(View view) {
        Paint.Cap cap;
        int radioId = view.getId();
        if (radioId == R.id.radioCapButt) {
            cap = Paint.Cap.BUTT;
        } else if (radioId == R.id.radioCapSquare) {
            cap = Paint.Cap.SQUARE;
        } else {
            cap = Paint.Cap.ROUND;
        }
        ui.myView1.paint.setStrokeCap(cap);
        ui.myView1.invalidate();
    }

    public void onRadioJoin(View view) {
        Paint.Join join;
        int radioId = view.getId();
        if (radioId == R.id.radioJoinBevel) {
            join = Paint.Join.BEVEL;
        } else if (radioId == R.id.radioJoinMiter) {
            join = Paint.Join.MITER;
        } else {
            join = Paint.Join.ROUND;
        }
        ui.myView1.paint.setStrokeJoin(join);
        ui.myView1.invalidate();
    }

    // intent : "무언가를 하고 싶다는 의도(Intent)를 안드로이드에게 전달하는 객체"
    // Intent.ACTION_VIEW → 무언가를 보겠다는 의도를 담은 상수야.
    // new Intent(...) → 이 인텐트를 만들어서 시스템에 "이걸 보고 싶어요"라고 말한 거지.
    // startActivity(intent) → 시스템에 이 인텐트를 넘겨서 적절한 앱(여기선 브라우저)을 실행하라고 요청
    public void onBtnOpenNaver(View view) {
        Uri uri = Uri.parse("https://www.naver.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void onBtnOpenAnother(View view) {
//        Log.d(TAG, "Opening Another Activity");
        Intent intent = new Intent(this, AnotherActivity.class);
        startActivity(intent);
    }
}