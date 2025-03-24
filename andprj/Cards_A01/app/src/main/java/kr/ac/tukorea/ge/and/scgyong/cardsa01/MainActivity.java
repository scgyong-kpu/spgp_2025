package kr.ac.tukorea.ge.and.scgyong.cardsa01;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import kr.ac.tukorea.ge.and.scgyong.cardsa01.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ImageButton previousCardButton;
    private @NonNull ActivityMainBinding ui;

    private int[] cardResIds = {
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd,
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd,
    };
    private ImageButton[] cardButtons;
    private int flips;
    private int openCardCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        cardButtons = new ImageButton[]{
                ui.card00, ui.card01, ui.card02, ui.card03,
                ui.card10, ui.card11, ui.card12, ui.card13,
                ui.card20, ui.card21, ui.card22, ui.card23,
                ui.card30, ui.card31, ui.card32, ui.card33,
        };

        startGame();
    }

    public void setFlips(int flips) {
        Resources res = getResources();
        this.flips = flips;
        String text = res.getString(R.string.score_fmt, this.flips);
        ui.scoreTextView.setText(text);
    }

    private void startGame() {
        //shuffleCards();

        for (int i = 0; i < cardResIds.length; i++) {
            ImageButton btn = cardButtons[i];
            btn.setImageResource(R.mipmap.card_blue_back);
            btn.setVisibility(View.VISIBLE);
            int resId = cardResIds[i];
            Integer resourceIdInteger = resId;
            btn.setTag(resourceIdInteger);
        }

        previousCardButton = null;
        setFlips(0);
        openCardCount = cardResIds.length;
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
        Log.d("MainActivity", "Button Clicked: ID=" + view.getId());
        //Toast.makeText(this, "BTN ID=" + view.getId(), Toast.LENGTH_SHORT).show();

        ImageButton btn = (ImageButton) view;
        if (btn == previousCardButton) {
            Toast.makeText(this, R.string.toast_same_card, Toast.LENGTH_SHORT).show();
            return;
        }

        int previousCardResourceId = 0;
        if (previousCardButton != null) {
            previousCardButton.setImageResource(R.mipmap.card_blue_back);
            previousCardResourceId = (Integer)previousCardButton.getTag();
        }

        int resId = (Integer) btn.getTag();

        if (resId == previousCardResourceId) {
            btn.setVisibility(View.INVISIBLE);
            previousCardButton.setVisibility(View.INVISIBLE);
            previousCardButton = null;
            openCardCount -= 2;
            if (openCardCount == 0) {
                //onBtnRestart(null);
                askRestart();
            }
        } else {
            btn.setImageResource(resId);
            previousCardButton = btn;

            setFlips(flips + 1);
        }
    }

    public void onBtnRestart(View view) {
        askRestart();
    }

    private void askRestart() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.restart_dlg_title)
                .setMessage(R.string.restart_dlg_message)
                .setPositiveButton(R.string.restart_dlg_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame();
                    }
                })
                .setNegativeButton(R.string.restart_dlg_no, null)
                .create()
                .show();
    }
}