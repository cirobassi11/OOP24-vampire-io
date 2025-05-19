package it.unibo.vampireio.model;

public class WeaponImpl implements Weapon {
    private final GameWorld gameWorld;
    private final String id;
    private double cooldown;
    private int projectilePerCooldown;
    private double timeSinceLastAttack;
    private final AttackFactory attackFactory;
    
    public WeaponImpl(GameWorld gameWorld, String id, double cooldown, int projectilePerCooldown, AttackFactory attackFactory) {
        this.gameWorld = gameWorld;
        this.id = id;
        this.cooldown = cooldown;
        this.projectilePerCooldown = projectilePerCooldown;
        this.timeSinceLastAttack = 0;
        this.attackFactory = attackFactory;
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
        Attack attack = attackFactory.createAttack();
        
        if (attack instanceof ProjectileAttack) {
            gameWorld.addProjectileAttack((ProjectileAttack) attack);
        } else if (attack instanceof AreaAttack) {
            gameWorld.addAreaAttack((AreaAttack) attack);
        }
    }
}