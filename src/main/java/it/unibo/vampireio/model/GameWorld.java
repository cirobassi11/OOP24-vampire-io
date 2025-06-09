package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
import java.awt.Dimension;
import java.util.List;
import java.util.Optional;
import java.util.Collection;




public class GameWorld implements GameModel {

    static final Dimension VISUAL_SIZE = new Dimension(1280, 720);
    private ModelErrorListener errorListener;

    private boolean isGameOver;
    private Score score;

    private ConfigData configData;

    private EntityManager entityManager;
    private SaveManager saveManager;
    private ShopManager shopManager;
    private GameDataProvider gameDataProvider;

    public GameWorld() {
        DataLoader.init(this);
        this.saveManager = new SaveManager(this);
        this.shopManager = new ShopManager(this.saveManager);

        final ConfigData configData = DataLoader.getInstance().getConfigLoader().get("").orElse(null);
        if (configData != null) {
            this.configData = configData;
        } else {
            this.notifyError("Config data not found!");
        }
    }

    @Override
    public void setModelErrorListener(final ModelErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    void notifyError(final String errorMessage) {
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

        this.score = new Score(selectedUnlockableCharacter.getName());

        this.entityManager = new EntityManager(this.configData, this.score, this.saveManager,
                selectedUnlockableCharacter);

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
        return VISUAL_SIZE;
    }

    @Override
    public Character getCharacter() {
        return this.gameDataProvider.getCharacter();
    }

    @Override
    public List<Enemy> getEnemies() {
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
    public Save getCurrentSave() {
        return this.saveManager.getCurrentSave();
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
        return this.gameDataProvider.getCharacter().hasJustLevelledUp();
    }

    @Override
    public List<WeaponData> getRandomLevelUpWeapons() {
        return this.entityManager.getRandomWeaponsForLevelUp();
    }

    @Override
    public Score exitGame() {
        this.score.setLevel(this.entityManager.getCharacter().getLevel());
        this.score.setCoinCounter(this.entityManager.getCharacter().getCoinCounter());
        this.saveManager.getCurrentSave().incrementMoneyAmount(getCoinCounter());
        this.saveManager.getCurrentSave().addScore(this.score);
        this.saveManager.saveCurrentSave();
        return this.score;
    }

    @Override
    public Collection<Unlockable> getAllItems() {
        return this.shopManager.getAllItems();
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
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
}
