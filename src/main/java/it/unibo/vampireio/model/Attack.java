package it.unibo.vampireio.model;

public interface Attack extends Collidable, Movable {
    void execute(long tickTime);

    boolean isExpired();
}