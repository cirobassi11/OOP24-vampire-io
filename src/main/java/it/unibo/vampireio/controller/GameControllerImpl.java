package it.unibo.vampireio.controller;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import it.unibo.vampireio.model.Character;
import it.unibo.vampireio.model.Attack;
import it.unibo.vampireio.model.Collectible;
import it.unibo.vampireio.model.Enemy;
import it.unibo.vampireio.model.GameModel;
import it.unibo.vampireio.model.GameWorld;
import it.unibo.vampireio.model.Save;
import it.unibo.vampireio.model.Score;
import it.unibo.vampireio.model.Unlockable;
import it.unibo.vampireio.view.GameView;
import it.unibo.vampireio.view.GameViewImpl;

public class GameControllerImpl implements GameController {

    private static final int FRAME_RATE = 60;
    private static final int TICK_RATE = 60;

    private GameModel model;
    private GameView view;

    private Thread modelThread;
    private Thread viewThread;

    private final InputHandler inputHandler = new InputHandler();

    private boolean running = false;
    private boolean paused = false;

    private final Deque<String> screenHistory = new ArrayDeque<>();

    public GameControllerImpl() {
        this.view = new GameViewImpl();
        this.model = new GameWorld(this);
        this.setListeners();
        this.showScreen(GameViewImpl.SAVE_MENU);
        this.view.setPlayerInputListener(this.inputHandler);
    }

