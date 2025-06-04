package it.unibo.vampireio.view;

import java.util.List;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import it.unibo.vampireio.controller.GameData;
import it.unibo.vampireio.controller.InputHandler;
import it.unibo.vampireio.controller.ItemData;
import it.unibo.vampireio.controller.ScoreData;
import it.unibo.vampireio.controller.UnlockableItemData;

public interface GameView {

    String SAVE_MENU = "saveMenu";
    String SAVE_SELECTION = "saveSelection";
    String START = "mainMenu";
    String SCOREBOARD = "scoreboard";
    String CHOOSE_CHARACTER = "chooseCharacter";
    String GAME = "game";
    String ITEM_SELECTION = "itemSelection";
    String END_GAME = "endGame";
    String PAUSE = "pause";
    String SHOP = "shop";
    String UNLOCKABLE_CHARACTERS = "unlockableCharacters";
    String UNLOCKABLE_POWERUPS = "unlockablePowerups";

    void update(GameData data);
    void updateSaveList(List<String> saves);
    void showScreen(String name);
    void setPlayerInputListener(InputHandler listener);
    void setViewErrorListener(ViewErrorListener listener);
    void notifyError(String message);
    void showError(String errorMessage);

    /* Button action listeners */
    void setConfirmCharacterListener(ActionListener listener);

    void setNewSaveListener(ActionListener listener);

    void setShowSaveListener(ActionListener listener);

    void setChooseSaveListener(ActionListener listener);
    
    void setChooseItemListener(ActionListener listener);

    void setCharactersShopListener(ActionListener listener);

    void setPowerupsShopListener(ActionListener listener);

    void setListSelectionListener(ListSelectionListener listener);

    void setBackListener(ActionListener listener);

    void setStartListener(ActionListener listener);

    void setScoreboardListener(ActionListener listener);

    void setShopListener(ActionListener listener);

    void setLoadSaveListener(ActionListener listener);

    void setQuitListener(ActionListener listener);

    void setBuyCharactersListener(ActionListener listener);

    void setBuyPowerupsListener(ActionListener listener);

    void setReturnMenuListener(ActionListener listener);

    void setResumeListener(ActionListener listener);

    void setExitListener(ActionListener listener);

    // Setta i dati dei punteggi (nella scoreboard)
    void setScoresData(List<ScoreData> scores);

    // Choose character panel
    void setChoosableCharactersData(List<UnlockableItemData> choosableCharactersData);

    String getChoosedCharacter();

    // Shop panel
    void setCoinsAmount(int coins);

    // Unlockable character panel
    void setUnlockableCharactersData(List<UnlockableItemData> unlockableCharactersData);

    String getSelectedCharacter();

    // Unlockable powerup panel
    void setUnlockablePowerupsData(List<UnlockableItemData> unlockablePowerupsData);

    String getSelectedPowerup();

    // Save Selection Panel
    String getSelectedSave();

    // Item Selection Panel
    void setItemsData(List<ItemData> itemsData);

    String getSelectedItem();

    // Set game score
    void setCurrentScore(ScoreData score);

    void disableBuyButton();

	void enableBuyButton();
}
