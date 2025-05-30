package it.unibo.vampireio.model;

public class WeaponImpl implements Weapon {
    private final GameWorld gameWorld;
    private final String id;
    private long cooldown;
    private int projectilePerCooldown;
    private long timeSinceLastAttack;
    private int currentLevel;
    private final AbstractAttackFactory attackFactory;

    public WeaponImpl(
        final GameWorld gameWorld,
        final String id,
        final long cooldown, 
        final int projectilePerCooldown, 
        final AbstractAttackFactory attackFactory) {
            this.gameWorld = gameWorld;
            this.id = id;
            this.cooldown = cooldown;
            this.projectilePerCooldown = projectilePerCooldown;
            this.timeSinceLastAttack = 0;
            this.currentLevel = 1;
            this.attackFactory = attackFactory;
    }

    @Override
    public void update(final long tickTime) {
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

    @Override
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    @Override
    public void multiplyCooldown(final double multiplier) {
        this.cooldown *= multiplier;
    }

    private void spawnAttack() {
        final Attack attack = attackFactory.createAttack();
        this.gameWorld.addAttack(attack);
    }

    @Override
    public void levelUp() {
        this.currentLevel++;
        this.attackFactory.increaseLevel();
    }
}
