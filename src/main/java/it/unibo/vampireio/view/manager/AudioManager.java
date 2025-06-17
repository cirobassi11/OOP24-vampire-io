package it.unibo.vampireio.view.manager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import it.unibo.vampireio.view.api.GameView;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * AudioManager is responsible for managing audio playback in the game.
 * It initializes and plays the background music when the game starts.
 */
public class AudioManager {
    private static final float DEFAULT_VOLUME_DB = -25.0f;
    private static final String AUDIO_PATH = "/audio/";

    /**
     * Constructs an AudioManager and initializes the background music.
     *
     * @param view the GameView instance to notify in case of errors
     */
    public AudioManager(final GameView view) {
        try {
            final InputStream backingTrackStream = AudioManager.class
                    .getResourceAsStream(AUDIO_PATH + "soundtrack.wav");
            final AudioInputStream backingTrackAudio = AudioSystem
                    .getAudioInputStream(new BufferedInputStream(backingTrackStream));
            final Clip backingTrack = AudioSystem.getClip();
            backingTrack.open(backingTrackAudio);

            final FloatControl volume = (FloatControl) backingTrack.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(DEFAULT_VOLUME_DB);

            backingTrack.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e) {
            view.notifyError("An error occurred while loading the audio.");
        }
    }
}
