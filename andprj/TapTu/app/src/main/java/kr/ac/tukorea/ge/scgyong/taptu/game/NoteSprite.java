package kr.ac.tukorea.ge.scgyong.taptu.game;

import kr.ac.tukorea.ge.scgyong.taptu.R;
import kr.ac.tukorea.ge.scgyong.taptu.data.Note;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class NoteSprite extends Sprite implements IRecyclable {
    public NoteSprite() {
        super(R.mipmap.note_1);
    }

    public static NoteSprite get(Note note) {
        return Scene.top().getRecyclable(NoteSprite.class).init(note);
    }

    private NoteSprite init(Note note) {
        this.note = note;
        return this;
    }

    protected Note note;

    @Override
    public void onRecycle() {}
}
