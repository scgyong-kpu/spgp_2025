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
        boolean isGood = ui.goodProgrammerCheckbox.isChecked();
        int strId = isGood ? R.string.you_get_one_grand : R.string.you_have_nothing; // Alt+Enter here
        ui.pageTitleTextView.setText(strId);
    }
}