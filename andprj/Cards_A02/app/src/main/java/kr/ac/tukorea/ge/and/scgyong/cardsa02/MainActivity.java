package kr.ac.tukorea.ge.and.scgyong.cardsa02;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.and.scgyong.cardsa02.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private @NonNull ActivityMainBinding ui;
    private ImageButton previousCardButton;
    private ImageButton[] cardImageButtons;

    private static final int[] RES_IDS = new int[] {
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd,
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd,
    };

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
        for (int i = 0; i < RES_IDS.length; i++) {
            Integer resId = RES_IDS[i];
            cardImageButtons[i].setTag(resId);
        }
    }

    public void onBtnCard(View view) {
        Log.d("MainActivity", "Btn ID=" + view.getId());
        //Toast.makeText(this, "Btn ID=" + view.getId(), Toast.LENGTH_SHORT).show();

        if (previousCardButton != null) {
            previousCardButton.setImageResource(R.mipmap.card_blue_back);
        }

        ImageButton btn = (ImageButton) view;
        int resId = (Integer) btn.getTag();
        btn.setImageResource(resId);

        previousCardButton = btn;
    }
}