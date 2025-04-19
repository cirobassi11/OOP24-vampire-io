package it.unibo.vampireio.model;

public class DataLoader {

    private final CharacterDataLoader characterLoader;
    private final PowerUpDataLoader powerUpLoader;

    public DataLoader() {
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