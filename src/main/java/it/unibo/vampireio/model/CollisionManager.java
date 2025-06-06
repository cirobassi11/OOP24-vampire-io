package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;
import java.util.Iterator;

public class CollisionManager {

    public static void checkCharacterCollisions(
            final Character character,
            final List<Enemy> enemies,
            final List<Collectible> collectibles) {
        checkEnemyCharacterCollisions(character, enemies);
        checkCharacterCollectibleCollisions(character, collectibles);
    }

    private static void checkEnemyCharacterCollisions(Collidable character, List<Enemy> enemies) {
        for (Collidable enemy : enemies) {
            if (enemy.isColliding(character)) {
                enemy.onCollision(character);
            }
        }
    }

    private static void checkCharacterCollectibleCollisions(Character character, List<Collectible> collectibles) {
        Iterator<Collectible> it = collectibles.iterator();
        while (it.hasNext()) {
            Collectible collectible = it.next();
            if (collectible.isColliding(character)) {
                character.collect(collectible);
                it.remove();
            }
        }
    }

    static boolean checkEnemyCollisions(Enemy currentEnemy, Point2D.Double futurePosition, List<Enemy> enemies,
            Character character) {
        double currentEnemyRadius = currentEnemy.getRadius();
        for (Enemy otherEnemy : enemies) {
            if (currentEnemy != otherEnemy) {
                double combinedRadius = currentEnemyRadius + otherEnemy.getRadius();
                if (futurePosition.distance(otherEnemy.getPosition()) < combinedRadius / 2) {
                    return true;
                }
            }
        }
        double combinedRadius = currentEnemyRadius + character.getRadius();
        if (futurePosition.distance(character.getPosition()) < combinedRadius / 2) {
            return true;
        }
        return false;
    }
}