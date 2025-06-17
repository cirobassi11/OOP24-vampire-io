package it.unibo.vampireio.model.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.vampireio.model.impl.SaveImpl;
import it.unibo.vampireio.model.api.GameModel;
import it.unibo.vampireio.model.api.Save;
import it.unibo.vampireio.model.api.Score;
import it.unibo.vampireio.model.impl.UnlockableCharacter;
import it.unibo.vampireio.model.impl.UnlockablePowerUp;
import it.unibo.vampireio.model.data.ConfigData;
import it.unibo.vampireio.model.data.DataLoader;

/**
 * Manages the saving and loading of game states.
 * Handles the creation, reading, and writing of save files and an index file
 * that keeps track of available saves.
 */
public final class SaveManager {
    private static final String SAVING_ERROR = "An error occurred while saving";
    private static final String READING_ERROR = "An error occurred while reading";
    private final GameModel model;

    private final String folderName = System.getProperty("user.home") + File.separator + "vampire-io_save";
    private Save currentSave;

    /**
     * Constructs a SaveManager for the given GameModel model.
     *
     * @param model the GameModel model
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "The GameModel instance is intentionally shared and is used in a controlled way within SaveManager.")
    public SaveManager(final GameModel model) {
        this.model = model;
    }

    /**
     * Returns a list of save names from the save directory.
     * The save directory is located in the user's home directory under
     * "vampire-io_save".
     * If an error occurs while reading the directory, an error is notified to the
     * model.
     *
     * @return a list of strings representing the names of available saves
     */
    public List<String> getSavesNames() {
        final List<File> files = new ArrayList<>(List.of(new File(folderName).listFiles()));
        if (!files.isEmpty()) {
            return files.stream()
                    .filter(File::exists)
                    .filter(File::isFile)
                    .filter(File::canRead)
                    .map(File::getName)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    /**
     * Creates a new save with the default character defined in the config data.
     * If the default character cannot be found, an error is notified to the model.
     * The new save is saved immediately after creation.
     */
    public void createNewSave() {
        final ConfigData configData = DataLoader.getInstance().getConfigLoader().get(ConfigData.CONFIG_ID).orElse(null);
        if (configData != null) {
            final UnlockableCharacter defaultCharacter = DataLoader.getInstance().getCharacterLoader()
                .get(configData.getDefaultCharacterId()).orElse(null);
            if (defaultCharacter == null) {
                this.model.notifyError(READING_ERROR);
            }
            this.currentSave = new SaveImpl();
            this.currentSave.addUnlockedCharacter(defaultCharacter);
            this.saveCurrentSave();
        } else {
            this.model.notifyError(READING_ERROR);
        }
    }

    /**
     * Loads a save from the specified file.
     * The save file is identified by the selected save name.
     * If the save file cannot be read or does not exist, an error is notified to
     * the model.
     *
     * @param selectedSave the name of the save to load
     */
    public void loadSave(final String selectedSave) {
        final String saveFilePath = this.getFilePath(selectedSave);
        if (saveFilePath != null) {
            try (FileInputStream input = new FileInputStream(saveFilePath);
                    ObjectInputStream in = new ObjectInputStream(input)) {
                this.currentSave = (Save) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                this.model.notifyError(READING_ERROR);
            }
        } else {
            this.model.notifyError(READING_ERROR);
        }
    }

    /**
     * Saves the current game state to a file.
     * The save file is named with the current save time and stored in the user's
     * home directory.
     * If an error occurs during saving, an error is notified to the model.
     */
    public void saveCurrentSave() {
        final String saveFilePath = this.getFilePath(this.currentSave.getSaveTime());
        if (saveFilePath != null) {
            try (FileOutputStream output = new FileOutputStream(saveFilePath);
                    ObjectOutputStream out = new ObjectOutputStream(output)) {
                out.writeObject(currentSave);
            } catch (final IOException e) {
                this.model.notifyError(SAVING_ERROR);
            }
        }
    }

    /**
     * Constructs the file path for the save file based on the provided save time.
     * The save file is stored in the user's home directory under "vampire-io_save".
     * If the directory does not exist, it attempts to create it.
     *
     * @param saveTime the name of the save file, a timestamp
     * @return the full path to the save file, or null if an error occurs
     */
    private String getFilePath(final String saveTime) {
    final File saveDirectory = new File(folderName);
        if (!saveDirectory.exists() && !saveDirectory.mkdirs()) {
            this.model.notifyError(SAVING_ERROR);
            return null;
        }
        return saveDirectory.getPath() + File.separator + saveTime;
    }

    /**
     * Returns a list of scores from the current save.
     * If no save is loaded or if the scores are null, an error is notified to the
     * model and an empty list is returned.
     *
     * @return a sorted list of Score objects from the current save
     */
    public List<Score> getScores() {
        if (this.currentSave == null || this.currentSave.getScores() == null) {
            this.model.notifyError(READING_ERROR);
            return List.of();
        }
        return sortScores(this.currentSave.getScores());
    }

    /**
     * Increments the money amount in the current save by the specified amount.
     *
     * @param moneyAmount the amount to add to the current money amount
     */
    public void incrementMoneyAmount(final int moneyAmount) {
        if (this.currentSave == null) {
            this.model.notifyError(READING_ERROR);
            return;
        }
        this.currentSave.incrementMoneyAmount(moneyAmount);
        this.saveCurrentSave();
    }

    /**
     * Adds a new score to the current save.
     *
     * @param score the Score object to be added
     */
    public void addScore(final Score score) {
        if (this.currentSave == null) {
            this.model.notifyError(READING_ERROR);
            return;
        }
        this.currentSave.addScore(score);
        this.saveCurrentSave();
    }

    /**
     * Returns a map of unlocked power-ups in the current save.
     * The keys are power-up IDs and the values are their enhancement levels.
     *
     * @return a map where keys are power-up IDs and values are their enhancement
     *         levels
     */
    public Map<String, Integer> getUnlockedPowerUps() {
        if (this.currentSave == null) {
            this.model.notifyError(READING_ERROR);
            return Map.of();
        }
        return this.currentSave.getUnlockedPowerUps();
    }

    /**
     * Returns the amount of money in the current save.
     *
     * @return the amount of money as an integer
     */
    public int getMoneyAmount() {
        if (this.currentSave == null) {
            this.model.notifyError(READING_ERROR);
            return 0;
        }
        return this.currentSave.getMoneyAmount();
    }

    /**
     * Enhances a power-up in the current save.
     *
     * @param powerUp the UnlockablePowerUp object representing the power-up to be
     *                enhanced
     */
    public void enhancePowerUp(final UnlockablePowerUp powerUp) {
        if (this.currentSave == null) {
            this.model.notifyError(READING_ERROR);
            return;
        }
        this.currentSave.enhancePowerUp(powerUp);
        this.saveCurrentSave();
    }

    /**
     * Adds a new unlocked character to the current save.
     *
     * @param character the UnlockableCharacter object representing the character to
     *                  be added
     */
    public void addUnlockedCharacter(final UnlockableCharacter character) {
        if (this.currentSave == null) {
            this.model.notifyError(READING_ERROR);
            return;
        }
        this.currentSave.addUnlockedCharacter(character);
        this.saveCurrentSave();
    }

    /**
     * Returns a list of unlocked characters in the current save.
     * If no save is loaded, an error is notified to the model and an empty list is
     * returned.
     *
     * @return a list of strings representing the IDs of unlocked characters
     */
    public List<String> getUnlockedCharacters() {
        if (this.currentSave == null) {
            this.model.notifyError(READING_ERROR);
            return List.of();
        }
        return this.currentSave.getUnlockedCharacters();
    }

    /**
     * Sorts a list of Score objects in descending order based on their score value.
     *
     * @param scoreList the list of Score objects to be sorted
     * @return a new list of Score objects sorted in descending order
     */
    private static List<Score> sortScores(final List<Score> scoreList) {
        final List<Score> sortedScores = new ArrayList<>(scoreList);
        sortedScores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));
        return sortedScores;
    }

}
