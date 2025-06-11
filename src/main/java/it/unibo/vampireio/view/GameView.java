package it.unibo.vampireio.view;

import java.util.List;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import it.unibo.vampireio.controller.GameData;
import it.unibo.vampireio.controller.InputHandler;
import it.unibo.vampireio.controller.ItemData;
import it.unibo.vampireio.controller.ScoreData;
import it.unibo.vampireio.controller.UnlockableItemData;

/**
 * Interface representing the view of the game.
 * It provides methods to update the view with game data,
 * handle user input, and manage different game screens.
 */
public interface GameView {

        /**
         * SaveMenuPanel id.
         */
        String SAVE_MENU = "saveMenu";

        /**
         * SaveSelectionPanel id.
         */
        String SAVE_SELECTION = "saveSelection";

        /**
         * StartMenuPanel id.
         */
        String START = "mainMenu";

        /**
         * ScoreboardPanel id.
         */
        String SCOREBOARD = "scoreboard";

        /**
         * ChooseCharacterPanel id.
         */
        String CHOOSE_CHARACTER = "chooseCharacter";

        /**
         * GamePanel id.
         */
        String GAME = "game";

        /**
         * ItemSelectionPanel id.
         */
        String ITEM_SELECTION = "itemSelection";

        /**
         * UnlockableItemShopPanel id.
         */
        String END_GAME = "endGame";

        /**
         * UnlockableItemShopPanel id.
         */
        String PAUSE = "pause";

        /**
         * UnlockableItemShopPanel id.
         */
        String SHOP = "shop";

        /**
         * UnlockableItemShopPanel id.
         */
        String UNLOCKABLE_CHARACTERS = "unlockableCharacters";

        /**
         * UnlockableItemShopPanel id.
         */
        String UNLOCKABLE_POWERUPS = "unlockablePowerUps";

        /**
         * Updates the view with the current game data.
         *
         * @param data The current game data to be displayed.
         */
        void update(GameData data);

        /**
         * Displays the specified screen.
         *
         * @param name The name of the screen to be displayed.
         */
        void showScreen(String name);

        /**
         * Updates the save list displayed in SaveSelectionPanel.
         *
         * @param saves A list of save names to be displayed.
         */
        void updateSaveList(List<String> saves);

        /**
         * Sets the input handler for player input.
         *
         * @param listener The input handler to be set for player input.
         */
        void setPlayerInputListener(InputHandler listener);

        /**
         * Sets the error listener for handling view errors.
         *
         * @param listener The listener to handle view errors.
         */
        void setViewErrorListener(ViewErrorListener listener);

        /**
         * Notifies the view of an error message.
         *
         * @param message The error message to be displayed.
         */
        void notifyError(String message);

        /**
         * Displays an error message in the view.
         *
         * @param errorMessage The error message to be displayed.
         */
        void showError(String errorMessage);

        /**
         * Sets the listeners for the SaveMenuPanel.
         *
         * @param newSaveListener ActionListener for creating a new save.
         * @param showSaveListener ActionListener for showing existing saves.
         */
        void setSaveMenuPanelListeners(
                        ActionListener newSaveListener,
                        ActionListener showSaveListener);

        /**
         * Sets the listeners for the StartMenuPanel.
         *
         * @param startListener ActionListener for starting a new game.
         * @param scoreboardListener ActionListener for viewing the scoreboard.
         * @param shopListener ActionListener for accessing the shop.
         * @param loadSaveListener ActionListener for loading a save.
         */
        void setStartMenuPanelListeners(
                        ActionListener startListener,
                        ActionListener scoreboardListener,
                        ActionListener shopListener,
                        ActionListener loadSaveListener);

        /**
         * Sets the listeners for the SaveSelectionPanel.
         *
         * @param chooseSaveListener ActionListener for choosing a save.
         * @param backListener ActionListener for going back to the previous menu.
         */
        void setSaveSelectionPanelListener(
                        ActionListener chooseSaveListener,
                        ActionListener backListener);

        /**
         * Sets the listeners for the ChooseCharacterPanel.
         *
         * @param characterSelectionListener ListSelectionListener for character selection.
         * @param confirmCharacterListener ActionListener for confirming character selection.
         * @param backListener ActionListener for going back to the previous menu.
         */
        void setChooseCharacterPanelListener(
                        ListSelectionListener characterSelectionListener,
                        ActionListener confirmCharacterListener,
                        ActionListener backListener);

