package it.unibo.vampireio.model;

public interface Movable extends Placeable {
    void move(float dx, float dy);
    void update();
    float getSpeed();
}
