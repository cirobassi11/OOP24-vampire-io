package it.unibo.vampireio.model;

public class WeaponData implements Identifiable {
    private String id;
    private String name;
    private String description;
    private long defaultCooldown;
    private int defaultAttacksPerCooldown;

    public WeaponData(
        final String id, 
        final String name, 
        final String description, 
        final long defaultCooldown, 
        final int defaultAttacksPerCooldown) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.defaultCooldown = defaultCooldown;
            this.defaultAttacksPerCooldown = defaultAttacksPerCooldown;
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

    public long getDefaultCooldown() {
        return this.defaultCooldown;
    }

    public int getDefaultAttacksPerCooldown() {
        return this.defaultAttacksPerCooldown;
    }
}