        /**
         * Sets the listeners for the ShopPanel.
         *
         * @param characterShopListener ActionListener for accessing the character shop.
         * @param powerUpsShopListener ActionListener for accessing the power-ups shop.
         * @param backListener ActionListener for going back to the previous menu.
         */
        void setShopPanelListener(
                        ActionListener characterShopListener,
                        ActionListener powerUpsShopListener,
                        ActionListener backListener);

        /**
         * Sets the listeners for the ScoreboardPanel.
         *
         * @param backListener ActionListener for going back to the previous menu.
         */
        void setScoreboardPanelListener(ActionListener backListener);

        /**
         * Sets the listeners for the EndGamePanel.
         *
         * @param returnMenuListener ActionListener for returning to the main menu.
         */
        void setEndGamePanelListener(ActionListener returnMenuListener);

        /**
         * Sets the listeners for the PausePanel.
         *
         * @param resumeListener ActionListener for resuming the game.
         * @param exitListener ActionListener for exiting the game.
         */
        void setPausePanelListener(
                        ActionListener resumeListener,
                        ActionListener exitListener);

        /**
         * Sets the listeners for the ItemSelectionPanel.
         *
         * @param chooseItemListener ActionListener for choosing an item.
         */
        void setItemSelectionPanelListener(ActionListener chooseItemListener);

        /**
         * Sets the listeners for the UnlockableItemShopPanel.
         *
         * @param buyCharacterListener ActionListener for buying a character.
         * @param buyPowerUpListener ActionListener for buying a power-up.
         * @param listSelectionListener ListSelectionListener for item selection.
         * @param backListener ActionListener for going back to the previous menu.
         */
        void setUnlockableItemShopPanelListener(
                        ActionListener buyCharacterListener,
                        ActionListener buyPowerUpListener,
                        ListSelectionListener listSelectionListener,
                        ActionListener backListener);

        /**
         * Sets the scores data to be displayed in the scoreboard.
         *
         * @param scores A list of ScoreData objects representing the scores.
         */
        void setScoresData(List<ScoreData> scores);

        /**
         * Sets the choosable characters data to be displayed in the character selection.
         *
         * @param choosableCharactersData A list of UnlockableItemData representing the characters.
         */
        void setChoosableCharactersData(List<UnlockableItemData> choosableCharactersData);

        /**
         * Gets the selected character from the character selection.
         *
         * @return The id of the selected character.
         */
        String getSelectedCharacter();

        /**
         * Gets the choosed character.
         *
         * @return The id of the choosed character.
         */
        String getChoosedCharacter();

        /**
         * Sets the amount of coins available in the current save.
         *
         * @param coins The number of coins to be displayed.
         */
        void setCoinsAmount(int coins);

        /**
         * Sets the unlockable characters data to be displayed in the unlockable characters shop screen.
         *
         * @param unlockableCharactersData A list of UnlockableItemData representing the unlockable characters.
         */
        void setUnlockableCharactersData(List<UnlockableItemData> unlockableCharactersData);

        /**
         * Gets the selected power-up from the power-ups selection.
         *
         * @return The id of the selected power-up.
         */
        String getSelectedPowerUp();

        /**
         * Sets the unlockable power-ups data to be displayed in the unlockable power-ups shop screen.
         *
         * @param unlockablePowerUpsData A list of UnlockableItemData representing the unlockable power-ups.
         */
        void setUnlockablePowerUpsData(List<UnlockableItemData> unlockablePowerUpsData);

        /**
         * Gets the selected save from the save selection.
         *
         * @return The name of the selected save.
         */
        String getSelectedSave();

        /**
         * Sets the items data to be displayed in the item selection screen.
         *
         * @param itemsData A list of ItemData representing the items.
         */
        void setItemsData(List<ItemData> itemsData);

        /**
         * Gets the selected item from the item selection.
         *
         * @return The id of the selected item.
         */
        String getSelectedItem();

        /**
         * Sets the current score to be displayed in the game view.
         *
         * @param score The ScoreData object representing the current score.
         */
        void setCurrentScore(ScoreData score);

        /**
         * Disables the buy button in the unlockable characters and power-ups shop.
         */
        void disableBuyButton();

        /**
         * Enables the buy button in the unlockable characters and power-ups shop.
         */
        void enableBuyButton();

        /**
         * Disables the confirm character button in the character selection.
         */
        void disableConfirmCharacterButton();

        /**
         * Enables the confirm character button in the character selection.
         */
        void enableConfirmCharacterButton();
}
