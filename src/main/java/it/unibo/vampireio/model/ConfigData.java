package it.unibo.vampireio.model;

public class ConfigData implements Identifiable {
    private String defaultCharacterId;
    private int weaponSlots;
    
    public ConfigData(String defaultCharacterId, int weaponSlots) {
        this.defaultCharacterId = defaultCharacterId;
        this.weaponSlots = weaponSlots;
    }

    @Override
    public String getId() {
        return "";
    }

    public String getDefaultCharacterId() {
        return this.defaultCharacterId;
    }

    public int getWeaponSlots() {
        return this.weaponSlots;
    }
}
