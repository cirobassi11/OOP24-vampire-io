package it.unibo.vampireio.model;

public class DataManager {

    private final CharacterDataLoader characterLoader;
    private final PowerUpDataLoader powerUpLoader;

    public DataManager() {
        this.characterLoader = new CharacterDataLoader();
        this.powerUpLoader = new PowerUpDataLoader();
    }

    public CharacterDataLoader getCharacterLoader() {
        return characterLoader;
    }

    public PowerUpDataLoader getPowerUpLoader() {
        return powerUpLoader;
    }
}