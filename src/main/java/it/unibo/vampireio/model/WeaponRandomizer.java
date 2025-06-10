package it.unibo.vampireio.model;

import java.util.List;
import java.util.stream.Collectors;

final class WeaponRandomizer {
    private final List<String> weaponsList;
    private final Character character;

    /**
     * Constructor for WeaponRandomizer.
     *
     * @param weaponsList the list of available weapons
     * @param character the character for which weapons are being randomized
     */
    WeaponRandomizer(final List<String> weaponsList, final Character character) {
        this.weaponsList = weaponsList;
        this.character = character;
    }

    private List<String> randomize(final int numberOfWeapons) {
        if (this.weaponsList.size() < numberOfWeapons) {
            return List.of();
        }

        return this.weaponsList.stream()
            .sorted((a, b) -> Math.random() < 0.5 ? -1 : 1)
            .limit(numberOfWeapons)
            .collect(Collectors.toList());
    }

    /**
     * Returns a list of random weapons for the character.
     *
     * @param numberOfWeapons the number of weapons to return
     * @return a list of weapon IDs
     */
    List<String> getRandomWeapons(final int numberOfWeapons) {
        if (!this.character.hasMaxWeapons()) {
            return this.randomize(numberOfWeapons);
        }
        return character.getWeapons().stream()
        .sorted((a, b) -> Math.random() < 0.5 ? -1 : 1)
        .limit(numberOfWeapons)
        .map(Identifiable::getId)
        .collect(Collectors.toList());
    }
}
