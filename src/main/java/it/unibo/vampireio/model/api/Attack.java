package it.unibo.vampireio.model.api;

/**
 * Represents an attack in the game.
 * An attack is a collidable and movable entity that can be executed and has a
 * lifespan.
 */
public interface Attack extends Collidable, Movable {
    /**
     * Executes the attack logic at the specified tick time.
     *
     * @param tickTime the current game tick time in milliseconds
     */
    void execute(long tickTime);

    /**
     * Checks if the attack has expired.
     *
     * @return true if the attack is expired, false otherwise
     */
    boolean isExpired();
}
