package kr.ac.tukorea.ge.scgyong.taptu.game;

import kr.ac.tukorea.ge.scgyong.taptu.R;
import kr.ac.tukorea.ge.scgyong.taptu.data.Note;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class NoteSprite extends Sprite implements IRecyclable {
    private static final float X_SPACE = 130f;
    private static final float LEFT = 450f - 2 * X_SPACE;
    private static final float WIDTH = 120f;
    private static final float HEIGHT = 55f;
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
        float y = note.msec / 10.0f;
        setPosition(x, y);
        return this;
    }

    protected Note note;

    @Override
    public void onRecycle() {}
}
