package it.unibo.vampireio.controller;

import java.util.List;

public class GameData {
    private VisibleMapSizeData VisibleMapSize;
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

    public GameData(VisibleMapSizeData VisibleMapSize, long elapsedTime, int level, double levelPercentage,
            int killCounter, int coinCounter, LivingEntityData character, List<LivingEntityData> enemies,
            List<PositionableData> attacks, List<PositionableData> collectibles, List<ItemData> items) {
        this.VisibleMapSize = VisibleMapSize;
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

    public VisibleMapSizeData getVisibleMapSizeData() {
        return this.VisibleMapSize;
    }

    public long getElapsedTime() {
        return this.elapsedTime;
    }

    public int getLevel() {
        return this.level;
    }

    public double getLevelPercentage() {
        return this.levelPercentage;
    }

    public int getKillCounter() {
        return this.killCounter;
    }

    public int getCoinCounter() {
        return this.coinCounter;
    }

    public LivingEntityData getCharacterData() {
        return this.character;
    }

    public List<LivingEntityData> getEnemiesData() {
        return List.copyOf(this.enemies);
    }

    public List<PositionableData> getAttacksData() {
        return List.copyOf(this.attacks);
    }

    public List<PositionableData> getCollectiblesData() {
        return List.copyOf(this.collectibles);
    }

    public List<ItemData> getItemsData() {
        return List.copyOf(this.items);
    }
}