package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

/**
 * Represents an enemy in the game.
 * An enemy is a living entity that can deal damage to characters upon
 * collision.
 */
public final class Enemy extends AbstractLivingEntity {

    private final int damage;

    /**
     * Constructs an Enemy instance with the specified parameters.
     *
     * @param id        the unique identifier of the enemy
     * @param position  the initial position of the enemy
     * @param radius    the radius of the enemy
     * @param direction the initial direction of the enemy
     * @param speed     the speed of the enemy
     * @param maxHealth the maximum health of the enemy
     * @param damage    the damage dealt by the enemy upon collision
     */
    public Enemy(final String id, final Point2D.Double position, final double radius, final Point2D.Double direction,
            final double speed, final int maxHealth, final int damage) {
        super(id, position, radius, direction, speed, maxHealth);
        this.damage = damage;
    }

    @Override
    public void onCollision(final Collidable collidable) {
        if (collidable instanceof Character) {
            final Character character = (Character) collidable;
            character.dealDamage(this.damage);
            character.setGettingAttacked(true);
        }
    }

    /**
     * Returns the damage dealt by the enemy upon collision.
     *
     * @return the damage dealt by the enemy
     */
    public int getDamage() {
        return this.damage;
    }
}
