package kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private @NonNull ActivityMainBinding ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
    }

    public void onBtnDoIt(View view) {
        doIt();
    }
    private void doIt() {
        boolean isGood = ui.goodProgrammerCheckbox.isChecked();
        int strId = isGood ? R.string.you_get_one_grand : R.string.you_have_nothing; // Alt+Enter here
        String msg = getString(strId);
        String name = ui.nameEditText.getText().toString();
        String text = getString(R.string.main_msg_fmt, name, msg);
        ui.pageTitleTextView.setText(text);
    }

    public void onCheckGoodProgrammer(View view) {
        boolean isGood = ui.goodProgrammerCheckbox.isChecked();
        int strId = isGood ? R.string.good_news : R.string.bad_news;
        ui.pageTitleTextView.setText(strId);
    }
}