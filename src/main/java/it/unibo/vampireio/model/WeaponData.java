package it.unibo.vampireio.model;

public class WeaponData implements Identifiable {
    private String id;
    private String name;
    private String description;
    private String projectileId;
    private double defaultCooldown;
    private int projectilePerCooldown;

    public WeaponData(String id, String name, String description, String projectileId, double defaultCooldown, int projectilePerCooldown) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectileId = projectileId;
        this.defaultCooldown = defaultCooldown;
        this.projectilePerCooldown = projectilePerCooldown;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }

    public String getProjectileId() {
        return this.projectileId;
    }

    public double getDefaultCooldown() {
        return this.defaultCooldown;
    }

    public int getProjectilePerCooldown() {
        return this.projectilePerCooldown;
    }
}
