package kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols.databinding.ActivityAnotherBinding;

public class AnotherActivity extends AppCompatActivity {

    private @NonNull ActivityAnotherBinding ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // API 31 (Android 12)
        // Material3 프리뷰 등장 (Theme.Material3)
        // → 기본적으로 ActionBar 숨김
        // 위 API31 이후는 위 코드가 null 을 리턴한다

        ui = ActivityAnotherBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
    }
}