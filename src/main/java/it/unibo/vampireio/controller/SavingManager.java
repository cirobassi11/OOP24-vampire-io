package it.unibo.vampireio.controller;

import java.io.File;
import java.util.List;
import it.unibo.vampireio.model.Saving;

public class SavingManager {
    private static final String FILE_NAME = System.getProperty("user.home") + File.separator + "vampire-io_savings.sav";

    public List<Saving> readSavings() {
        //todo
        return null;
    }

    public void writeSaving(Saving saving) {
        //todo
    }

    public void removeSaving(String savingTime) {
        //todo
    }

    public List<SavingData> getSavingsData() {
        //todo
        return null;
    }

}