package it.unibo.vampireio.view;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

class AudioManager {
    private Clip backingTrack;
    private final String audioPath = "/audio/";

    public AudioManager() {
        try {
            InputStream backingTrackStream = getClass().getResourceAsStream(audioPath + "soundtrack.wav");
            AudioInputStream backingTrackAudio = AudioSystem.getAudioInputStream(backingTrackStream);
            backingTrack = AudioSystem.getClip();
            backingTrack.open(backingTrackAudio);

            FloatControl volume = (FloatControl) backingTrack.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-10.0f);

            backingTrack.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}