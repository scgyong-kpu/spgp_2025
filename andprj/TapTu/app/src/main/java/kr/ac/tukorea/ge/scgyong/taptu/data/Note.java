package kr.ac.tukorea.ge.scgyong.taptu.data;

public class Note {
    public int pret;
    public float time; // in seconds
    public static Note parse(String line) {
        String[] comps = line.split("\\s+");
        if (comps.length < 3) return null;
        if (!comps[0].equals("N")) return null;

        Note note = new Note();
        note.pret = Integer.parseInt(comps[1]);
        note.time = Integer.parseInt(comps[2]) / 1000.0f;
        return note;
    }
}
