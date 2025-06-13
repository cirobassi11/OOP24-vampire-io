package it.unibo.vampireio.model.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.vampireio.model.impl.SaveImpl;
import it.unibo.vampireio.model.api.Save;
import it.unibo.vampireio.model.impl.GameWorld;
import it.unibo.vampireio.model.impl.UnlockableCharacter;
import it.unibo.vampireio.model.data.ConfigData;
import it.unibo.vampireio.model.data.DataLoader;

/**
 * Manages the saving and loading of game states.
 * Handles the creation, reading, and writing of save files and an index file
 * that keeps track of available saves.
 */
public final class SaveManager {
    private static final String SAVING_ERROR = "An error occurred while saving the file";
    private static final String READING_ERROR = "An error occurred while reading the file";
    private final GameWorld model;

    private final String indexFileName = System.getProperty("user.home") + File.separator
            + "vampire-io_saves_index.sav";
    private Save currentSave;
    private List<String> savesNames;

    /**
     * Constructs a SaveManager for the given GameWorld model.
     * Initializes the save index file and reads existing saves if available.
     *
     * @param model the GameWorld model
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "The GameWorld instance is intentionally shared and is used in a controlled way within SaveManager."
    )
    public SaveManager(final GameWorld model) {
        this.model = model;
        final File indexFile = new File(indexFileName);
        if (!indexFile.exists()) {
            try {
                if (!indexFile.createNewFile()) {
                    this.model.notifyError("Failed to create index file.");
                    return;
                }
                this.savesNames = new ArrayList<>();
            } catch (final IOException e) {
                this.model.notifyError(SAVING_ERROR);
            }
        } else {
            this.readIndex();
        }
    }

    private void readIndex() {
        final File indexFile = new File(this.indexFileName);
        this.savesNames = new ArrayList<>();

        if (indexFile.length() == 0) {
            return;
        }

        try (FileInputStream input = new FileInputStream(indexFile);
                ObjectInputStream in = new ObjectInputStream(input)) {

            final Object obj = in.readObject();
            if (obj instanceof List<?>) {
                for (final Object item : (List<?>) obj) {
                    if (item instanceof String) {
                        this.savesNames.add((String) item);
                    } else {
                        this.model.notifyError(READING_ERROR);
                    }
                }
            } else {
                this.model.notifyError(READING_ERROR);
            }

        } catch (IOException | ClassNotFoundException e) {
            this.model.notifyError(READING_ERROR);
        }
    }

    private void saveIndex() {
        try (FileOutputStream fileOut = new FileOutputStream(indexFileName);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(savesNames);
        } catch (final IOException e) {
            this.model.notifyError(SAVING_ERROR);
        }
    }

    /**
     * Returns the names of all available saves.
     * This method provides a read-only view of the saves names list.
     *
     * @return a list of save names
     */
    public List<String> getSavesNames() {
        return List.copyOf(this.savesNames);
    }

    /**
     * Creates a new save with the default character defined in the config data.
     * If the default character is not found, an error is notified to the model.
     */
    public void createNewSave() {
        final ConfigData configData = DataLoader.getInstance().getConfigLoader().get(ConfigData.CONFIG_ID).orElse(null);
        final UnlockableCharacter defaultCharacter = DataLoader.getInstance().getCharacterLoader()
                .get(configData.getDefaultCharacterId()).orElse(null);
        if (defaultCharacter == null) {
            this.model.notifyError("Default character not found in config data!");
        }
        this.createNewSave(defaultCharacter);
    }

    private void createNewSave(final UnlockableCharacter defaultCharacter) {
        this.currentSave = new SaveImpl();
        this.savesNames.add(this.currentSave.getSaveTime());
        this.currentSave.addUnlockedCharacter(defaultCharacter);
        this.saveCurrentSave();
        this.saveIndex();
    }

    /**
     * Loads a save by its name.
     * If the save file does not exist or cannot be read, an error is notified to
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
     * Returns the current save.
     *
     * @return the current Save object
     */
    public Save getCurrentSave() {
        return this.currentSave;
    }

    /**
     * Returns the file path for a save file based on the save time.
     * The save files are stored in a directory named "vampire-io_save" in the
     * user's
     * home directory.
     *
     * @param saveTime the timestamp of the save
     * @return the file path for the save file
     */
    private String getFilePath(final String saveTime) {
        final File saveDirectory = new File(System.getProperty("user.home") + File.separator + "vampire-io_save");
        if (!saveDirectory.exists() && !saveDirectory.mkdirs()) {
            this.model.notifyError("Failed to create save directory.");
            return null;
        }
        return saveDirectory.getPath() + File.separator + saveTime + ".sav";
    }
}