    private void setListeners() {
        // SAVE MENU LISTENERS
        this.view.setNewSaveListener(e -> {
            this.model.createNewSave();
            this.showScreen(GameViewImpl.START);
        });

        this.view.setShowSaveListener(e -> {
            this.view.updateSaveList(this.model.getSaveNames());
            this.showScreen(GameViewImpl.SAVE_SELECTION);
        });

        // START MENU LISTENERS
        this.view.setStartListener(e -> {
            List<UnlockableItemData> choosableCharactersData = this.model.getChoosableCharacters().stream()
                    .map(character -> new UnlockableItemData(character.getId(), character.getName(),
                            character.getDescription(), character.getCurrentLevel(), character.getMaxLevel(),
                            character.getPrice()))
                    .collect(Collectors.toList());
            this.view.setChoosableCharactersData(choosableCharactersData);
            this.showScreen(GameViewImpl.CHOOSE_CHARACTER);
        });

        this.view.setScoreboardListener(e -> {
            this.view.setScoresData(this.getScores());
            this.showScreen(GameViewImpl.SCOREBOARD);
        });

        this.view.setShopListener(e -> {
            this.showScreen(GameViewImpl.SHOP);
        });

        this.view.setLoadSaveListener(e -> {
            this.showScreen(GameViewImpl.SAVE_MENU);
        });

        // CHOOSE CHARACTER LISTENERS
        this.view.setConfirmCharacterListener(e -> {
            String selectedCharacter = this.view.getChoosedCharacter();
            if (selectedCharacter != null && this.startGame(selectedCharacter)) {
                this.view.update(this.getData());
                this.showScreen(GameViewImpl.GAME);
            } else {
                this.view.showError("Error");
            }
        });

        // SAVE SELECTION LISTENERS
        this.view.setChooseSaveListener(e -> {
            String selectedSave = this.view.getSelectedSave();
            if (selectedSave != null) {
                this.model.loadSave(selectedSave);
                this.showScreen(GameViewImpl.START);
            }
        });

        // ITEM SELECTION LISTENERS
        this.view.setChooseItemListener(e -> {
            String selectedItem = this.view.getSelectedItem();
            if (selectedItem != null) {
                this.model.levelUpWeapon(selectedItem);
                this.resumeGame();
                this.view.showScreen(GameViewImpl.GAME);
            }
        });

        // SHOP LISTENERS
        this.view.setCharactersShopListener(e -> {
            List<UnlockableItemData> unlockableCharactersData = this.model.getLockedCharacters().stream()
                    .map(character -> new UnlockableItemData(character.getId(), character.getName(),
                            character.getDescription(), character.getCurrentLevel(), character.getMaxLevel(),
                            character.getPrice()))
                    .collect(Collectors.toList());
            this.view.setUnlockableCharactersData(unlockableCharactersData);
            this.view.setCoinsAmount(this.model.getCurrentSave().getMoneyAmount());
            this.showScreen(GameViewImpl.UNLOCKABLE_CHARACTERS);
        });

        this.view.setPowerupsShopListener(e -> {
            List<UnlockableItemData> unlockablePowerupsData = this.model.getUnlockablePowerups().stream()
                    .map(powerup -> new UnlockableItemData(powerup.getId(), powerup.getName(),
                            powerup.getDescription(), powerup.getCurrentLevel(), powerup.getMaxLevel(),
                            powerup.getPrice()))
                    .collect(Collectors.toList());
            this.view.setUnlockablePowerupsData(unlockablePowerupsData);
            this.view.setCoinsAmount(this.model.getCurrentSave().getMoneyAmount());
            this.showScreen(GameViewImpl.UNLOCKABLE_POWERUPS);
        });

        // UNLOCKABLE CHARACTERS SHOP LISTENERS
        this.view.setBuyCharactersListener(e -> {
            String selectedCharacter = this.view.getSelectedCharacter();
            if (selectedCharacter != null && this.model.buyCharacter(selectedCharacter)) {
                List<UnlockableItemData> unlockableCharactersData = this.model.getLockedCharacters().stream()
                        .map(character -> new UnlockableItemData(character.getId(), character.getName(),
                                character.getDescription(), character.getCurrentLevel(), character.getMaxLevel(),
                                character.getPrice()))
                        .collect(Collectors.toList());
                this.view.setUnlockableCharactersData(unlockableCharactersData);
                this.view.setCoinsAmount(this.model.getCurrentSave().getMoneyAmount());
                this.view.disableBuyButton();
            }
        });

        // UNLOCKABLE POWERUPS SHOP LISTENERS
        this.view.setBuyPowerupsListener(e -> {
            String selectedPowerup = this.view.getSelectedPowerup();
            if (selectedPowerup != null && this.model.buyPowerup(selectedPowerup)) {
                List<UnlockableItemData> unlockablePowerupsData = this.model.getUnlockablePowerups().stream()
                        .map(powerup -> new UnlockableItemData(powerup.getId(), powerup.getName(),
                                powerup.getDescription(), powerup.getCurrentLevel(), powerup.getMaxLevel(),
                                powerup.getPrice()))
                        .collect(Collectors.toList());
                this.view.setUnlockablePowerupsData(unlockablePowerupsData);
                this.view.setCoinsAmount(this.model.getCurrentSave().getMoneyAmount());
                this.view.disableBuyButton();
            }
        });

        // ITEM SELECTION LISTENER
        this.view.setListSelectionListener(e -> {
            String selectedItemID = this.view.getSelectedCharacter();
            String selected;
            if (selectedItemID == null) {
                selected = this.view.getSelectedPowerup();
            } else {
                selected = selectedItemID;
            }
            if (selected != null) {
                final Unlockable selectedItem = this.model.getAllItems().stream()
                        .filter(item -> item.getId() == selected)
                        .findFirst()
                        .orElse(null);
                if (selectedItem == null) {
                    this.showErrorWithExit(selected + " is not a valid item.");
                } else if (this.model.getCurrentSave().getMoneyAmount() < selectedItem.getPrice()
                        || (selectedItem.getCurrentLevel() >= selectedItem.getMaxLevel() && selectedItem.getMaxLevel() > 0)) {
                    this.view.disableBuyButton();
                } else {
                    this.view.enableBuyButton();
                }
            }
        });

        // PAUSE LISTENERS
        this.view.setResumeListener(e -> {
            this.showScreen(GameViewImpl.GAME);
            this.resumeGame();
        });

        this.view.setExitListener(e -> {
            new Thread(this::stop).start();
            Score score = this.model.exitGame();
            ScoreData scoreData = new ScoreData(
                    score.getCharacterName(),
                    score.getSessionTime(),
                    score.getKillCounter(),
                    score.getLevel(),
                    score.getCoinCounter(),
                    score.getScore());
            this.view.setScore(scoreData);
            this.showScreen(GameViewImpl.END_GAME);
        });

        // ENDGAME LISTENERS
        this.view.setReturnMenuListener(e -> {
            this.showScreen(GameViewImpl.START);
        });

        this.view.setBackListener(e -> goBack());

        this.view.setQuitListener(e -> {
            System.exit(0);
        });
    }

