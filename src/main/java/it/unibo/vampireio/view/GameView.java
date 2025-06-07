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
        String UNLOCKABLE_POWERUPS = "unlockablePowerUps";

        void update(GameData data);

        void updateSaveList(List<String> saves);

        void showScreen(String name);

        void setPlayerInputListener(InputHandler listener);

        void setViewErrorListener(ViewErrorListener listener);

        void notifyError(String message);

        void showError(String errorMessage);

        /* Listeners */

        public void setSaveMenuPanelListeners(
                        final ActionListener newSaveListener,
                        final ActionListener showSaveListener,
                        final ActionListener quitListener);

        public void setStartMenuPanelListeners(
                        final ActionListener startListener,
                        final ActionListener scoreboardListener,
                        final ActionListener shopListener,
                        final ActionListener loadSaveListener,
                        final ActionListener quitListener);

        public void setSaveSelectionPanelListener(
                        final ActionListener chooseSaveListener,
                        final ActionListener backListener);

        public void setChooseCharacterPanelListener(
                        final ListSelectionListener characterSelectionListener,
                        final ActionListener confirmCharacterListener,
                        final ActionListener backListener);

        public void setShopPanelListener(
                        final ActionListener characterShopListener,
                        final ActionListener powerUpsShopListener,
                        final ActionListener backListener);

        public void setScoreboardPanelListener(final ActionListener backListener);

        public void setEndGamePanelListener(final ActionListener returnMenuListener);

        public void setPausePanelListener(
                        final ActionListener resumeListener,
                        final ActionListener exitListener);

        public void setItemSelectionPanelListener(final ActionListener chooseItemListener);

        public void setUnlockableItemShopPanelListener(
                        final ActionListener buyCharacterListener,
                        final ActionListener buyPowerUpListener,
                        final ListSelectionListener listSelectionListener,
                        final ActionListener backListener);

        void setScoresData(List<ScoreData> scores);

        void setChoosableCharactersData(List<UnlockableItemData> choosableCharactersData);

        String getChoosedCharacter();

        void setCoinsAmount(int coins);

        void setUnlockableCharactersData(List<UnlockableItemData> unlockableCharactersData);

        String getSelectedCharacter();

        void setUnlockablePowerUpsData(List<UnlockableItemData> unlockablePowerUpsData);

        String getSelectedPowerUp();

        String getSelectedSave();

        void setItemsData(List<ItemData> itemsData);

        String getSelectedItem();

        void setCurrentScore(ScoreData score);

        void disableBuyButton();

        void enableBuyButton();

        void disableConfirmCharacterButton();

        void enableConfirmCharacterButton();
}
