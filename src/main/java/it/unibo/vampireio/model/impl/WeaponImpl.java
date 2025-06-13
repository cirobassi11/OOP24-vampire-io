package it.unibo.vampireio.model.impl;

import it.unibo.vampireio.model.api.Attack;
import it.unibo.vampireio.model.api.Weapon;
import it.unibo.vampireio.model.impl.attacks.AbstractAttackFactory;
import it.unibo.vampireio.model.manager.EntityManager;

/**
 * Represents a weapon in the game, capable of spawning attacks at specified
 * intervals.
 * Implements the Weapon interface.
 */
public final class WeaponImpl implements Weapon {
    private final String id;
    private final int projectilePerCooldown;
    private long cooldown;
    private long timeSinceLastAttack;
    private int currentLevel;
    private final AbstractAttackFactory attackFactory;
    private final EntityManager entityManager;

    /**
     * Constructs a WeaponImpl instance.
     *
     * @param entityManager         the EntityManager to manage attacks
     * @param id                    the unique identifier for the weapon
     * @param cooldown              the cooldown time in milliseconds between
     *                              attacks
     * @param projectilePerCooldown the number of projectiles spawned per cooldown
     * @param attackFactory         the factory to create attacks
     */
    public WeaponImpl(
            final EntityManager entityManager,
            final String id,
            final long cooldown,
            final int projectilePerCooldown,
            final AbstractAttackFactory attackFactory) {
        this.entityManager = entityManager;
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
        this.entityManager.addAttack(attack);
    }

    @Override
    public void levelUp() {
        this.currentLevel++;
        this.attackFactory.increaseLevel();
    }
}
