package kr.ac.tukorea.ge.scgyong.taptu.data;

import androidx.annotation.NonNull;

public class Song {
    public int rank;
    public String title;
    public String artist;
    public String thumbnail;

    @NonNull
    @Override
    public String toString() {
        return "<" + rank + ">" + title + "/" + artist;
    }
}
