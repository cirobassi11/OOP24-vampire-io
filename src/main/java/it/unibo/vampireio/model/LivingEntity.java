package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public abstract class LivingEntity extends Entity implements Movable {
    private int health;
    private int maxHealth;
    private double speed;
    private Point2D.Double direction;

    protected LivingEntity(Point2D.Double position, int maxHealth, int speed, Point2D.Double direction) {
        super(position);
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.speed = speed;
        this.direction = direction;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void dealDamage(int damage) {
        this.setHealth(this.getHealth() - ((damage < 0) ? 0 : damage));
        if(this.health <= 0) {
            //TODO --------------------- DEATHHHHHHHH/GAMEOVER
        }
    }

    public void heal(int heal) {
        this.setHealth(Math.min(this.getHealth() + ((heal < 0) ? 0 : heal), this.getMaxHealth()));
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public Point2D.Double getDirection() {
        return this.direction;
    }

    private void setHealth(int health) {
        this.health = health;
    }
}