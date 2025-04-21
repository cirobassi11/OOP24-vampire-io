package it.unibo.vampireio.controller;

import java.util.List;

public class GameData {
    private VisibleMapSizeData VisibleMapSize;
    private long elapsedTime;
    private int level;
    private int levelPercentage;
    private LivingEntityData character;
    private List<LivingEntityData> enemies;
    private List<PositionableData> projectiles;
    private List<PositionableData> areaAttacks;
    private List<PositionableData> collectibles;

    public GameData(VisibleMapSizeData VisibleMapSize, long elapsedTime, int level, int levelPercentage, LivingEntityData character, List<LivingEntityData> enemies, List<PositionableData> projectiles, List<PositionableData> areaAttacks, List<PositionableData> collectibles) {
        this.VisibleMapSize = VisibleMapSize;
        this.elapsedTime = elapsedTime;
        this.level = level;
        this.levelPercentage = levelPercentage;
        this.character = character;
        this.enemies = enemies;
        this.projectiles = projectiles;
        this.areaAttacks = areaAttacks;
        this.collectibles = collectibles;
    }

    public long getElapsedTime() {
        return this.elapsedTime;
    }

    public int getLevel() {
        return this.level;
    }

    public int getLevelPercentage() {
        return this.levelPercentage;
    }

    public VisibleMapSizeData getVisibleMapSizeData() {
        return this.VisibleMapSize;
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