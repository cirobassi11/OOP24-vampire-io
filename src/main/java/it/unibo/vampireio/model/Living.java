package it.unibo.vampireio.model;

public interface Living {
    int getHealth();
    int getMaxHealth();
    void setMaxHealth(int maxHealth);
    void dealDamage (int damage);
    void heal(int heal);
}
