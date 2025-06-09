package kr.ac.tukorea.ge.scgyong.taptu.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Note {
    public int pret;
    public float time; // in seconds
    private static final Pattern pattern = Pattern.compile("^N\\s+(\\d+)\\s+(\\d+)\\s*$");
    public static Note parse(String line) {
        Matcher m = pattern.matcher(line);
        if (!m.find()) return null;
        String pret = m.group(1);
        String millis = m.group(2);
        if (pret == null || millis == null) return null; // 필요한 코드는 아니지만 null check analyzer 때문에 써준다

        Note note = new Note();
        note.pret = Integer.parseInt(pret);
        note.time = Integer.parseInt(millis) / 1000.0f;
        return note;
    }
}
