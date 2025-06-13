package it.unibo.vampireio.model.manager;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import it.unibo.vampireio.model.api.Save;
import it.unibo.vampireio.model.api.Unlockable;
import it.unibo.vampireio.model.data.DataLoader;
import it.unibo.vampireio.model.impl.UnlockableCharacter;
import it.unibo.vampireio.model.impl.UnlockablePowerUp;

/**
 * ShopManager is responsible for managing the shop functionalities in the game.
 * It allows players to buy characters and power-ups, and provides methods to
 * retrieve
 * available characters and power-ups.
 */
public final class ShopManager {
    private final SaveManager saveManager;

    /**
     * Constructs a ShopManager with the specified SaveManager.
     *
     * @param saveManager the SaveManager to be used by this ShopManager
     */
    public ShopManager(final SaveManager saveManager) {
        this.saveManager = saveManager;
    }

    /**
     * Buys a character if the player can afford it and it is available.
     *
     * @param characterID the ID of the character to buy
     * @return true if the purchase was successful, false otherwise
     */
    public boolean buyCharacter(final String characterID) {
        final Save currentSave = saveManager.getCurrentSave();
        final UnlockableCharacter character = this.getLockedCharacters().stream()
                .filter(c -> c.getId().equals(characterID))
                .findFirst()
                .orElse(null);
        if (character == null || !canAfford(currentSave, character.getPrice())) {
            return false;
        }

        return purchaseCharacter(currentSave, character);
    }

    /**
     * Buys a power-up if the player can afford it and it is available.
     *
     * @param powerUpID the ID of the power-up to buy
     * @return true if the purchase was successful, false otherwise
     */
    public boolean buyPowerUp(final String powerUpID) {
        final Save currentSave = saveManager.getCurrentSave();
        final UnlockablePowerUp powerUp = DataLoader.getInstance().getPowerUpLoader().get(powerUpID).orElse(null);

        if (powerUp == null || !canAfford(currentSave, powerUp.getPrice())) {
            return false;
        }

        return purchasePowerUp(currentSave, powerUp);
    }

    private boolean canAfford(final Save save, final int price) {
        return save.getMoneyAmount() >= price;
    }

    private boolean purchaseCharacter(final Save save, final UnlockableCharacter character) {
        save.incrementMoneyAmount(-character.getPrice());
        save.addUnlockedCharacter(character);
        saveManager.saveCurrentSave();
        return true;
    }

    private boolean purchasePowerUp(final Save save, final UnlockablePowerUp powerUp) {
        if (!powerUp.enhance()) {
            return false;
        }
        save.incrementMoneyAmount(-powerUp.getPrice());
        save.enhancePowerUp(powerUp);
        saveManager.saveCurrentSave();
        return true;
    }

    /**
     * Retrieves a list of characters.
     *
     * @return a list of UnlockableCharacter objects representing the available
     *         characters
     */
    public List<UnlockableCharacter> getChoosableCharacters() {
        return this.saveManager.getCurrentSave()
                .getUnlockedCharacters().stream()
                .map(id -> DataLoader.getInstance().getCharacterLoader().get(id).get())
                .toList();
    }

    /**
     * Retrieves a list of characters that are locked and can be unlocked.
     *
     * @return a list of UnlockableCharacter objects representing the locked
     *         characters
     */
    public List<UnlockableCharacter> getLockedCharacters() {
        final List<UnlockableCharacter> unlockedCharacters = this.getChoosableCharacters();
        final List<UnlockableCharacter> unlockableCharacters = DataLoader.getInstance().getCharacterLoader().getAll();

        final List<String> unlockedIds = unlockedCharacters.stream()
                .map(UnlockableCharacter::getId)
                .toList();

        final List<UnlockableCharacter> lockedCharacters = unlockableCharacters.stream()
                .filter(c -> !unlockedIds.contains(c.getId()))
                .toList();
        return List.copyOf(lockedCharacters);
    }

    /**
     * Retrieves a list of unlockable power-ups.
     *
     * @return a list of UnlockablePowerUp objects representing the available
     *         power-ups
     */
    public List<UnlockablePowerUp> getUnlockablePowerUps() {
        final List<UnlockablePowerUp> unlockablePowerUps = DataLoader.getInstance().getPowerUpLoader().getAll();
        final Map<String, Integer> unlockedPowerUps = this.saveManager.getCurrentSave().getUnlockedPowerUps();
        return unlockablePowerUps.stream()
                .peek(p -> p.setCurrentLevel(unlockedPowerUps.getOrDefault(p.getId(), 0)))
                .toList();
    }

    /**
     * Retrieves all unlockable items, including characters and power-ups.
     *
     * @return a collection of Unlockable objects representing all available items
     */
    public Collection<Unlockable> getAllItems() {
        final List<Unlockable> allItems = new LinkedList<>();
        allItems.addAll(DataLoader.getInstance().getCharacterLoader().getAll());
        allItems.addAll(DataLoader.getInstance().getPowerUpLoader().getAll());
        return allItems;
    }
}
