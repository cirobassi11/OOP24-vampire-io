package it.unibo.vampireio.model;

public class WeaponImpl implements Weapon {
    private final GameWorld gameWorld;
    private final String id;
    private double cooldown;
    private int projectilePerCooldown;
    private double timeSinceLastAttack;
    private int currentLevel;
    private final AttackFactory attackFactory;
    
    public WeaponImpl(GameWorld gameWorld, String id, double cooldown, int projectilePerCooldown, AttackFactory attackFactory) {
        this.gameWorld = gameWorld;
        this.id = id;
        this.cooldown = cooldown;
        this.projectilePerCooldown = projectilePerCooldown;
        this.timeSinceLastAttack = 0;
        this.currentLevel = 1;
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

    public int getCurrentLevel() {
        return this.currentLevel;
    }
    
    public void multiplyCooldown(double multiplier) {
        this.cooldown *= multiplier;
    }
    
    private void spawnAttack() {
        Attack attack = attackFactory.createAttack();
        
        gameWorld.addAttack(attack);
    }
}