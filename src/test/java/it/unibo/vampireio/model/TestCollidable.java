package it.unibo.vampireio.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.geom.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TestCollidable is a test class for the Collidable interface and its
 * implementation
 * in AbstractCollidableEntity.
 */
class TestCollidable {

    private static final Point2D.Double TEST_POSITION_1 = new Point2D.Double(0.0, 0.0);
    private static final Point2D.Double TEST_POSITION_2 = new Point2D.Double(3.0, 4.0);
    private static final double TEST_RADIUS_1 = 5.0;
    private static final double TEST_RADIUS_2 = 2.0;

    private TestCollidableImpl c1;
    private TestCollidableImpl c2;

    @BeforeEach
    void setUp() {
        c1 = new TestCollidableImpl("c1", TEST_POSITION_1, TEST_RADIUS_1);
        c2 = new TestCollidableImpl("c2", TEST_POSITION_2, TEST_RADIUS_2);
    }

    /*
     * * Tests the getRadius method of the Collidable interface.
     * It checks if the radius is returned correctly for different collidable
     * entities.
     */
    @Test
    void testGetRadius() {
        assertEquals(TEST_RADIUS_1, c1.getRadius());
        assertEquals(TEST_RADIUS_2, c2.getRadius());
    }

    /**
     * Tests the getPosition method of the Collidable interface.
     * It checks if the position is returned correctly for different collidable
     * entities.
     */
    @Test
    void testOnCollisionCalled() {
        assertFalse(c1.hasCollided());
        c1.onCollision(c2);
        assertTrue(c1.hasCollided());
    }

    private static class TestCollidableImpl extends AbstractCollidableEntity {
        private boolean collided;

        TestCollidableImpl(final String id, final Point2D.Double position, final double radius) {
            super(id, position, radius);
        }

        @Override
        public void onCollision(final Collidable collidable) {
            this.collided = true;
        }

        public boolean hasCollided() {
            return this.collided;
        }
    }
}
