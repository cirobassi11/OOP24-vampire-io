package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;

public class CollisionManager {
    private static final double COLLISION_DISTANCE = 50.0;
    private static final double ENEMY_COLLISION_DISTANCE = 15.0;

    public static void checkCharacterCollisions(
            final Character character,
            final List<Enemy> enemies,
            final List<Collectible> collectibles) {
        checkEnemyCharacterCollisions(character, enemies);
        checkCharacterCollectibleCollisions(character, collectibles);
    }

    private static void checkEnemyCharacterCollisions(Collidable character, List<Enemy> enemies) {
        for (Collidable enemy : enemies) {
            if (isColliding(enemy.getPosition(), character.getPosition(), COLLISION_DISTANCE)) {
                enemy.onCollision(character);
            }
        }
    }

    private static void checkCharacterCollectibleCollisions(Character character, List<Collectible> collectibles) {
        double magnetRange = COLLISION_DISTANCE * character.getStats().getStat(StatType.MAGNET);

        collectibles.removeIf(collectible -> {
            if (CollisionManager.isColliding(character.getPosition(), collectible.getPosition(), magnetRange)) {
                character.collect(collectible);
                return true;
            }
            return false;
        });
    }

    private static boolean isColliding(Point2D.Double pos1, Point2D.Double pos2, double distance) {
        return pos1.distance(pos2) <= distance;
    }

    static boolean checkEnemyCollisions(Enemy currentEnemy, Point2D.Double futurePosition, List<Enemy> enemies) {
        for (Enemy otherEnemy : enemies) {
            if (currentEnemy != otherEnemy && 
                futurePosition.distance(otherEnemy.getPosition()) < 15) {
                return true;
            }
        }
        return false;
    }
}