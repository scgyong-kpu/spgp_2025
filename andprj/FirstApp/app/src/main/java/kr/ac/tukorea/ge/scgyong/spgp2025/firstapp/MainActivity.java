package kr.ac.tukorea.ge.scgyong.spgp2025.firstapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.scgyong.spgp2025.firstapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 추가 선언 과정 설명.
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.mainButton.setOnClickListener(m_mainButtonListener);
        binding.pushMeButton.setOnClickListener(m_pushMeButtonListener);
    }

    private View.OnClickListener m_mainButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            binding.mainTextView.setText("Main Button Clicked");
            binding.subTextView.setText("Main is 4 characters long");
        }
    };
    private View.OnClickListener m_pushMeButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            binding.mainTextView.setText("PushMe Button Clicked");
            binding.subTextView.setText("PushMe is 6 characters long");
        }
    };
}