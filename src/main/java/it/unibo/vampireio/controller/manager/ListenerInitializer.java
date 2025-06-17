package it.unibo.vampireio.controller.manager;

import it.unibo.vampireio.controller.data.DataBuilder;
import it.unibo.vampireio.controller.data.ScoreData;
import it.unibo.vampireio.controller.data.UnlockableItemData;
import it.unibo.vampireio.controller.impl.GameControllerImpl;
import it.unibo.vampireio.model.api.GameModel;
import it.unibo.vampireio.model.api.Unlockable;
import it.unibo.vampireio.model.impl.UnlockableCharacter;
import it.unibo.vampireio.model.api.Score;
import it.unibo.vampireio.view.api.GameView;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.event.ListSelectionListener;

/**
 * ListenerInitializer is a utility class that initializes all the listeners for
 * the game view.
 * It sets up the action listeners for various game screens and handles user
 * interactions.
 */
public final class ListenerInitializer {

    /**
     * Private constructor to prevent instantiation.
     */
    private ListenerInitializer() {
    }

    /**
     * Initializes the listeners for the game view, model, controller, game loop
     * manager, screen manager, and input handler.
     *
     * @param view            the game view
     * @param model           the game model
     * @param controller      the game controller
     * @param gameLoopManager the game loop manager
     * @param screenManager   the screen manager
     * @param inputProcessor  the input handler
     */
    public static void init(
            final GameView view,
            final GameModel model,
            final GameControllerImpl controller,
            final GameLoopManager gameLoopManager,
            final ScreenManager screenManager,
            final InputProcessor inputProcessor) {

        view.setPlayerInputListener(inputProcessor);
        screenManager.showScreen(GameView.SAVE_MENU);

        final ActionListener backListener = e -> screenManager.goBack();

        // SAVE MENU
        final ActionListener newSaveListener = e -> {
            model.createNewSave();
            screenManager.showScreen(GameView.START);
        };
        final ActionListener showSaveListener = e -> {
            view.updateSaveList(model.getSaveNames());
            screenManager.showScreen(GameView.SAVE_SELECTION);
        };

        view.setSaveMenuPanelListeners(newSaveListener, showSaveListener);

        // START MENU
        final ActionListener startListener = e -> {
            final List<UnlockableItemData> characters = model.getChoosableCharacters().stream()
                    .map(character -> new UnlockableItemData(
                        character.getId(),
                        character.getName(),
                        character.getDescription(),
                        character.getCurrentLevel(),
                        character.getMaxLevel(),
                        character.getPrice()))
                    .toList();
            view.setChoosableCharactersData(characters);
            screenManager.showScreen(GameView.CHOOSE_CHARACTER);
        };
        final ActionListener scoreboardListener = e -> {
            view.setScoresData(DataBuilder.getScores(model));
            screenManager.showScreen(GameView.SCOREBOARD);
        };
        final ActionListener shopListener = e -> screenManager.showScreen(GameView.SHOP);
        final ActionListener loadSaveListener = e -> screenManager.showScreen(GameView.SAVE_MENU);

        view.setStartMenuPanelListeners(
                startListener,
                scoreboardListener,
                shopListener,
                loadSaveListener);

        // CHOOSE CHARACTER
        final ActionListener confirmCharacterListener = e -> {
            final String selected = view.getChoosedCharacter();
            if (selected != null && gameLoopManager.startGame(selected)) {
                view.update(DataBuilder.getData(model));
                screenManager.showScreen(GameView.GAME);
            }
        };

        // CHARACTER SELECTION
        final ListSelectionListener setCharacterSelectionListener = e -> {
            final String selected = view.getChoosedCharacter();

            if (selected != null) {
                final UnlockableCharacter character = model.getChoosableCharacters().stream()
                        .filter(selectedCharacter -> selectedCharacter.getId().equals(selected))
                        .findFirst()
                        .orElse(null);
                if (character == null) {
                    view.disableConfirmCharacterButton();
                    controller.showError(selected + " is not a valid character.");
                } else {
                    view.enableConfirmCharacterButton();
                }
            } else {
                view.disableConfirmCharacterButton();
            }
        };

        view.setChooseCharacterPanelListener(setCharacterSelectionListener, confirmCharacterListener, backListener);

        // SAVE SELECTION
        final ActionListener setChooseSaveListener = e -> {
            final String save = view.getSelectedSave();
            if (save != null) {
                model.loadSave(save);
                screenManager.showScreen(GameView.START);
            }
        };

        view.setSaveSelectionPanelListener(setChooseSaveListener, backListener);

        // ITEM SELECTION
        final ActionListener setChooseItemListener = e -> {
            final String item = view.getSelectedItem();
            if (item != null) {
                model.levelUpWeapon(item);
                gameLoopManager.resume();
                screenManager.showScreen(GameView.GAME);
            }
        };

        view.setItemSelectionPanelListener(setChooseItemListener);

        // SHOP
        final ActionListener setCharacterShopListener = e -> {
            final List<UnlockableItemData> characters = model.getLockedCharacters().stream()
                    .map(character -> new UnlockableItemData(
                        character.getId(),
                        character.getName(),
                        character.getDescription(),
                        character.getCurrentLevel(),
                        character.getMaxLevel(),
                        character.getPrice()))
                    .toList();
            view.setUnlockableCharactersData(characters);
            view.setCoinsAmount(model.getMoneyAmount());
            screenManager.showScreen(GameView.UNLOCKABLE_CHARACTERS);
        };

        final ActionListener setPowerUpsShopListener = e -> {
            final List<UnlockableItemData> powerUps = model.getUnlockablePowerUps().stream()
                    .map(powerUp -> new UnlockableItemData(
                        powerUp.getId(),
                        powerUp.getName(),
                        powerUp.getDescription(),
                        powerUp.getCurrentLevel(),
                        powerUp.getMaxLevel(),
                        powerUp.getPrice()))
                    .toList();
            view.setUnlockablePowerUpsData(powerUps);
            view.setCoinsAmount(model.getMoneyAmount());
            screenManager.showScreen(GameView.UNLOCKABLE_POWERUPS);
        };

        view.setShopPanelListener(setCharacterShopListener, setPowerUpsShopListener, backListener);

        // SCOREBOARD
        view.setScoreboardPanelListener(backListener);

        // BUY CHARACTERS
        final ActionListener setBuyCharactersListener = e -> {
            final String selectedCharacter = view.getSelectedCharacter();
            if (selectedCharacter != null && model.buyCharacter(selectedCharacter)) {
                final List<UnlockableItemData> characters = model.getLockedCharacters().stream()
                        .map(character -> new UnlockableItemData(
                            character.getId(),
                            character.getName(),
                            character.getDescription(),
                            character.getCurrentLevel(),
                            character.getMaxLevel(),
                            character.getPrice()))
                        .toList();
                view.setUnlockableCharactersData(characters);
                view.setCoinsAmount(model.getMoneyAmount());
                view.disableBuyButton();
            }
        };

        // ITEM SELECTION
        final ListSelectionListener setListSelectionListener = e -> {
            String tempSelected = view.getSelectedCharacter();
            if (tempSelected == null) {
                tempSelected = view.getSelectedPowerUp();
            }
            final String selected = tempSelected;

            if (selected != null) {
                final Unlockable item = model.getAllItems().stream()
                        .filter(it -> it.getId().equals(selected))
                        .findFirst()
                        .orElse(null);

                if (item == null) {
                    controller.showError(selected + " is not a valid item.");
                } else if (model.getMoneyAmount() < item.getPrice()
                        || item.getCurrentLevel() >= item.getMaxLevel() && item.getMaxLevel() > 0) {
                    view.disableBuyButton();
                } else {
                    view.enableBuyButton();
                }
            } else {
                view.disableBuyButton();
            }
        };

        // BUY POWERUPS
        final ActionListener setBuyPowerUpsListener = e -> {
            final String powerUp = view.getSelectedPowerUp();
            if (powerUp != null && model.buyPowerUp(powerUp)) {
                final List<UnlockableItemData> powerUps = model.getUnlockablePowerUps().stream()
                        .map(powerup -> new UnlockableItemData(
                            powerup.getId(),
                            powerup.getName(),
                            powerup.getDescription(),
                            powerup.getCurrentLevel(),
                            powerup.getMaxLevel(),
                            powerup.getPrice()))
                        .toList();
                view.setUnlockablePowerUpsData(powerUps);
                view.setCoinsAmount(model.getMoneyAmount());
                view.disableBuyButton();
            }
        };

        view.setUnlockableItemShopPanelListener(setBuyCharactersListener, setBuyPowerUpsListener,
                setListSelectionListener, backListener);

        // PAUSE MENU
        final ActionListener setResumeListener = e -> {
            screenManager.showScreen(GameView.GAME);
            gameLoopManager.resume();
        };

        final ActionListener setExitListener = e -> {
            gameLoopManager.stop();
            final Score score = model.exitGame();
            final ScoreData data = new ScoreData(score.getCharacterName(), score.getSessionTime(),
                    score.getKillCounter(),
                    score.getLevel(), score.getCoinCounter(), score.getScore());
            view.setCurrentScore(data);
            screenManager.showScreen(GameView.END_GAME);
        };

        view.setPausePanelListener(setResumeListener, setExitListener);

        // ENDGAME
        final ActionListener setReturnMenuListener = e -> {
            screenManager.showScreen(GameView.START);
        };
        view.setEndGamePanelListener(setReturnMenuListener);
    }
}
