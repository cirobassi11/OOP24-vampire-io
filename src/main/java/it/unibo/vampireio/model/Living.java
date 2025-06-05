package it.unibo.vampireio.model;

public interface Living extends Movable {
    double getHealth();
    double getMaxHealth();
    void setMaxHealth(double maxHealth);
    void dealDamage(double damage);
    void heal(double heal);
    boolean isGettingAttacked();
    void setGettingAttacked(boolean isGettingAttacked);
}
