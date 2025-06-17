package it.unibo.vampireio.model.impl;

import java.awt.geom.Point2D;
import java.awt.Dimension;
import java.util.List;
import java.util.Optional;
import it.unibo.vampireio.model.manager.EntityManager;
import it.unibo.vampireio.model.manager.SaveManager;
import it.unibo.vampireio.model.manager.ShopManager;
import it.unibo.vampireio.model.api.Attack;
import it.unibo.vampireio.model.api.Collectible;
import it.unibo.vampireio.model.api.GameModel;
import it.unibo.vampireio.model.api.Living;
import it.unibo.vampireio.model.api.ModelErrorListener;
import it.unibo.vampireio.model.api.Score;
import it.unibo.vampireio.model.api.Unlockable;
import it.unibo.vampireio.model.api.Weapon;
import it.unibo.vampireio.model.data.ConfigData;
import it.unibo.vampireio.model.data.DataLoader;
import it.unibo.vampireio.model.data.GameDataProvider;
import it.unibo.vampireio.model.data.WeaponData;
import java.util.Collection;

/**
 * GameModelImpl is the main class that represents the model and implements the
 * GameModel interface.
 * It manages the game state, including entities, score, saves, and shop
 * functionalities.
 */
public final class GameModelImpl implements GameModel {
    private final SaveManager saveManager;
    private final ShopManager shopManager;

    private ModelErrorListener errorListener;

    private boolean isGameOver;
    private ScoreImpl score;

    private ConfigData configData;
    private EntityManager entityManager;
    private GameDataProvider gameDataProvider;

    /**
     * Constructs a GameModelImpl instance, initializes the data loader, save manager,
     * and shop manager.
     */
    public GameModelImpl() {
        DataLoader.init(this);
        this.saveManager = new SaveManager(this);
        this.shopManager = new ShopManager(this.saveManager);

        final Optional<ConfigData> optionalConfigData = DataLoader.getInstance().getConfigLoader()
                .get(ConfigData.CONFIG_ID);
        if (optionalConfigData.isPresent()) {
            this.configData = optionalConfigData.get();
        } else {
            this.notifyError("Config data not found!");
        }
    }

    @Override
    public void setModelErrorListener(final ModelErrorListener listener) {
        this.errorListener = listener;
    }

    @Override
    public void notifyError(final String errorMessage) {
        if (this.errorListener != null) {
            this.errorListener.onError(errorMessage);
        }
    }

    @Override
    public boolean initGame(final String selectedCharacter) {
        this.isGameOver = false;
        final Optional<UnlockableCharacter> optionalSelectedUnlockableCharacter = DataLoader.getInstance()
                .getCharacterLoader()
                .get(selectedCharacter);
        if (!optionalSelectedUnlockableCharacter.isPresent()) {
            return false;
        }
        final UnlockableCharacter selectedUnlockableCharacter = optionalSelectedUnlockableCharacter.get();
        this.score = new ScoreImpl(selectedUnlockableCharacter.getName());
        this.entityManager = new EntityManager(this.configData, this.score, this.saveManager, selectedUnlockableCharacter);
        this.gameDataProvider = new GameDataProvider(entityManager, saveManager, score);
        return true;
    }

    @Override
    public boolean isGameOver() {
        return this.isGameOver;
    }

    @Override
    public void update(final long tickTime, final Point2D.Double characterDirection) {
        this.score.incrementSessionTime(tickTime);
        this.entityManager.updateEntities(tickTime, characterDirection);

        if (entityManager.getCharacter().getHealth() <= 0) {
            this.isGameOver = true;
        }
    }

    @Override
    public Dimension getVisualSize() {
        return new Dimension(VISUAL_SIZE.width, VISUAL_SIZE.height);
    }

    @Override
    public Living getCharacter() {
        return this.gameDataProvider.getCharacter();
    }

    @Override
    public List<Living> getEnemies() {
        return this.gameDataProvider.getEnemies();
    }

    @Override
    public List<Attack> getAttacks() {
        return this.gameDataProvider.getAttacks();
    }

    @Override
    public List<Weapon> getWeapons() {
        return this.gameDataProvider.getWeapons();
    }

    @Override
    public List<Collectible> getCollectibles() {
        return this.gameDataProvider.getCollectibles();
    }

    @Override
    public int getPlayerLevel() {
        return this.gameDataProvider.getCharacter().getLevel();
    }

    @Override
    public double getPlayerLevelPercentage() {
        return this.gameDataProvider.getCharacter().getLevelPercentage();
    }

    @Override
    public int getKillCounter() {
        return this.gameDataProvider.getKillCounter();
    }

    @Override
    public int getCoinCounter() {
        return this.gameDataProvider.getCharacter().getCoinCounter();
    }

    @Override
    public List<UnlockableCharacter> getChoosableCharacters() {
        return this.shopManager.getChoosableCharacters();
    }

    @Override
    public List<UnlockableCharacter> getLockedCharacters() {
        return this.shopManager.getLockedCharacters();
    }

    @Override
    public boolean buyCharacter(final String selectedCharacter) {
        return this.shopManager.buyCharacter(selectedCharacter);
    }

    @Override
    public List<UnlockablePowerUp> getUnlockablePowerUps() {
        return this.shopManager.getUnlockablePowerUps();
    }

    @Override
    public List<String> getSaveNames() {
        return this.saveManager.getSavesNames();
    }

    @Override
    public void loadSave(final String selectedSave) {
        this.saveManager.loadSave(selectedSave);
    }

    @Override
    public long getElapsedTime() {
        if (this.score == null) {
            return 0;
        }
        return this.score.getSessionTime();
    }

    @Override
    public boolean hasJustLevelledUp() {
        return this.gameDataProvider.hasJustLevelledUp();
    }

    @Override
    public List<WeaponData> getRandomLevelUpWeapons() {
        return this.entityManager.getRandomWeaponsForLevelUp();
    }

    @Override
    public Score exitGame() {
        final ScoreImpl tempScore = new ScoreImpl(score);
        tempScore.setLevel(this.entityManager.getCharacter().getLevel());
        tempScore.setCoinCounter(this.entityManager.getCharacter().getCoinCounter());
        this.saveManager.incrementMoneyAmount(getCoinCounter());
        this.saveManager.addScore(tempScore);
        this.saveManager.saveCurrentSave();
        return tempScore;
    }

    @Override
    public Collection<Unlockable> getAllItems() {
        return this.shopManager.getAllItems();
    }

    @Override
    public void levelUpWeapon(final String selectedWeapon) {
        this.entityManager.levelUpWeapon(selectedWeapon);
    }

    @Override
    public void createNewSave() {
        this.saveManager.createNewSave();
    }

    @Override
    public boolean buyPowerUp(final String selectedPowerUp) {
        return this.shopManager.buyPowerUp(selectedPowerUp);
    }

    @Override
    public int getMoneyAmount() {
        return this.saveManager.getMoneyAmount();
    }

    @Override
    public List<Score> getScores() {
        return this.saveManager.getScores();
    }
}
