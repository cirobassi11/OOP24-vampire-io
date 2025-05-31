package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public class Enemy extends AbstractLivingEntity {

    private final int damage;

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

    public int getDamage() {
        return this.damage;
    }
}
