package it.unibo.vampireio.model;

public interface GameModel {
    void addPositionable(Positionable positionable);
    void removePositionable(Positionable positionable);
    void update();
}
