package it.unibo.vampireio.model;

public class WeaponImpl implements Weapon, Identifiable {
    private GameWorld gameWorld;
    private String id;
    private String projectileId;
    private double cooldown;
    private int projectilePerCooldown;
    private double timeSinceLastAttack;

    private Attack attack;
    
    public WeaponImpl(GameWorld gameWorld, String id, String projectileId, double cooldown, int projectilePerCooldown) {
        this.gameWorld = gameWorld;
        this.id = id;
        this.projectileId = projectileId;
        this.cooldown = cooldown;
        this.projectilePerCooldown = projectilePerCooldown;
        this.timeSinceLastAttack = 0;
        //this.attack=boh
    }

    @Override
    public void update(double tickTime) {
        this.timeSinceLastAttack += tickTime;

        if (this.timeSinceLastAttack >= this.cooldown) {
            for (int i = 0; i < this.projectilePerCooldown; i++) {
                this.spawnAttack();
            }
            this.timeSinceLastAttack = 0;
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void multiplyCooldown(double multiplier) {
        this.cooldown *= multiplier;
    }

    private void spawnAttack() {
        System.out.println("Spawning attack with id: " + this.projectileId);
    }
}