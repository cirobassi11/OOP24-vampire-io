package it.unibo.vampireio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Saving implements Serializable{
    /* Salvataggio dei progressi
     * Lista dei personaggi sbloccati
     * Lista delle armi sbloccate
     * Lista dei nomi del giocatore
     * Lista dei powerup sbloccati
     * Quantità monete possedute
    */
    private String accountId;
    private List<Character> characterList = new ArrayList<>();
    private List<Weapon> weaponsList = new ArrayList<>();
    private List<UnlockablePowerUp> unlockedPowerUpsList = new ArrayList<>();
    private int moneyAmount;
    private List<Score> playerScores;
}
