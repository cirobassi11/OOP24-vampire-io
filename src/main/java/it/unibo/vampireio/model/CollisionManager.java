package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Iterator;

/**
 * CollisionManager is a utility class that handles collision detection and
 * response
 * between characters, enemies, and collectibles in the game.
 * It provides methods to check for collisions and handle the consequences of
 * those collisions.
 */
public final class CollisionManager {

    /**
     * Private constructor to prevent instantiation of this utility class.
     * This class should only be used statically.
     */
    private CollisionManager() {
    }

    static void checkCharacterCollisions(
            final Character character,
            final List<Enemy> enemies,
            final List<Collectible> collectibles) {
        checkEnemyCharacterCollisions(character, enemies);
        checkCharacterCollectibleCollisions(character, collectibles);
    }

    private static void checkEnemyCharacterCollisions(final Collidable character, final List<Enemy> enemies) {
        for (final Collidable enemy : enemies) {
            if (enemy.isColliding(character)) {
                enemy.onCollision(character);
            }
        }
    }

    private static void checkCharacterCollectibleCollisions(final Character character,
            final List<Collectible> collectibles) {
        final Iterator<Collectible> it = collectibles.iterator();
        while (it.hasNext()) {
            final Collectible collectible = it.next();
            if (collectible.isColliding(character)) {
                character.collect(collectible);
                it.remove();
            }
        }
    }

    static boolean checkEnemyCollisions(
            final Enemy currentEnemy,
            final Point2D.Double futurePosition,
            final List<Enemy> enemies,
            final Character character) {
        final double currentEnemyRadius = currentEnemy.getRadius();

        for (final Enemy otherEnemy : enemies) {
            if (!currentEnemy.equals(otherEnemy)) {
                final double combinedRadius = currentEnemyRadius + otherEnemy.getRadius();

                if (futurePosition.distance(otherEnemy.getPosition()) < combinedRadius / 2) {
                    return true;
                }
            }
        }
        final double combinedRadius = currentEnemyRadius + character.getRadius();
        
        return futurePosition.distance(character.getPosition()) < combinedRadius / 2;
    }

    static boolean checkAttackCollisions(final Attack attack, final List<Living> enemies) {
        for (final Living enemy : enemies) {
            if (attack.isColliding(enemy)) {
                attack.onCollision(enemy);
                return true;
            }
        }
        return false;
    }
}
