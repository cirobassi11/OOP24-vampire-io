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
    private List<PositionableData> projectiles;
    private List<PositionableData> areaAttacks;
    private List<PositionableData> collectibles;

    public GameData(VisibleMapSizeData VisibleMapSize, long elapsedTime, int level, double levelPercentage, int killCounter, int coinCounter, LivingEntityData character, List<LivingEntityData> enemies, List<PositionableData> projectiles, List<PositionableData> areaAttacks, List<PositionableData> collectibles) {
        this.VisibleMapSize = VisibleMapSize;
        this.elapsedTime = elapsedTime;
        this.level = level;
        this.levelPercentage = levelPercentage;
        this.killCounter = killCounter;
        this.coinCounter = coinCounter;
        this.character = character;
        this.enemies = enemies;
        this.projectiles = projectiles;
        this.areaAttacks = areaAttacks;
        this.collectibles = collectibles;
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
        return this.enemies;
    }

    public List<PositionableData> getProjectilesData() {
        return this.projectiles;
    }

    public List<PositionableData> getAreaAttacksData() {
        return this.areaAttacks;
    }

    public List<PositionableData> getCollectiblesData() {
        return this.collectibles;
    }
}