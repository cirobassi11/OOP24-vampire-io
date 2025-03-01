package it.unibo.vampireio.model;

public interface Attack extends Collidable {
    int getDamage();
    String getType();
}