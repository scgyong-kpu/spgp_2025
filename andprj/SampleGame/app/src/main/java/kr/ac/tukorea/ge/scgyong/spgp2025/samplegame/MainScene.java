package kr.ac.tukorea.ge.scgyong.spgp2025.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class MainScene {
    private final ArrayList<IGameObject> gameObjects = new ArrayList<>();
    private Fighter fighter;

    public MainScene(GameView gameView) {
        Resources res = gameView.getResources();
        Bitmap ballBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
        Ball.setBitmap(ballBitmap);

        Bitmap fighterBitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
        fighter = new Fighter(fighterBitmap);

        for (int i = 0; i < 5; i++) {
            gameObjects.add(new BouncingCircle());
        }
        for (int i = 0; i < 10; i++) {
            gameObjects.add(Ball.random());
        }
        gameObjects.add(fighter);

    }

    public void update() {
        for (IGameObject gobj : gameObjects) {
            gobj.update();
        }
    }
    public void draw(Canvas canvas) {
        for (IGameObject gobj : gameObjects) {
            gobj.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float[] xy = Metrics.fromScreen(event.getX(), event.getY());
                fighter.setTargetPosition(xy[0], xy[1]);
                return true;
        }
        return false;
    }
}
