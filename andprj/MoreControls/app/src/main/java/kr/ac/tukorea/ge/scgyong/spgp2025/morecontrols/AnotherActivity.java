package kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols.databinding.ActivityAnotherBinding;

public class AnotherActivity extends AppCompatActivity {

    private @NonNull ActivityAnotherBinding ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ui = ActivityAnotherBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        Log.d(AnotherActivity.class.getSimpleName(), "Root = " + ui.getRoot());
    }
}