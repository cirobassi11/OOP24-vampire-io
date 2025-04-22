package it.unibo.vampireio.view;

import java.util.List;
import java.awt.event.ActionListener;
import it.unibo.vampireio.controller.GameData;
import it.unibo.vampireio.controller.ScoreData;
import it.unibo.vampireio.controller.UnlockableCharacterData;

public interface GameView {
    void update(GameData data);
    void updateSaveList(List<String> saves);
    void showScreen(String name);
    
    /* Button action listeners */
    void setConfirmCharacterListener(ActionListener listener);
    void setNewSaveListener(ActionListener listener);
    void setShowSaveListener(ActionListener listener);
    void setChooseSaveListener(ActionListener listener);
    void setCharactersShopListener(ActionListener listener);
    void setPowerUpsShopListener(ActionListener listener);
    void setBackListener(ActionListener listener);
    void setStartListener(ActionListener listener);
    void setScoreboardListener(ActionListener listener);
    void setShopListener(ActionListener listener);
    void setLoadSaveListener(ActionListener listener);
    void setQuitListener(ActionListener listener);
    void setBuyCharactersListener(ActionListener listener);
    void setBuyPowerUpsListener(ActionListener listener);
    void setReturnMenuListener(ActionListener listener);
    void setContinueListener(ActionListener listener);
    void setExitListener(ActionListener listener);

    //Setta i dati dei punteggi (nella scoreboard)
    void setScoreList(List<ScoreData> scores);
    
    //Choose character panel
    void setChoosableCharactersData(List<UnlockableCharacterData> choosableCharactersData);
    String getChoosedCharacter();

    //Unlockable character panel
    void setUnlockableCharactersData(List<UnlockableCharacterData> unlockableCharactersData);
    String getSelectedCharacter();
    
    String getSelectedSave();

    void showError(String message);
}
