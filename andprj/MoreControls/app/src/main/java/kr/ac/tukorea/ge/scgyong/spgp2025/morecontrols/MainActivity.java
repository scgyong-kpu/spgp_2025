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
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        ui.nameEditText.addTextChangedListener(nameEditTextWatcher);
        ui.moneySeekBar.setOnSeekBarChangeListener(moneySeekbarChangeListener);
        setMoney(1000);
    }

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
            if (ui.immediateSwitch.isChecked()) {
                doIt();
                return;
            }
            String name = ui.nameEditText.getText().toString().trim();
            String text = getString(R.string.name_length_msg, name.length());
            ui.pageTitleTextView.setText(text);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private final SeekBar.OnSeekBarChangeListener moneySeekbarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
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
        ui.moneyValueTextView.setText(String.valueOf(money));
    }
    public void onBtnDoIt(View view) {
        doIt();
    }
    private void doIt() {
        boolean isGood = ui.goodProgrammerCheckbox.isChecked();
        String msg;
        if (isGood) {
            msg = getString(R.string.you_get_money_fmt, money);
        } else {
            msg = getString(R.string.you_have_nothing);
        }
        String name = ui.nameEditText.getText().toString().trim();
        if (name.isEmpty()) {
            name = getString(R.string.noname);
        }
        String text = getString(R.string.main_msg_fmt, name, msg);
        ui.pageTitleTextView.setText(text);
    }

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