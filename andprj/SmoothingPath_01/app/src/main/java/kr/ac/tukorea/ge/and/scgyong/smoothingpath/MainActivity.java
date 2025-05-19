package kr.ac.tukorea.ge.and.scgyong.smoothingpath;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import kr.ac.tukorea.ge.and.scgyong.smoothingpath.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private @NonNull ActivityMainBinding ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        ui.pathView.setCallback(pathViewCallback);
        updatePointCount(0);
    }
    private PathView.CallBack pathViewCallback = new PathView.CallBack() {
        @Override
        public void onPathChanged(int count) {
            updatePointCount(count);
        }
    };

    public void onCheckClosed(View view) {
        ui.pathView.closePath(ui.closedCheckbox.isChecked());
    }

        public void onBtnClear(View view) {
        ui.pathView.clearPoints();
        updatePointCount(0);
    }

    public void updatePointCount(int count) {
        String text = getString(R.string.point_count_fmt, count);
        ui.countTextView.setText(text);
    }
}