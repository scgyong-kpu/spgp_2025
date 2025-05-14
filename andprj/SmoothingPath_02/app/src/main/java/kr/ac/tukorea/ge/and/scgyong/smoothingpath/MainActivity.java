package kr.ac.tukorea.ge.and.scgyong.smoothingpath;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.and.scgyong.smoothingpath.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private @NonNull ActivityMainBinding ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        ui.pathView.setCallback(pointsCallback);
        updatePointsCount(0);
    }
    private PathView.Callback pointsCallback = new PathView.Callback() {
        @Override
        public void onPointsCountChange(int count) {
            updatePointsCount(count);
        }
    };

    public void onBtnClear(View view) {
        ui.pathView.clearPoints();
        updatePointsCount(0);
    }

    void updatePointsCount(int count) {
        String text = getString(R.string.points_count_fmt, count);
        ui.countTextView.setText(text);
    }
}