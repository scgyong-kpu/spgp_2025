package kr.ac.tukorea.ge.and.scgyong.cardsa02;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import kr.ac.tukorea.ge.and.scgyong.cardsa02.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private @NonNull ActivityMainBinding ui;
    private ImageButton previousCardButton;
    private ImageButton[] cardImageButtons;

    private int[] cardResIds = new int[] {
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd,
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd,
    };
    private int flips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        cardImageButtons = new ImageButton[]{
                ui.card00, ui.card01, ui.card02, ui.card03,
                ui.card10, ui.card11, ui.card12, ui.card13,
                ui.card20, ui.card21, ui.card22, ui.card23,
                ui.card30, ui.card31, ui.card32, ui.card33,
        };

        startGame();
    }

    private void startGame() {
        shuffleCards();

        for (int i = 0; i < cardResIds.length; i++) {
            Integer resId = cardResIds[i];
            cardImageButtons[i].setVisibility(View.VISIBLE);
            cardImageButtons[i].setImageResource(R.mipmap.card_blue_back);
            cardImageButtons[i].setTag(resId);
        }
    }

    private void shuffleCards() {
        // Fisher-Yates Algorithm
        Random rand = new Random();
        for (int i = 0; i < cardResIds.length; i++) {
            int r = rand.nextInt(cardResIds.length);
            int resId = cardResIds[i];
            cardResIds[i] = cardResIds[r];
            cardResIds[r] = resId;
        }
    }

    public void onBtnCard(View view) {
        Log.d("MainActivity", "Btn ID=" + view.getId());
        //Toast.makeText(this, "Btn ID=" + view.getId(), Toast.LENGTH_SHORT).show();

        int previousResourceId = 0;
        if (previousCardButton != null) {
            previousCardButton.setImageResource(R.mipmap.card_blue_back);
            previousResourceId = (Integer) previousCardButton.getTag();
        }

        ImageButton btn = (ImageButton) view;
        int resId = (Integer) btn.getTag();
        btn.setImageResource(resId);

        flips += 1;
        String text = String.format("Flips: %d", flips);
        ui.scoreTextView.setText(text);

        if (previousResourceId == resId) {
            previousCardButton.setVisibility(View.INVISIBLE);
            btn.setVisibility(View.INVISIBLE);
            previousCardButton = null;
        } else {
            previousCardButton = btn;
        }
    }

    public void onBtnRestart(View view) {
        startGame();
    }
}