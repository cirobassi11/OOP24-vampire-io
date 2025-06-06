package it.unibo.vampireio.model;

import java.util.List;
import java.util.Objects;

public class LevelUpManager {
    private final EntityManager entityManager;
    private final WeaponRandomizer weaponRandomizer;
    private static final int LEVELUP_CHOICES = 3;

    public LevelUpManager(final EntityManager entityManager, final WeaponRandomizer weaponRandomizer) {
        this.entityManager = entityManager;
        this.weaponRandomizer = weaponRandomizer;
    }

    public List<WeaponData> getRandomLevelUpWeapons() {
        return weaponRandomizer.getRandomWeapons(LEVELUP_CHOICES).stream()
                .map(weaponID -> DataLoader.getInstance().getWeaponLoader().get(weaponID).orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    public void levelUpWeapon(Character character, String selectedWeapon) {
        Weapon weapon = findWeaponById(character, selectedWeapon);

        if (weapon != null) {
            weapon.levelUp();
        } else {
            addNewWeapon(character, selectedWeapon);
        }
    }

    Weapon findWeaponById(Character character, String weaponId) {
        return character.getWeapons().stream()
                .filter(weapon -> weapon.getId().equals(weaponId))
                .findFirst()
                .orElse(null);
    }

    private void addNewWeapon(Character character, String weaponId) {
        WeaponData weaponData = DataLoader.getInstance().getWeaponLoader().get(weaponId).orElse(null);
        if (weaponData != null) {
            Weapon newWeapon = createWeapon(weaponData);
            character.addWeapon(newWeapon);
        }
    }

    private Weapon createWeapon(WeaponData data) {
        return new WeaponImpl(
                this.entityManager,
                data.getId(),
                data.getDefaultCooldown(),
                data.getDefaultAttacksPerCooldown(),
                this.entityManager.getAttackFactory(data.getId())
        );
    }
}