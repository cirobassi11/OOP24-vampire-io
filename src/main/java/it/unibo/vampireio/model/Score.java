package it.unibo.vampireio.model;

public class Score{
    /* 
     * personaggio utilizzato, duratapartita, kill, livello raggiunto, punteggio (kill*livello raggiunto??)
     * statistiche sortate in base al punteggio
    */
    private String character; // trovare il metodo per passarglielo --> estende classe character?
    private double sessionTime; // trovare il metodo per passarglielo --> estende classe character?
    private int killCounter;
    private int lvlCounter;

    private Score(){
        this.character="";
        this.sessionTime=0;
        this.killCounter=0;
        this.lvlCounter=0;
    }

    public int incrementKillCounter(){
        this.killCounter += 1;
        return this.killCounter;
    }

    public int incrementLvlCounter(){
        this.lvlCounter += 1;
        return this.lvlCounter;
    }

    public int getKillCounter(){
        return this.killCounter;
    }

    public int getLvlCounter(){
        return this.lvlCounter;
    }

    public int getScore(){
        return getKillCounter()*getLvlCounter();
    }
}
