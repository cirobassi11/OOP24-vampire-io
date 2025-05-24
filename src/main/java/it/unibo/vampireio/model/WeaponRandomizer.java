package it.unibo.vampireio.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WeaponRandomizer {
    private List<String> weaponsList;
    
    public WeaponRandomizer(List<String> weaponsList){
        this.weaponsList = weaponsList;
    }

    private List<String> randomize(int numberOfWeapons) {
        if (weaponsList.size() < numberOfWeapons) {
            return null;
        }

        return weaponsList.stream()
            .sorted((a, b) -> Math.random() < 0.5 ? -1 : 1) // random shuffle
            .limit(numberOfWeapons)
            .collect(Collectors.toList());
    }

    public List<String> getRandomWeapons(int numberOfWeapons){
        return this.randomize(numberOfWeapons);
    }
}
