package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * AbstractAttack is an abstract class that represents an attack entity in the
 * game.
 * It extends AbstractMovableEntity and implements the Attack interface.
 * This class provides basic functionality for attacks, including damage,
 * duration, and collision handling.
 */
public abstract class AbstractAttack extends AbstractMovableEntity implements Attack {

    private final EntityManager entityManager;
    private final int damage;
    private final long duration;
    private boolean expired;
    private long elapsedTime;

    /**
     * Constructs a new AbstractAttack with the specified parameters.
     *
     * @param id            the unique identifier for the attack
     * @param position      the initial position of the attack
     * @param radius        the radius of the attack
     * @param direction     the initial direction of the attack
     * @param speed         the speed of the attack
     * @param damage        the damage dealt by the attack
     * @param duration      the duration of the attack in milliseconds
     * @param entityManager the entity manager to manage entities in the game
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "The EntityManager instance is intentionally shared"
            + "and is used in a controlled way within AbstractAttack."
    )
    public AbstractAttack(
            final String id,
            final Point2D.Double position,
            final double radius,
            final Point2D.Double direction,
            final double speed,
            final int damage,
            final long duration,
            final EntityManager entityManager) {
        super(id, position, radius, direction, speed);
        this.damage = damage;
        this.entityManager = entityManager;
        this.duration = duration;
    }

    /**
     * Subclasses overriding this method should call
     * {@code super.execute(tickTime)}.
     */
    @Override
    public void execute(final long tickTime) {
        this.elapsedTime += tickTime;
        if (elapsedTime > duration) {
            this.expired = true;
            return;
        }
        this.update(tickTime);
    }

    /**
     * Updates the state of the attack.
     * This method should be implemented by subclasses to define how the attack
     * behaves over time.
     *
     * @param tickTime the time elapsed since the last update in milliseconds
     */
    protected abstract void update(long tickTime);

    @Override
    public abstract void onCollision(Collidable collidable);

    @Override
    public final boolean isExpired() {
        return this.expired;
    }

    /**
     * Returns the damage dealt by this attack.
     *
     * @return the damage value
     */
    protected int getDamage() {
        return this.damage;
    }

    /**
     * Returns the EntityManager associated with this attack.
     *
     * @return the EntityManager managing entities in the game
     */
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    /**
     * Marks this attack as expired.
     * This method should be called when the attack has completed its duration
     * or has otherwise been deemed no longer active.
     */
    protected void expire() {
        this.expired = true;
    }
}
