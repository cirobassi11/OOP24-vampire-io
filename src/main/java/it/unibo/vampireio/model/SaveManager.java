package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.GameController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class SaveManager {

    private GameController gameController;

    private final String indexFileName = System.getProperty("user.home") + File.separator + "vampire-io_saves_index.sav";
    private Save currentSave;
    private List<String> savesNames;

    private final String savingError = "An error occurred while saving the file";
    private final String readingError = "An error occurred while reading the file";

    public SaveManager(final GameController gameController) {
        this.gameController = gameController;
        final File indexFile = new File(indexFileName); // nome salvataggio e percorso file salvataggio
        if (!indexFile.exists()) {
            try {
                indexFile.createNewFile();
                this.savesNames = new ArrayList<>();
            } catch (final IOException e) {
                this.gameController.showErrorWithExit(this.savingError);
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
                        this.gameController.showErrorWithExit(this.readingError);
                    }
                }
            } else {
                this.gameController.showErrorWithExit(this.readingError);
            }

        } catch (IOException | ClassNotFoundException e) {
            this.gameController.showErrorWithExit(this.readingError);
            e.printStackTrace();
        }
    }

    private void saveIndex() {
        try (FileOutputStream fileOut = new FileOutputStream(indexFileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(savesNames);
        } catch (final IOException e) {
            this.gameController.showErrorWithExit(this.savingError);
        }
    }

    public List<String> getSavesNames() {
        return List.copyOf(this.savesNames);
    }

    public void createNewSave(final UnlockableCharacter defaultCharacter) {
        this.currentSave = new Save();
        this.savesNames.add(this.currentSave.getSaveTime());
        this.currentSave.addUnlockedCharacter(defaultCharacter);
        this.saveCurrentSave();
        this.saveIndex();
    }

    public void loadSave(final String selectedSave) {
        final String saveFilePath = this.getFilePath(selectedSave);
        if (saveFilePath != null) {
            try (FileInputStream input = new FileInputStream(saveFilePath);
                 ObjectInputStream in = new ObjectInputStream(input)) {
                this.currentSave = (Save) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                this.gameController.showErrorWithExit(this.readingError);
                e.printStackTrace();
            }
        } else {
            this.gameController.showErrorWithExit(this.readingError);
        }
    }

    public void saveCurrentSave() {
        final String saveFilePath = this.getFilePath(this.currentSave.getSaveTime());
        if (saveFilePath != null) {
            try (FileOutputStream output = new FileOutputStream(saveFilePath);
                 ObjectOutputStream out = new ObjectOutputStream(output)) {
                out.writeObject(currentSave);
            } catch (final IOException e) {
                this.gameController.showErrorWithExit(this.savingError);
            }
        }
    }

    public Save getCurrentSave() {
        return this.currentSave;
    }

    private String getFilePath(final String saveTime) {
        final File saveDirectory = new File(System.getProperty("user.home") + File.separator + "vampire-io_save");
        if (!saveDirectory.exists()) {
            saveDirectory.mkdirs();
        }
        return saveDirectory.getPath() + File.separator + saveTime + ".sav";
    }
}
