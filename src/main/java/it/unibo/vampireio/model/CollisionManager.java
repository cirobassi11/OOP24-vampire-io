package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
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

    private static void checkEnemyCharacterCollisions(final Collidable character, List<Enemy> enemies) {
        for (final Collidable enemy : enemies) {
            if (enemy.isColliding(character)) {
                enemy.onCollision(character);
            }
        }
    }

    private static void checkCharacterCollectibleCollisions(final Character character, final List<Collectible> collectibles) {
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
            if (currentEnemy != otherEnemy) {
                final double combinedRadius = currentEnemyRadius + otherEnemy.getRadius();
                if (futurePosition.distance(otherEnemy.getPosition()) < combinedRadius / 2) {
                    return true;
                }
            }
        }
        final double combinedRadius = currentEnemyRadius + character.getRadius();
        if (futurePosition.distance(character.getPosition()) < combinedRadius / 2) {
            return true;
        }
        return false;
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
