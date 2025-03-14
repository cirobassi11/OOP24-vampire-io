package it.unibo.vampireio.model;

import java.io.Serializable;

public class Saving implements Serializable{
    /* personaggio utilizzato, duratapartita, kill, livello raggiunto, punteggio (kill*livello raggiunto??)
    statistiche sortate in base al punteggio*/
    //private String character;
    private int killCounter;
    private int lvlCounter;
    //private double score;
    //private double time;

    private Saving(){
        //this.character="";
        this.killCounter=0;
        this.lvlCounter=0;
        //this.score=0;
    }

    public int incrementKillCounter(){
        this.killCounter += 1;
        return this.killCounter;
    }

    public int incrementlvlCounter(){
        this.lvlCounter += 1;
        return this.lvlCounter;
    } 
}
