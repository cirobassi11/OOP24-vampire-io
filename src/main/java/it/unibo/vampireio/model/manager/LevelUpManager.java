package it.unibo.vampireio.model.manager;

import java.util.List;
import java.util.Optional;
import it.unibo.vampireio.model.data.WeaponData;
import it.unibo.vampireio.model.impl.WeaponImpl;
import it.unibo.vampireio.model.api.Weapon;
import it.unibo.vampireio.model.data.DataLoader;
import it.unibo.vampireio.model.impl.Character;

/**
 * * Manages the level-up process for characters, including weapon upgrades.
 * Provides methods to retrieve random weapons for level-up choices and to
 * handle
 * the leveling up of existing weapons or adding new ones.
 */
class LevelUpManager {
    private static final int LEVELUP_CHOICES = 3;
    private final EntityManager entityManager;
    private final WeaponRandomizer weaponRandomizer;

    /**
     * Constructs a LevelUpManager with the specified EntityManager and
     * WeaponRandomizer.
     *
     * @param entityManager    the EntityManager to manage entities
     * @param weaponRandomizer the WeaponRandomizer to provide random weapons
     */
    LevelUpManager(final EntityManager entityManager, final WeaponRandomizer weaponRandomizer) {
        this.entityManager = entityManager;
        this.weaponRandomizer = weaponRandomizer;
    }

    List<WeaponData> getRandomLevelUpWeapons() {
        return weaponRandomizer.getRandomWeapons(LEVELUP_CHOICES).stream()
                .map(weaponID -> DataLoader.getInstance().getWeaponLoader().get(weaponID))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    void levelUpWeapon(final Character character, final String selectedWeapon) {
        final Weapon weapon = findWeaponById(character, selectedWeapon);

        if (weapon != null) {
            weapon.levelUp();
        } else {
            addNewWeapon(character, selectedWeapon);
        }
    }

    Weapon findWeaponById(final Character character, final String weaponId) {
        return character.getWeapons().stream()
                .filter(weapon -> weapon.getId().equals(weaponId))
                .findFirst()
                .orElse(null);
    }

    private void addNewWeapon(final Character character, final String weaponId) {
        final WeaponData weaponData = DataLoader.getInstance().getWeaponLoader().get(weaponId).orElse(null);
        if (weaponData != null) {
            final Weapon newWeapon = createWeapon(weaponData);
            character.addWeapon(newWeapon);
        }
    }

    private Weapon createWeapon(final WeaponData data) {
        return new WeaponImpl(
                this.entityManager,
                data.getId(),
                data.getDefaultCooldown(),
                data.getDefaultAttacksPerCooldown(),
                this.entityManager.getAttackFactory(data.getId()));
    }
}
