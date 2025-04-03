package it.unibo.vampireio.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import it.unibo.vampireio.model.Saving;

public class SavingManager {
    private static final String FILE_NAME = System.getProperty("user.home") + File.separator + "vampire-io_savings.sav";

    public List<Saving> readSavings() {
        List<Saving> savings = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            savings = (List<Saving>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("An error occured while reading the list of savings!");
            e.printStackTrace();
        }
        return savings;
    }

    public void writeSaving(Saving saving) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(saving);
        } catch (IOException ex) {
            System.err.println("An error occured while writing a saving!");
            ex.printStackTrace();
        }
    }

    public void removeSaving(String savingTime) {
        List<Saving> savings = readSavings();
        List<SavingData> savingsDatas = getSavingsData();
        int indexToRemove = 0;
        for (int i = 0; i < savings.size(); i++) {
            if (savingsDatas.get(i).getSavingTime() == savingTime) {
                indexToRemove = i;
            }
        }
        savings.remove(indexToRemove);
    }

    public List<SavingData> getSavingsData() {
        List<SavingData> savingsDatas = new ArrayList<>();
        return savingsDatas;
    }

}