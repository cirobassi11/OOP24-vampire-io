package it.unibo.vampireio.view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;

class AudioManager {
    private static final float DEFAULT_VOLUME_DB = -25.0f;
    private final String audioPath = "/audio/";

    AudioManager(final GameViewImpl view) {
        try {
            final InputStream backingTrackStream = getClass().getResourceAsStream(audioPath + "soundtrack.wav");
            final AudioInputStream backingTrackAudio = AudioSystem.getAudioInputStream(backingTrackStream);
            Clip backingTrack = AudioSystem.getClip();
            backingTrack.open(backingTrackAudio);

            final FloatControl volume = (FloatControl) backingTrack.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(DEFAULT_VOLUME_DB);

            backingTrack.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e) {
            view.notifyError("An error occurred while loading the audio.");
        }
    }
}
