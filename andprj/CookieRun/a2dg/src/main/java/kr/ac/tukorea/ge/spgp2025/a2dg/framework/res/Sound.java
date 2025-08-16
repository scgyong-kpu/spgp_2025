package kr.ac.tukorea.ge.spgp2025.a2dg.framework.res;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
// ✅ 안드로이드 퍼블릭 API임 → 누구나 앱에서 사용 가능

import java.util.HashMap;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;


// 게임에서 배경 음악(BGM)과 효과음(SFX)을 재생하고 관리하는 기능
public class Sound {
    protected static MediaPlayer mediaPlayer;
    protected static SoundPool soundPool;
    // MediaPlayer는 배경음악용, SoundPool은 짧은 효과음용으로
    // 사용하는 Android의 일반적인 방식


    // 기존 음악이 있으면 중지 후 새로 생성
    // 반복 재생 설정 후 시작
    public static void playMusic(int resId) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(GameView.view.getContext(), resId);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    // 음악 정지 및 mediaPlayer 해제
    public static void stopMusic() {
        if (mediaPlayer == null) return;
        mediaPlayer.stop();
        mediaPlayer = null;
    }
    public static void pauseMusic() {
        if (mediaPlayer == null) return;
        mediaPlayer.pause();
    }
    public static void resumeMusic() {
        if (mediaPlayer == null) return;
        mediaPlayer.start();
    }

    private static final HashMap<Integer, Integer> soundIdMap = new HashMap<>();
    // soundIdMap: resId(리소스 ID)와 실제 SoundPool에 로드된 soundId를 매핑


    // 이미 로드된 효과음인지 확인하고, 없다면 로드하고 저장
    //
    //play(...)로 실제 재생
    //볼륨 1.0 (좌우 동일)
    //우선순위 1
    //반복 없음 (0)
    //속도 1.0배
    public static void playEffect(int resId) {
        SoundPool pool = getSoundPool();
        int soundId;
        if (soundIdMap.containsKey(resId)) {
            soundId = soundIdMap.get(resId);
        } else {
            soundId = pool.load(GameView.view.getContext(), resId, 1);
            soundIdMap.put(resId, soundId);
        }
        // int streamId =
        pool.play(soundId, 1f, 1f, 1, 0, 1f);
    }

    // SoundPool이 없으면 새로 생성
    //AudioAttributes를 사용해 게임 용도로 세팅
    //동시에 최대 3개의 효과음을 재생 가능
    private static SoundPool getSoundPool() {
        if (soundPool != null) return soundPool;

        AudioAttributes attrs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attrs)
                .setMaxStreams(3)
                .build();
        return soundPool;
    }
}
