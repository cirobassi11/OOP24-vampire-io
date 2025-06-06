package it.unibo.vampireio.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShopManager {
    private final SaveManager saveManager;

    public ShopManager(SaveManager saveManager) {
        this.saveManager = saveManager;
    }

    public boolean buyCharacter(String characterId) {
        Save currentSave = saveManager.getCurrentSave();
        UnlockableCharacter character = this.getLockedCharacters().stream()
                .filter(c -> c.getId().equals(characterId))
                .findFirst()
                .orElse(null);
        if (character == null || !canAfford(currentSave, character.getPrice())) {
            return false;
        }

        return purchaseCharacter(currentSave, character);
    }

    public boolean buyPowerUp(String powerUpID) {
        Save currentSave = saveManager.getCurrentSave();
        UnlockablePowerUp powerUp = DataLoader.getInstance().getPowerUpLoader().get(powerUpID).orElse(null);

        if (powerUp == null || !canAfford(currentSave, powerUp.getPrice())) {
            return false;
        }

        return purchasePowerUp(currentSave, powerUp);
    }

    private boolean canAfford(Save save, int price) {
        return save.getMoneyAmount() >= price;
    }

    private boolean purchaseCharacter(Save save, UnlockableCharacter character) {
        save.incrementMoneyAmount(-character.getPrice());
        save.addUnlockedCharacter(character);
        saveManager.saveCurrentSave();
        return true;
    }

    private boolean purchasePowerUp(Save save, UnlockablePowerUp powerUp) {
        if (!powerUp.enhance()) {
            return false;
        }
        save.incrementMoneyAmount(-powerUp.getPrice());
        save.enhancePowerUp(powerUp);
        saveManager.saveCurrentSave();
        return true;
    }

    public List<UnlockableCharacter> getChoosableCharacters() {
        List<UnlockableCharacter> unlockedCharacters = this.saveManager.getCurrentSave()
                .getUnlockedCharacters().stream()
                .map(id -> DataLoader.getInstance().getCharacterLoader().get(id).get())
                .toList();
        return unlockedCharacters;
    }

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

    public List<UnlockablePowerUp> getUnlockablePowerUps() {
        List<UnlockablePowerUp> unlockablePowerUps = DataLoader.getInstance().getPowerUpLoader().getAll();
        Map<String, Integer> unlockedPowerUps = this.saveManager.getCurrentSave().getUnlockedPowerUps();

        List<UnlockablePowerUp> levelAdjustedPowerUps = unlockablePowerUps.stream()
                .peek(p -> p.setCurrentLevel(unlockedPowerUps.getOrDefault(p.getId(), 0)))
                .toList();
        return levelAdjustedPowerUps;
    }

    public Collection<Unlockable> getAllItems() {
        final List<Unlockable> allItems = new LinkedList<>();
        allItems.addAll(DataLoader.getInstance().getCharacterLoader().getAll());
        allItems.addAll(DataLoader.getInstance().getPowerUpLoader().getAll());
        return allItems;
    }
}