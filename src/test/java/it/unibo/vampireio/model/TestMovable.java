package it.unibo.vampireio.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.geom.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.vampireio.model.api.Collidable;
import it.unibo.vampireio.model.impl.AbstractMovableEntity;

/**
 * TestMovable is a test class for the Movable interface and its implementation
 * in AbstractMovableEntity.
 */
class TestMovable {

    private static final Point2D.Double START_POSITION = new Point2D.Double(0.0, 0.0);
    private static final Point2D.Double POSITION = new Point2D.Double(10.0, 5.0);
    private static final Point2D.Double TEST_FUTURE_POSITION = new Point2D.Double(10.0, -95.0);
    private static final Point2D.Double TEST_DIRECTION = new Point2D.Double(1.0, 1.0);
    private static final Point2D.Double TEST_DIRECTION_2 = new Point2D.Double(1, 0);
    private static final Point2D.Double TEST_DIRECTION_ZERO = new Point2D.Double(0.0, 0.0);
    private static final Point2D.Double TEST_FUTURE_DIRECTION = new Point2D.Double(0, -1);
    private static final Point2D.Double TEST_MOVEMENT = new Point2D.Double(200.0, 0.0);
    private static final double TEST_SPEED = 2.0;
    private static final double TEST_HALF_SPEED = 0.5;
    private static final double TEST_NORMAL_SPEED = 1.0;
    private static final double TEST_SPEED_ZERO = 0.0;
    private static final long TEST_TICKTIME = 1000;

    private TestMovableImpl movable;

    @BeforeEach
    void setUp() {
        movable = new TestMovableImpl("test", START_POSITION);
    }

    /**
     * Tests the setDirection and setSpeed methods of the Movable interface.
     * It checks if the direction and speed can be set and retrieved correctly.
     */
    @Test
    void testSetAndGetDirectionAndSpeed() {
        movable.setDirection(TEST_DIRECTION);
        movable.setSpeed(TEST_SPEED);

        assertEquals(TEST_DIRECTION, movable.getDirection());
        assertEquals(TEST_SPEED, movable.getSpeed());
    }

    /**
     * Tests the move method of the Movable interface.
     * It checks if the position is updated correctly based on the direction and
     * speed.
     */
    @Test
    void testMove() {
        movable.setDirection(TEST_DIRECTION_2);
        movable.setSpeed(TEST_NORMAL_SPEED);
        movable.move(TEST_TICKTIME);

        assertEquals(TEST_MOVEMENT, movable.getPosition());
    }

    /**
     * Tests the getFuturePosition method of the Movable interface.
     * It checks if the future position is calculated correctly based on the current
     * position,
     * direction, speed, and time.
     */
    @Test
    void testGetFuturePosition() {
        movable.setPosition(POSITION);
        movable.setDirection(TEST_FUTURE_DIRECTION);
        movable.setSpeed(TEST_HALF_SPEED);
        final Point2D.Double future = movable.getFuturePosition(TEST_TICKTIME);
        assertEquals(TEST_FUTURE_POSITION, future);
    }

    /**
     * Tests the isMoving method of the Movable interface.
     * It checks if the entity is considered moving based on its direction.
     */
    @Test
    void testIsMoving() {
        assertFalse(movable.isMoving());

        movable.setDirection(TEST_DIRECTION);
        assertTrue(movable.isMoving());

        movable.setDirection(TEST_DIRECTION_ZERO);
        assertFalse(movable.isMoving());
    }

    /*
     * * Tests the move method with zero speed.
     * It checks if the position remains unchanged when speed is zero.
     */
    @Test
    void testMoveWithZeroSpeed() {
        movable.setDirection(TEST_DIRECTION);
        movable.setSpeed(TEST_SPEED_ZERO);
        movable.move(TEST_TICKTIME);
        assertEquals(START_POSITION, movable.getPosition());
    }

    /**
     * Tests the move method with zero direction.
     * It checks if the position remains unchanged when the direction is zero.
     */
    @Test
    void testMoveWithZeroDirection() {
        movable.setDirection(TEST_DIRECTION_ZERO);
        movable.setSpeed(TEST_SPEED);
        movable.move(TEST_TICKTIME);
        assertEquals(START_POSITION, movable.getPosition());
    }

    private static class TestMovableImpl extends AbstractMovableEntity {
        TestMovableImpl(final String id, final Point2D.Double position) {
            super(id, position, 10.0, START_POSITION, 0.0);
        }

        @Override
        public void onCollision(final Collidable collidable) {
        }
    }
}
