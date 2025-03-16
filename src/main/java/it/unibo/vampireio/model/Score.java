package it.unibo.vampireio.model;

public class Score{
    /* 
     * personaggio utilizzato, duratapartita, kill, livello raggiunto, punteggio (kill*livello raggiunto??)
     * statistiche sortate in base al punteggio
    */
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
}
