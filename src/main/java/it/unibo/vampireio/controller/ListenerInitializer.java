package it.unibo.vampireio.controller;

import it.unibo.vampireio.model.GameModel;
import it.unibo.vampireio.model.Unlockable;
import it.unibo.vampireio.model.UnlockableCharacter;
import it.unibo.vampireio.model.Score;
import it.unibo.vampireio.view.GameView;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.event.ListSelectionListener;

public final class ListenerInitializer {

    private ListenerInitializer() {
    }

    public static void initialize(GameView view, GameModel model, GameControllerImpl controller,
            GameLoopManager gameLoopManager, ScreenManager screenManager, InputHandler inputHandler) {

        view.setPlayerInputListener(inputHandler);
        screenManager.showScreen(GameView.SAVE_MENU);

        ActionListener backListener = e -> screenManager.goBack();
        ActionListener quitListener = e -> System.exit(0);

        // SAVE MENU
        ActionListener newSaveListener = e -> {
            model.createNewSave();
            screenManager.showScreen(GameView.START);
        };
        ActionListener showSaveListener = e -> {
            view.updateSaveList(model.getSaveNames());
            screenManager.showScreen(GameView.SAVE_SELECTION);
        };

        view.setSaveMenuPanelListeners(newSaveListener, showSaveListener, quitListener);

        // START MENU
        ActionListener startListener = e -> {
            List<UnlockableItemData> characters = model.getChoosableCharacters().stream()
                    .map(c -> new UnlockableItemData(c.getId(), c.getName(), c.getDescription(), c.getCurrentLevel(),
                            c.getMaxLevel(), c.getPrice()))
                    .collect(Collectors.toList());
            view.setChoosableCharactersData(characters);
            screenManager.showScreen(GameView.CHOOSE_CHARACTER);
        };
        ActionListener scoreboardListener = e -> {
            view.setScoresData(DataBuilder.getScores(model));
            screenManager.showScreen(GameView.SCOREBOARD);
        };
        ActionListener shopListener = e -> screenManager.showScreen(GameView.SHOP);
        ActionListener loadSaveListener = e -> screenManager.showScreen(GameView.SAVE_MENU);

        view.setStartMenuPanelListeners(
                startListener,
                scoreboardListener,
                shopListener,
                loadSaveListener,
                quitListener);

        // CHOOSE CHARACTER
        ActionListener confirmCharacterListener = e -> {
            String selected = view.getChoosedCharacter();
            if (selected != null && gameLoopManager.startGame(selected)) {
                view.update(DataBuilder.getData(model));
                screenManager.showScreen(GameView.GAME);
            }
        };

        // CHARACTER SELECTION
        ListSelectionListener setCharacterSelectionListener = e -> {
            final String selected = view.getChoosedCharacter();

            if (selected != null) {
                UnlockableCharacter character = model.getChoosableCharacters().stream()
                        .filter(i -> i.getId().equals(selected))
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
        ActionListener setChooseSaveListener = e -> {
            String save = view.getSelectedSave();
            if (save != null) {
                model.loadSave(save);
                screenManager.showScreen(GameView.START);
            }
        };

        view.setSaveSelectionPanelListener(setChooseSaveListener, backListener);

        // ITEM SELECTION
        ActionListener setChooseItemListener = e -> {
            String item = view.getSelectedItem();
            if (item != null) {
                model.levelUpWeapon(item);
                gameLoopManager.resume();
                screenManager.showScreen(GameView.GAME);
            }
        };

        view.setItemSelectionPanelListener(setChooseItemListener);

        // SHOP
        ActionListener setCharacterShopListener = e -> {
            List<UnlockableItemData> characters = model.getLockedCharacters().stream()
                    .map(c -> new UnlockableItemData(c.getId(), c.getName(), c.getDescription(), c.getCurrentLevel(),
                            c.getMaxLevel(), c.getPrice()))
                    .collect(Collectors.toList());
            view.setUnlockableCharactersData(characters);
            view.setCoinsAmount(model.getCurrentSave().getMoneyAmount());
            screenManager.showScreen(GameView.UNLOCKABLE_CHARACTERS);
        };

        ActionListener setPowerUpsShopListener = e -> {
            List<UnlockableItemData> powerUps = model.getUnlockablePowerUps().stream()
                    .map(p -> new UnlockableItemData(p.getId(), p.getName(), p.getDescription(), p.getCurrentLevel(),
                            p.getMaxLevel(), p.getPrice()))
                    .collect(Collectors.toList());
            view.setUnlockablePowerUpsData(powerUps);
            view.setCoinsAmount(model.getCurrentSave().getMoneyAmount());
            screenManager.showScreen(GameView.UNLOCKABLE_POWERUPS);
        };

        view.setShopPanelListener(setCharacterShopListener, setPowerUpsShopListener, backListener);

        // SCOREBOARD
        view.setScoreboardPanelListener(backListener);

        // BUY CHARACTERS
        ActionListener setBuyCharactersListener = e -> {
            String character = view.getSelectedCharacter();
            if (character != null && model.buyCharacter(character)) {
                List<UnlockableItemData> characters = model.getLockedCharacters().stream()
                        .map(c -> new UnlockableItemData(c.getId(), c.getName(), c.getDescription(),
                                c.getCurrentLevel(), c.getMaxLevel(), c.getPrice()))
                        .collect(Collectors.toList());
                view.setUnlockableCharactersData(characters);
                view.setCoinsAmount(model.getCurrentSave().getMoneyAmount());
                view.disableBuyButton();
            }
        };

        // ITEM SELECTION
        ListSelectionListener setListSelectionListener = e -> {
            String tempSelected = view.getSelectedCharacter();
            if (tempSelected == null) {
                tempSelected = view.getSelectedPowerUp();
            }
            final String selected = tempSelected;

            if (selected != null) {
                Unlockable item = model.getAllItems().stream()
                        .filter(i -> i.getId().equals(selected))
                        .findFirst()
                        .orElse(null);

                if (item == null) {
                    controller.showError(selected + " is not a valid item.");
                } else if ((model.getCurrentSave().getMoneyAmount() < item.getPrice())
                        || (item.getCurrentLevel() >= item.getMaxLevel() && item.getMaxLevel() > 0)) {
                    view.disableBuyButton();
                } else {
                    view.enableBuyButton();
                }
            } else {
                view.disableBuyButton();
            }
        };

        // BUY POWERUPS
        ActionListener setBuyPowerUpsListener = e -> {
            String powerUp = view.getSelectedPowerUp();
            if (powerUp != null && model.buyPowerUp(powerUp)) {
                List<UnlockableItemData> powerUps = model.getUnlockablePowerUps().stream()
                        .map(p -> new UnlockableItemData(p.getId(), p.getName(), p.getDescription(),
                                p.getCurrentLevel(), p.getMaxLevel(), p.getPrice()))
                        .collect(Collectors.toList());
                view.setUnlockablePowerUpsData(powerUps);
                view.setCoinsAmount(model.getCurrentSave().getMoneyAmount());
                view.disableBuyButton();
            }
        };

        view.setUnlockableItemShopPanelListener(setBuyCharactersListener, setBuyPowerUpsListener,
                setListSelectionListener, backListener);

        // PAUSE MENU
        ActionListener setResumeListener = e -> {
            screenManager.showScreen(GameView.GAME);
            gameLoopManager.resume();
        };

        ActionListener setExitListener = e -> {
            gameLoopManager.stop();
            Score score = model.exitGame();
            ScoreData data = new ScoreData(score.getCharacterName(), score.getSessionTime(), score.getKillCounter(),
                    score.getLevel(), score.getCoinCounter(), score.getScore());
            view.setCurrentScore(data);
            screenManager.showScreen(GameView.END_GAME);
        };

        view.setPausePanelListener(setResumeListener, setExitListener);

        // ENDGAME
        ActionListener setReturnMenuListener = e -> {
            screenManager.showScreen(GameView.START);
        };
        view.setEndGamePanelListener(setReturnMenuListener);
    }
}