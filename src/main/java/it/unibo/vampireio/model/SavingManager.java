package it.unibo.vampireio.model;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SavingManager {
    private final String INDEX_FILE_NAME = System.getProperty("user.home") + File.separator + "vampire-io_savings_index.sav";
    private Saving currentSaving;
    private Map<String, String> indexMap = new HashMap<>();

    public SavingManager() {
        File indexFile = new File(INDEX_FILE_NAME); // nome salvataggio e percorso file salvataggio
        if (!indexFile.exists()) {
            try {
                indexFile.createNewFile();
                this.indexMap = new HashMap<>();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.readIndex();
        }
    }

    private void readIndex() {
        try (FileInputStream input = new FileInputStream(this.INDEX_FILE_NAME);
             ObjectInputStream in = new ObjectInputStream(input)) {
            this.indexMap = (Map<String, String>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveIndex() {
        try (FileOutputStream fileOut = new FileOutputStream(INDEX_FILE_NAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(indexMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getSavingNames() {
        return new LinkedList<>(this.indexMap.keySet());
    }

    public void createNewSaving() {
        this.currentSaving = new Saving();

        // Crea la cartella se non esiste
        File savingDirectory = new File(System.getProperty("user.home") + File.separator + "vampire-io_savings");
        if (!savingDirectory.exists()) {
            savingDirectory.mkdirs();
        }

        String savingFilePath = savingDirectory + File.separator + currentSaving.getSavingTime() + ".sav";
        this.indexMap.put(currentSaving.getSavingTime(), savingFilePath);
        this.saveCurrentSaving();
        this.saveIndex();
    }

    public void loadSaving(String selectedSaving) {
        String savingFilePath = indexMap.get(selectedSaving);
        if (savingFilePath != null) {
            try (FileInputStream input = new FileInputStream(savingFilePath);
                 ObjectInputStream in = new ObjectInputStream(input)) {
                this.currentSaving = (Saving) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Errore nel caricare il salvataggio", e);
            }
        } else {
            throw new RuntimeException("Salvataggio non trovato: " + selectedSaving);
        }
    }

    public void saveCurrentSaving() {
        String savingFilePath = indexMap.get(currentSaving.getSavingTime());
        if (savingFilePath != null) {
            try (FileOutputStream output = new FileOutputStream(savingFilePath);
                 ObjectOutputStream out = new ObjectOutputStream(output)) {
                out.writeObject(currentSaving);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Saving getCurrentSaving() {
        return this.currentSaving;
    }
}
