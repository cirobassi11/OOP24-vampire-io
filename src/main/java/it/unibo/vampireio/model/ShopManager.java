package it.unibo.vampireio.model;

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

    public boolean buyPowerup(String powerupId) {
        Save currentSave = saveManager.getCurrentSave();
        UnlockablePowerup powerup = DataLoader.getInstance().getPowerupLoader().get(powerupId).orElse(null);

        if (powerup == null || !canAfford(currentSave, powerup.getPrice())) {
            return false;
        }

        return purchasePowerup(currentSave, powerup);
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

    private boolean purchasePowerup(Save save, UnlockablePowerup powerup) {
        if (!powerup.enhance()) {
            return false;
        }
        save.incrementMoneyAmount(-powerup.getPrice());
        save.enhancePowerup(powerup);
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

    public List<UnlockablePowerup> getUnlockablePowerups() {
        List<UnlockablePowerup> unlockablePowerups = DataLoader.getInstance().getPowerupLoader().getAll();
        Map<String, Integer> unlockedPowerups = this.saveManager.getCurrentSave().getUnlockedPowerups();

        List<UnlockablePowerup> levelAdjustedPowerups = unlockablePowerups.stream()
                .peek(p -> p.setCurrentLevel(unlockedPowerups.getOrDefault(p.getId(), 0)))
                .toList();
        return levelAdjustedPowerups;
    }
}