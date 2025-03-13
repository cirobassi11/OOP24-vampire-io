package it.unibo.vampireio.controller;

import java.util.List;

public class DTO {
    private VisualSizeDTO visualSize;
    private PositionableDTO character;
    private List<PositionableDTO> enemies;
    private List<PositionableDTO> projectiles;
    private List<PositionableDTO> areaAttacks;
    private List<PositionableDTO> collectibles;

    public DTO(VisualSizeDTO visualSize, PositionableDTO character, List<PositionableDTO> enemies, List<PositionableDTO> projectiles, List<PositionableDTO> areaAttacks, List<PositionableDTO> collectibles) {
        this.visualSize = visualSize;
        this.character = character;
        this.enemies = enemies;
        this.projectiles = projectiles;
        this.areaAttacks = areaAttacks;
        this.collectibles = collectibles;
    }

    public VisualSizeDTO getVisualSizeData() {
        return this.visualSize;
    }

    public PositionableDTO getCharacterData() {
        return this.character;
    }

    public List<PositionableDTO> getEnemiesData() {
        return this.enemies;
    }

    public List<PositionableDTO> getProjectilesData() {
        return this.projectiles;
    }

    public List<PositionableDTO> getAreaAttacksData() {
        return this.areaAttacks;
    }

    public List<PositionableDTO> getCollectiblesData() {
        return this.collectibles;
    }
}