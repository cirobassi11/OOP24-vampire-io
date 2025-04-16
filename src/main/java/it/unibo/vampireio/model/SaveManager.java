package it.unibo.vampireio.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveManager {
    private final String INDEX_FILE_NAME = System.getProperty("user.home") + File.separator + "vampire-io_saves_index.sav";
    private Save currentSave;
    private List<String> savesNames; 

    public SaveManager() {
        File indexFile = new File(INDEX_FILE_NAME); // nome salvataggio e percorso file salvataggio
        if (!indexFile.exists()) {
            try {
                indexFile.createNewFile();
                this.savesNames = new ArrayList<>();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.readIndex();
        }
    }

    private void readIndex() {
        File indexFile = new File(this.INDEX_FILE_NAME);
        this.savesNames = new ArrayList<>();

        if (indexFile.length() == 0) {
            return;
        }

        try (FileInputStream input = new FileInputStream(indexFile);
            ObjectInputStream in = new ObjectInputStream(input)) {

            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                for (Object item : (List<?>) obj) {
                    if (item instanceof String) {
                        this.savesNames.add((String) item);
                    } else {
                        //STAMPA ERRORE ("errore nella lettura del file")
                    }
                }
            } else {
                //STAMPA ERRORE ("errore nella lettura del file")
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveIndex() {
        try (FileOutputStream fileOut = new FileOutputStream(INDEX_FILE_NAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(savesNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getSavesNames() {
        return List.copyOf(this.savesNames);
    }

    public void createNewSave() {
        this.currentSave = new Save();
        this.savesNames.add(this.currentSave.getSaveTime());
        this.saveCurrentSave();
        this.saveIndex();
    }

    public void loadSave(String selectedSave) {
        String saveFilePath = this.getFilePath(selectedSave);
        if (saveFilePath != null) {
            try (FileInputStream input = new FileInputStream(saveFilePath);
                 ObjectInputStream in = new ObjectInputStream(input)) {
                this.currentSave = (Save) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Errore nel caricare il salvataggio", e);
            }
        } else {
            throw new RuntimeException("Salvataggio non trovato: " + selectedSave);
        }
    }

    public void saveCurrentSave() {
        String saveFilePath = this.getFilePath(this.currentSave.getSaveTime());
        if (saveFilePath != null) {
            try (FileOutputStream output = new FileOutputStream(saveFilePath);
                 ObjectOutputStream out = new ObjectOutputStream(output)) {
                out.writeObject(currentSave);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Save getCurrentSave() {
        return this.currentSave;
    }

    private String getFilePath(String saveTime){
        File saveDirectory = new File(System.getProperty("user.home") + File.separator + "vampire-io_save");
        if (!saveDirectory.exists()) {
            saveDirectory.mkdirs();
        }
        return saveDirectory.getPath() + File.separator + saveTime + ".sav";
    }
}