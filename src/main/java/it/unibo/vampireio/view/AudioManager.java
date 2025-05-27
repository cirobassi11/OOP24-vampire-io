package it.unibo.vampireio.view;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

class AudioManager {
    private Clip backingTrack;
    private final String audioPath = "/audio/";

    public AudioManager() {
        try {
            final InputStream backingTrackStream = getClass().getResourceAsStream(audioPath + "soundtrack.wav");
            final AudioInputStream backingTrackAudio = AudioSystem.getAudioInputStream(backingTrackStream);
            backingTrack = AudioSystem.getClip();
            backingTrack.open(backingTrackAudio);

            FloatControl volume = (FloatControl) backingTrack.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-25.0f);

            backingTrack.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
