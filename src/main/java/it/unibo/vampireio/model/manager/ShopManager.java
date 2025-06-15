package it.unibo.vampireio.model.manager;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
    @SuppressFBWarnings(
        value = "EI2", 
        justification = "The SaveManager instance is intentionally shared and is used in a controlled way within ShopManager."
    )
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
        final UnlockableCharacter lockedCharacter = this.getLockedCharacters().stream()
                .filter(character -> character.getId().equals(characterID))
                .findFirst()
                .orElse(null);
        if (lockedCharacter == null || !canAfford(lockedCharacter.getPrice())) {
            return false;
        }

        return purchaseCharacter(lockedCharacter);
    }

    /**
     * Buys a power-up if the player can afford it and it is available.
     *
     * @param powerUpID the ID of the power-up to buy
     * @return true if the purchase was successful, false otherwise
     */
    public boolean buyPowerUp(final String powerUpID) {
        final UnlockablePowerUp powerUp = DataLoader.getInstance().getPowerUpLoader().get(powerUpID).orElse(null);

        if (powerUp == null || !canAfford(powerUp.getPrice())) {
            return false;
        }

        return purchasePowerUp(powerUp);
    }

    private boolean canAfford(final int price) {
        return this.saveManager.getMoneyAmount() >= price;
    }

    private boolean purchaseCharacter(final UnlockableCharacter character) {
        this.saveManager.incrementMoneyAmount(-character.getPrice());
        this.saveManager.addUnlockedCharacter(character);
        return true;
    }

    private boolean purchasePowerUp(final UnlockablePowerUp powerUp) {
        if (!powerUp.enhance()) {
            return false;
        }
        this.saveManager.incrementMoneyAmount(-powerUp.getPrice());
        this.saveManager.enhancePowerUp(powerUp);
        return true;
    }

    /**
     * Retrieves a list of characters.
     *
     * @return a list of UnlockableCharacter objects representing the available
     *         characters
     */
    public List<UnlockableCharacter> getChoosableCharacters() {
        return this.saveManager.getUnlockedCharacters().stream()
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
                .filter(character -> !unlockedIds.contains(character.getId()))
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
        final Map<String, Integer> unlockedPowerUps = this.saveManager.getUnlockedPowerUps();
        for (final UnlockablePowerUp powerUp : unlockablePowerUps) {
            final int level = unlockedPowerUps.getOrDefault(powerUp.getId(), 0);
            powerUp.setCurrentLevel(level);
        }
        return unlockablePowerUps;
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
