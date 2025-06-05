package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.GameController;
import java.awt.geom.Point2D;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.text.html.parser.Entity;

import java.util.Collection;
import java.util.Iterator;

public class GameWorld implements GameModel {
    private static final int LEVELUP_CHOICES = 3;

    private ModelErrorListener errorListener;

    private boolean isGameOver;
    private Score score;
    private WeaponRandomizer weaponRandomizer;

    private ConfigData configData;

    private EntityManager entityManager;
    private SaveManager saveManager;
    private ShopManager shopManager;

    static final Dimension VISUAL_SIZE = new Dimension(1280, 720);

    public GameWorld() {
        DataLoader.init(this);
        this.saveManager = new SaveManager(this);
        this.shopManager = new ShopManager(this.saveManager);

        ConfigData configData = DataLoader.getInstance().getConfigLoader().get("").orElse(null);
        if (configData != null) {
            this.configData = configData;
        } else {
            this.notifyError("Config data not found!");
        }
    }

    @Override
    public void setModelErrorListener(ModelErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    void notifyError(String errorMessage) {
        if (this.errorListener != null) {
            this.errorListener.onError(errorMessage);
        }
    }

    @Override
    public boolean initGame(String selectedCharacter) {
        this.isGameOver = false;

        Optional<UnlockableCharacter> optionalSelectedUnlockableCharacter = DataLoader.getInstance()
                .getCharacterLoader()
                .get(selectedCharacter);
        if (!optionalSelectedUnlockableCharacter.isPresent()) {
            return false;
        }
        UnlockableCharacter selectedUnlockableCharacter = optionalSelectedUnlockableCharacter.get();

        this.score = new Score(selectedUnlockableCharacter.getName());

        this.entityManager = new EntityManager(this.configData, this.score, this.saveManager,
                selectedUnlockableCharacter);

        // ??????
        Map<String, Integer> unlockedPowerups = this.saveManager.getCurrentSave().getUnlockedPowerups();
        for (Map.Entry<String, Integer> entry : unlockedPowerups.entrySet()) {
            Optional<UnlockablePowerup> powerupOpt = DataLoader.getInstance().getPowerupLoader().get(entry.getKey());
            powerupOpt.ifPresent(p -> p.setCurrentLevel(entry.getValue()));
        }
        //////////

        return true;
    }

    @Override
    public boolean isGameOver() {
        return this.isGameOver;
    }

    @Override
    public void update(long tickTime, Point2D.Double characterDirection) {
        synchronized (this) {
            this.score.incrementSessionTime(tickTime);
            this.entityManager.updateEntities(tickTime, characterDirection);

            if (entityManager.getCharacter().getHealth() <= 0) {
                this.isGameOver = true;
            }
        }
    }

    @Override
    public Dimension getVisualSize() {
        return VISUAL_SIZE;
    }

    @Override
    public Character getCharacter() {
        return this.entityManager.getCharacter();
    }

    @Override
    public List<Enemy> getEnemies() {
        return this.entityManager.getEnemies();
    }

    @Override
    public List<Attack> getAttacks() {
        return this.entityManager.getAttacks();
    }

    @Override
    public List<Weapon> getWeapons() {
        return this.entityManager.getWeapons();
    }

    @Override
    public List<Collectible> getCollectibles() {
        return this.entityManager.getCollectibles();
    }

    @Override
    public int getPlayerLevel() {
        return this.entityManager.getCharacter().getLevel();
    }

    @Override
    public double getPlayerLevelPercentage() {
        return this.entityManager.getCharacter().getLevelPercentage();
    }

    @Override
    public int getKillCounter() {
        return this.score.getKillCounter();
    }

    @Override
    public int getCoinCounter() {
        return this.entityManager.getCharacter().getCoinCounter();
    }

    @Override
    public List<UnlockableCharacter> getChoosableCharacters() {
        List<UnlockableCharacter> unlockedCharacters = this.saveManager.getCurrentSave()
                .getUnlockedCharacters().stream()
                .map(id -> DataLoader.getInstance().getCharacterLoader().get(id).get())
                .toList();
        return unlockedCharacters;
    }

    @Override
    public List<UnlockableCharacter> getLockedCharacters() {
        List<UnlockableCharacter> unlockedCharacters = this.getChoosableCharacters();
        List<UnlockableCharacter> unlockableCharacters = DataLoader.getInstance().getCharacterLoader().getAll();

        List<String> unlockedIds = unlockedCharacters.stream()
                .map(UnlockableCharacter::getId)
                .toList();

        List<UnlockableCharacter> lockedCharacters = unlockableCharacters.stream()
                .filter(c -> !unlockedIds.contains(c.getId()))
                .toList();
        return List.copyOf(lockedCharacters);
    }

    @Override
    public boolean buyCharacter(String selectedCharacter) {
        return this.shopManager.buyCharacter(selectedCharacter);
    }

    @Override
    public List<UnlockablePowerup> getUnlockablePowerups() {
        List<UnlockablePowerup> unlockablePowerups = DataLoader.getInstance().getPowerupLoader().getAll();
        Map<String, Integer> unlockedPowerups = this.saveManager.getCurrentSave().getUnlockedPowerups();

        List<UnlockablePowerup> levelAdjustedPowerups = unlockablePowerups.stream()
                .peek(p -> p.setCurrentLevel(unlockedPowerups.getOrDefault(p.getId(), 0)))
                .toList();
        return levelAdjustedPowerups;
    }

    private Stats applyBuffs(Stats baseStats) {
        Stats modifiedStats = new Stats(baseStats);
        Map<String, Integer> unlockedPowerups = saveManager.getCurrentSave().getUnlockedPowerups();

        for (Map.Entry<String, Integer> entry : unlockedPowerups.entrySet()) {
            String powerupID = entry.getKey();

            DataLoader.getInstance().getPowerupLoader().get(powerupID).ifPresent(unlockablePowerup -> {
                modifiedStats.multiplyStat(unlockablePowerup.getStatToModify(), unlockablePowerup.getMultiplier());
            });
        }

        return modifiedStats;
    }

    @Override
    public List<String> getSaveNames() {
        return this.saveManager.getSavesNames();
    }

    @Override
    public void loadSave(String selectedSave) {
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
        return this.entityManager.getCharacter().hasJustLevelledUp();
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
        final List<Unlockable> allItems = new LinkedList<>();
        allItems.addAll(DataLoader.getInstance().getCharacterLoader().getAll());
        allItems.addAll(DataLoader.getInstance().getPowerupLoader().getAll());
        return allItems;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public void levelUpWeapon(String selectedWeapon) {
        this.entityManager.levelUpWeapon(selectedWeapon);
    }

    @Override
    public void createNewSave() {
        this.saveManager.createNewSave();
    }

    @Override
    public boolean buyPowerup(String selectedPowerup) {
        return this.shopManager.buyPowerup(selectedPowerup);
    }
}
