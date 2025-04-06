package it.unibo.vampireio.model;

import java.io.File;
import java.util.List;

public class SavingManager {
    private static final String FILE_NAME = System.getProperty("user.home") + File.separator + "vampire-io_savings.sav";

    public List<String> getSavingNames() {
        return List.of(); //TODO
    }

    public void loadNewSaving() {
        //TODO
    }

    public void loadSaving(String selectedSaving) {
        //TODO
    }

    public Saving getCurrentSaving() {
        return new Saving(); //TODO
    }
}