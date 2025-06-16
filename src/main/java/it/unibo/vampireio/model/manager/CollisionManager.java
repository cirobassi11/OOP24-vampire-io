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

    private static final double COLLISION_RADIUS_MULTIPLIER = 2.5;

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
            enemy.checkCollision(character);
        }
    }

    private static void checkCharacterCollectibleCollisions(final Character character,
            final List<Collectible> collectibles) {
        final Iterator<Collectible> it = collectibles.iterator();
        while (it.hasNext()) {
            final Collectible collectible = it.next();
            collectible.checkCollision(character);
            if (collectible.isCollected()) {
                it.remove();
            }
        }
    }

    static boolean checkEnemyCollisions(
            final Enemy currentEnemy,
            final Point2D.Double futurePosition,
            final List<Enemy> enemies,
            final Collidable character) {
        final double currentEnemyRadius = currentEnemy.getRadius();

        for (final Enemy otherEnemy : enemies) {
            if (!currentEnemy.equals(otherEnemy)) {
                final double combinedRadius = currentEnemyRadius + otherEnemy.getRadius();

                if (futurePosition.distance(otherEnemy.getPosition()) < combinedRadius / COLLISION_RADIUS_MULTIPLIER) {
                    return true;
                }
            }
        }
        final double combinedRadius = currentEnemyRadius + character.getRadius();

        return futurePosition.distance(character.getPosition()) < combinedRadius / COLLISION_RADIUS_MULTIPLIER;
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
            if (attack.checkCollision(enemy)) {
                return true;
            }
        }
        return false;
    }
}
