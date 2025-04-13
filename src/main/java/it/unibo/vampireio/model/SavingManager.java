package it.unibo.vampireio.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SavingManager {
    private final String INDEX_FILE_NAME = System.getProperty("user.home") + File.separator + "vampire-io_savings_index.sav";
    private Saving currentSaving;
    private List<String> savingsNames; 

    public SavingManager() {
        File indexFile = new File(INDEX_FILE_NAME); // nome salvataggio e percorso file salvataggio
        if (!indexFile.exists()) {
            try {
                indexFile.createNewFile();
                this.savingsNames = new ArrayList<>();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.readIndex();
        }
    }

    private void readIndex() {
        File indexFile = new File(this.INDEX_FILE_NAME);
        this.savingsNames = new ArrayList<>();

        if (indexFile.length() == 0) {
            return;
        }

        try (FileInputStream input = new FileInputStream(indexFile);
            ObjectInputStream in = new ObjectInputStream(input)) {

            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                for (Object item : (List<?>) obj) {
                    if (item instanceof String) {
                        this.savingsNames.add((String) item);
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
            out.writeObject(savingsNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getSavingsNames() {
        return List.copyOf(this.savingsNames);
    }

    public void createNewSaving() {
        this.currentSaving = new Saving();
        this.savingsNames.add(this.currentSaving.getSavingTime());
        this.saveCurrentSaving();
        this.saveIndex();
    }

    public void loadSaving(String selectedSaving) {
        String savingFilePath = this.getFilePath(selectedSaving);
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
        String savingFilePath = this.getFilePath(this.currentSaving.getSavingTime());
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

    private String getFilePath(String savingTime){
        File savingDirectory = new File(System.getProperty("user.home") + File.separator + "vampire-io_savings");
        if (!savingDirectory.exists()) {
            savingDirectory.mkdirs();
        }
        return savingDirectory.getPath() + File.separator + savingTime + ".sav";
    }
}
