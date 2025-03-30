package kr.ac.tukorea.ge.scgyong.spgp2025.morecontrols;

import android.os.Bundle;

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
        ui = ActivityAnotherBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
    }
}