    private void showScreen(String newScreen) {
        this.screenHistory.push(newScreen);
        this.view.showScreen(newScreen);
    }

    private void goBack() {
        if (!this.screenHistory.isEmpty()) {
            this.screenHistory.pop();
            if (!this.screenHistory.isEmpty()) {
                this.view.showScreen(this.screenHistory.peek());
            }
        }
    }

    public boolean startGame(String selectedCharacter) {
        if (this.model.initGame(selectedCharacter)) {
            this.running = true;
            this.paused = false;

            this.modelThread = new Thread(this::modelLoop);
            this.viewThread = new Thread(this::viewLoop);

            this.modelThread.start();
            this.viewThread.start();
            return true;
        } else {
            return false;
        }
    }

    private synchronized void pauseGame() {
        this.paused = true;
        this.inputHandler.clearPressedKeys();
    }

    private synchronized void resumeGame() {
        this.paused = false;
        notifyAll();
    }

    private synchronized void stop() {
        this.running = false;

        if (modelThread != null) {
            modelThread.interrupt();
        }
        if (viewThread != null) {
            viewThread.interrupt();
        }
    }

    private void modelLoop() {
        long tickTime = 1000 / TICK_RATE;

        while (this.running && !Thread.currentThread().isInterrupted()) {
            if (this.model.isGameOver()) {
                this.running = false;

                Score score = this.model.exitGame();
                ScoreData scoreData = new ScoreData(
                        score.getCharacterName(),
                        score.getSessionTime(),
                        score.getKillCounter(),
                        score.getLevel(),
                        score.getCoinCounter(),
                        score.getScore());
                this.view.setScore(scoreData);
                this.view.showScreen(GameViewImpl.END_GAME);
                this.inputHandler.clearPressedKeys();
                return;
            }
            if (this.model.hasJustLevelledUp()) {
                this.pauseGame();
                this.view.showScreen(GameViewImpl.ITEM_SELECTION);

                this.view.setItemsData(this.model.getRandomLevelUpWeapons().stream()
                        .map(item -> new ItemData(
                                item.getId(),
                                item.getName(),
                                item.getDescription()))
                        .collect(Collectors.toList()));
            }
            synchronized (this) {
                if (inputHandler.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                    this.pauseGame();
                    this.view.showScreen(GameViewImpl.PAUSE);
                }
                while (this.paused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                if (!this.running) {
                    return;
                }
            }

            Point2D.Double direction = new Point2D.Double(0, 0);
            if (this.inputHandler.isKeyPressed(KeyEvent.VK_W) || this.inputHandler.isKeyPressed(KeyEvent.VK_UP)) {
                direction.y -= 1;
            }
            if (this.inputHandler.isKeyPressed(KeyEvent.VK_S) || this.inputHandler.isKeyPressed(KeyEvent.VK_DOWN)) {
                direction.y += 1;
            }
            if (this.inputHandler.isKeyPressed(KeyEvent.VK_A) || this.inputHandler.isKeyPressed(KeyEvent.VK_LEFT)) {
                direction.x -= 1;
            }
            if (this.inputHandler.isKeyPressed(KeyEvent.VK_D) || this.inputHandler.isKeyPressed(KeyEvent.VK_RIGHT)) {
                direction.x += 1;
            }

            double length = direction.distance(0, 0);
            if (length > 0) {
                direction = new Point2D.Double(direction.x / length, direction.y / length);
            }

            this.model.update(tickTime, direction);

            try {
                Thread.sleep(tickTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void viewLoop() {
        long frameTime = 1000 / FRAME_RATE;

        while (this.running && !Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                while (this.paused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                if (!this.running) {
                    return;
                }
            }

            this.view.update(this.getData());

            try {
                Thread.sleep(frameTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private GameData getData() {
        Dimension visualSize = this.model.getVisualSize();
        Character character = this.model.getCharacter();
        List<Enemy> enemies = this.model.getEnemies();
        List<Attack> attacks = this.model.getAttacks();
        List<Collectible> collectibles = this.model.getCollectibles();

        VisibleMapSizeData visibleMapSizeData = new VisibleMapSizeData(
                visualSize.width, visualSize.height);

        LivingEntityData characterData = new LivingEntityData(
                character.getId(),
                new Point2D.Double(
                        character.getPosition().getX(),
                        character.getPosition().getY()),
                new Point2D.Double(
                        character.getDirection().getX(),
                        character.getDirection().getY()),
                character.getRadius(),
                character.getHealth(),
                character.getMaxHealth(),
                character.isGettingAttacked(),
                character.isMoving());

        List<LivingEntityData> enemiesData = enemies.stream()
                .map(enemy -> new LivingEntityData(
                        enemy.getId(),
                        new Point2D.Double(
                                enemy.getPosition().getX(),
                                enemy.getPosition().getY()),
                        new Point2D.Double(
                                enemy.getDirection().getX(),
                                enemy.getDirection().getY()),
                        enemy.getRadius(),
                        enemy.getHealth(),
                        enemy.getMaxHealth(),
                        enemy.isGettingAttacked(),
                        enemy.isMoving()))
                .collect(Collectors.toList());

        List<PositionableData> attacksData = attacks.stream()
                .map(attack -> new PositionableData(
                        attack.getId(),
                        new Point2D.Double(
                                attack.getPosition().getX(),
                                attack.getPosition().getY()),
                        new Point2D.Double(
                                attack.getDirection().getX(),
                                attack.getDirection().getY()),
                        attack.getRadius()))
                .collect(Collectors.toList());

        List<PositionableData> collectiblesData = collectibles.stream()
                .map(collectible -> new PositionableData(
                        collectible.getId(),
                        new Point2D.Double(
                                collectible.getPosition().getX(),
                                collectible.getPosition().getY()),
                        new Point2D.Double(0, 0),
                        collectible.getRadius()))
                .collect(Collectors.toList());

        List<ItemData> itemsData = this.model.getWeapons().stream()
                .map(item -> new ItemData(
                        item.getId(),
                        "",
                        ""))
                .collect(Collectors.toList());

        return new GameData(
                visibleMapSizeData,
                this.model.getElapsedTime(),
                this.model.getPlayerLevel(),
                this.model.getPlayerLevelPercentage(),
                this.model.getKillCounter(),
                this.model.getCoinCounter(),
                characterData,
                enemiesData,
                attacksData,
                collectiblesData,
                itemsData);
    }

    private List<ScoreData> getScores() {
        Save currentSave = this.model.getCurrentSave();
        if (currentSave == null) {
            return List.of();
        }
        return currentSave.getScores().stream()
                .map(score -> new ScoreData(
                        score.getCharacterName(),
                        score.getSessionTime(),
                        score.getKillCounter(),
                        score.getLevel(),
                        score.getCoinCounter(),
                        score.getScore()))
                .collect(Collectors.toList());
    }

    @Override
    public void showError(String message) {
        this.view.showError(message);
    }

    @Override
    public void showErrorWithExit(String message) {
        this.view.showError(message);
        System.exit(1);
    }
}
