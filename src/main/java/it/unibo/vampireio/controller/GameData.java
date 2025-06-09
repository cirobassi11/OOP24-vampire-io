package it.unibo.vampireio.controller;

import java.util.List;

/**
 * Represents the game data that is sent to the view.
 * This class encapsulates all the necessary information about the game state,
 * including the visible map size, elapsed time, level, character data, enemies,
 * attacks, collectibles, and items.
 */
public final class GameData {
    private VisibleMapSizeData visibleMapSize;
    private long elapsedTime;
    private int level;
    private double levelPercentage;
    private int killCounter;
    private int coinCounter;
    private LivingEntityData character;
    private List<LivingEntityData> enemies;
    private List<PositionableData> attacks;
    private List<PositionableData> collectibles;
    private List<ItemData> items;

    /**
     * Constructs a GameData instance with the specified parameters.
     *
     * @param visibleMapSize   the visible map size data
     * @param elapsedTime       the elapsed time in milliseconds
     * @param level             the current level of the game
     * @param levelPercentage   the percentage of the level completed
     * @param killCounter       the number of kills made by the player
     * @param coinCounter       the number of coins collected by the player
     * @param character         the character data of the player
     * @param enemies           a list of enemy data in the game
     * @param attacks           a list of attack data in the game
     * @param collectibles      a list of collectible data in the game
     * @param items             a list of item data in the game
     */
    public GameData(
            final VisibleMapSizeData visibleMapSize,
            final long elapsedTime,
            final int level,
            final double levelPercentage,
            final int killCounter,
            final int coinCounter,
            final LivingEntityData character,
            final List<LivingEntityData> enemies,
            final List<PositionableData> attacks,
            final List<PositionableData> collectibles,
            final List<ItemData> items
        ) {
        this.visibleMapSize = visibleMapSize;
        this.elapsedTime = elapsedTime;
        this.level = level;
        this.levelPercentage = levelPercentage;
        this.killCounter = killCounter;
        this.coinCounter = coinCounter;
        this.character = character;
        this.enemies = List.copyOf(enemies);
        this.attacks = List.copyOf(attacks);
        this.collectibles = List.copyOf(collectibles);
        this.items = List.copyOf(items);
    }

    /**
     * Returns the visible map size data.
     *
     * @return the visible map size data
     */
    public VisibleMapSizeData getVisibleMapSizeData() {
        return this.visibleMapSize;
    }

    /**
     * Returns the elapsed time in milliseconds.
     *
     * @return the elapsed time
     */
    public long getElapsedTime() {
        return this.elapsedTime;
    }

    /**
     * Returns the current level of the game.
     *
     * @return the current level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns the percentage of the level completed.
     *
     * @return the level completion percentage
     */
    public double getLevelPercentage() {
        return this.levelPercentage;
    }

    /**
     * Returns the number of kills made by the player.
     *
     * @return the kill counter
     */
    public int getKillCounter() {
        return this.killCounter;
    }

    /**
     * Returns the number of coins collected by the player.
     *
     * @return the coin counter
     */
    public int getCoinCounter() {
        return this.coinCounter;
    }

    /**
     * Returns the character data of the player.
     *
     * @return the character data
     */
    public LivingEntityData getCharacterData() {
        return this.character;
    }

    /**
     * Returns a list of enemy data in the game.
     *
     * @return a list of enemies
     */
    public List<LivingEntityData> getEnemiesData() {
        return List.copyOf(this.enemies);
    }

    /**
     * Returns a list of attack data in the game.
     *
     * @return a list of attacks
     */
    public List<PositionableData> getAttacksData() {
        return List.copyOf(this.attacks);
    }

    /**
     * Returns a list of collectible data in the game.
     *
     * @return a list of collectibles
     */
    public List<PositionableData> getCollectiblesData() {
        return List.copyOf(this.collectibles);
    }

    /**
     * Returns a list of item data in the game.
     *
     * @return a list of items
     */
    public List<ItemData> getItemsData() {
        return List.copyOf(this.items);
    }
}
