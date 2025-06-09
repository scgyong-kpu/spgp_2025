package kr.ac.tukorea.ge.scgyong.taptu.game;

import android.util.Log;

import kr.ac.tukorea.ge.scgyong.taptu.R;
import kr.ac.tukorea.ge.scgyong.taptu.data.Note;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class NoteSprite extends Sprite implements IRecyclable {
    private static final float X_SPACE = 130f;
    private static final float LEFT = 450f - 2 * X_SPACE;
    private static final float WIDTH = 120f;
    private static final float HEIGHT = 55f;
    public static final float SPEED = 200f;
    public static final float GOAL_Y = 1400f;
    protected Note note;
    public NoteSprite() {
        super(R.mipmap.note_1);
        setPosition(0, 0, WIDTH, HEIGHT);
    }

    public static NoteSprite get(Note note) {
        return Scene.top().getRecyclable(NoteSprite.class).init(note);
    }

    private NoteSprite init(Note note) {
        this.note = note;
        float x = LEFT + note.pret * X_SPACE;
        float y = -note.msec;
        setPosition(x, y);
        //Log.d("NoteSprite", "init(), msec=" + note.msec + " x=" + x + " y=" + y);
        return this;
    }

    public boolean logs;

    @Override
    public void update() {
        float musicTime = MainScene.scene.getMusicTime();
        float timeDiff = note.msec / 1000.0f - musicTime;
        float y = GOAL_Y - timeDiff * SPEED;
        setPosition(x, y);
        if (logs)
        Log.d("NoteSprite", "x=" + x + " y=" + y + " t=" + musicTime + " scene=" + MainScene.scene);
    }

    @Override
    public void onRecycle() {}
}
