package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
import java.util.Map;

public interface GameModel {
    void update();
    void addEnemy(Enemy enemy);
    void removeEnemy(Enemy enemy);
    void addCollectible(Collectible collectible);
    void removeCollectible(Collectible collectible);
    Map<String, Point2D.Double> getPositionables();
}
