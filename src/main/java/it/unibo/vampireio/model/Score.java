package it.unibo.vampireio.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Score{
    public static final String FILE_NAME = System.getProperty("user.home") + File.separator + "scores"; // da cambiare
    private String characterName;
    private double sessionTime;
    private int killCounter;
    private int lvlCounter;

    public Score(String characterName) {
        this.characterName = characterName;
        this.sessionTime = 0;
        this.killCounter = 0;
        this.lvlCounter = 0;
    }

    public int incrementKillCounter() {
        this.killCounter += 1;
        return this.killCounter;
    }

    public int incrementLvlCounter() {
        this.lvlCounter += 1;
        return this.lvlCounter;
    }

    public double incrementSessionTime() {
        this.sessionTime += 1;
        return this.sessionTime;
    }

    public int getKillCounter() {
        return this.killCounter;
    }

    public int getLvlCounter() {
        return this.lvlCounter;
    }

    public double getSessionTime() {
        return this.sessionTime;
    }

    public int getScore() {
        return getKillCounter()*getLvlCounter();
    }

    public static void saveScore(List<Score> scores) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(scores);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static List<Score> loadScores() {
        List<Score> scores = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            scores = (List<Score>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public static List<Score> sortScores(List<Score> scores) {
        scores.sort((score1, score2) -> Integer.compare(score2.getScore(), score1.getScore()));
        return scores;
    }

}
