package it.unibo.vampireio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Saving implements Serializable{
    private static final long serialVersionUID = 567742502623265945L;
    public static final String FILE_NAME = System.getProperty("user.home") + File.separator + "progress"; // da cambiare
    private String accountId;
    private List<Character> characterList = new ArrayList<>();
    private List<Weapon> weaponsList = new ArrayList<>();
    private List<UnlockablePowerUp> unlockedPowerUpsList = new ArrayList<>();
    private int moneyAmount;
    private List<Score> playerScores;
    
    public void load(){
        try(
            final ObjectInputStream progress = new ObjectInputStream(new FileInputStream(FILE_NAME));
        ){
            Saving sav = (Saving)progress.readObject();
            this.accountId = sav.accountId;
            this.characterList = sav.characterList;
            this.weaponsList = sav.weaponsList;
            this.unlockedPowerUpsList = sav.unlockedPowerUpsList;
            this.moneyAmount = sav.moneyAmount;
            this.playerScores = sav.playerScores;
        }
        catch(IOException | ClassNotFoundException ex){
            //
        }
    }

    public void save(){
        try(
            final ObjectOutputStream progress = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
        ){
            progress.writeObject(this);
        }
        catch(IOException ex){
            //
        }
    }
}
