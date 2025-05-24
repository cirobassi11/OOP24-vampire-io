package it.unibo.vampireio.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WeaponRandomizer {
    private List<String> weaponsList;
    private Character character;
    
    public WeaponRandomizer(List<String> weaponsList, Character character) {
        this.weaponsList = weaponsList;
        this.character = character;
    }

    private List<String> randomize(int numberOfWeapons) {
        if (this.weaponsList.size() < numberOfWeapons) {
            return null;
        }

        return this.weaponsList.stream()
            .sorted((a, b) -> Math.random() < 0.5 ? -1 : 1)
            .limit(numberOfWeapons)
            .collect(Collectors.toList());
    }

    public List<String> getRandomWeapons(int numberOfWeapons){
        if(!this.character.hasMaxWeapons()) {
            return this.randomize(numberOfWeapons);
        }
        return character.getWeapons().stream()
        .sorted((a, b) -> Math.random() < 0.5 ? -1 : 1)
        .limit(numberOfWeapons)
        .map(weapon -> weapon.getId())
        .collect(Collectors.toList());
    }
}
