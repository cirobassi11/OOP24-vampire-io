package it.unibo.vampireio.model;

public interface Entity extends Placeable {
    int getHealth();
    void setHealth(int health);
    double getSpeed();
    void setSpeed(double speed);
    Direction getDirection();
    void setDirection(Direction direction);
    void move(Direction direction);
    void attack(Entity entity);
    void takeDamage(int damage);
    void die();
}
