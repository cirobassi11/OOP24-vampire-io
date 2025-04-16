package it.unibo.vampireio.controller;

import java.util.List;

public class GameData {
    private long elapsedTime;
    private VisibleMapSizeData VisibleMapSize;
    private LivingEntityData character;
    private List<LivingEntityData> enemies;
    private List<PositionableData> projectiles;
    private List<PositionableData> areaAttacks;
    private List<PositionableData> collectibles;

    public GameData(long elapsedTime, VisibleMapSizeData VisibleMapSize, LivingEntityData character, List<LivingEntityData> enemies, List<PositionableData> projectiles, List<PositionableData> areaAttacks, List<PositionableData> collectibles) {
        this.elapsedTime = elapsedTime;
        this.VisibleMapSize = VisibleMapSize;
        this.character = character;
        this.enemies = enemies;
        this.projectiles = projectiles;
        this.areaAttacks = areaAttacks;
        this.collectibles = collectibles;
    }

    public long getElapsedTime() {
        return this.elapsedTime;
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