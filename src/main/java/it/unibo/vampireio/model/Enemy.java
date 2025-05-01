package it.unibo.vampireio.model;

import java.awt.Shape;
import java.awt.geom.Point2D;

public class Enemy extends LivingEntity {

    private int damage;

    public Enemy(String id, Point2D.Double position, Shape hitbox, Point2D.Double direction, double speed, int maxHealth, int damage) {
        super(id, position, hitbox, direction, speed, maxHealth);
        this.damage = damage;
    }

    @Override
    public void onCollision(Collidable collidable) {
        if (collidable instanceof Character) {
            Character character = (Character) collidable;
            character.dealDamage(this.damage);
            character.setGettingAttacked(true);
        }
    }

    public int getDamage() {
        return this.damage;
    }
}
