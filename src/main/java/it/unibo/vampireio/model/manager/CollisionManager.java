package it.unibo.vampireio.model.manager;

import java.awt.geom.Point2D;
import java.util.List;
import it.unibo.vampireio.model.api.Attack;
import it.unibo.vampireio.model.api.Collectible;
import it.unibo.vampireio.model.api.Collidable;
import it.unibo.vampireio.model.api.Living;
import it.unibo.vampireio.model.impl.Enemy;
import it.unibo.vampireio.model.impl.Character;
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

    /**
     * Checks if the given attack collides with any enemies in the provided list.
     * If a collision is detected, it invokes the onCollision method of the attack
     * with the enemy as an argument.
     *
     * @param attack   the Attack object to check for collisions
     * @param enemies  a list of Living enemies to check against
     * @return true if a collision occurs, false otherwise
     */
    public static boolean checkAttackCollisions(final Attack attack, final List<Living> enemies) {
        for (final Living enemy : enemies) {
            if (attack.isColliding(enemy)) {
                attack.onCollision(enemy);
                return true;
            }
        }
        return false;
    }
}
