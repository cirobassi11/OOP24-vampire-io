package it.unibo.vampireio.model;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.geom.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCollidable {

    private static class TestCollidableImpl extends CollidableEntity {
        private boolean collided = false;

        public TestCollidableImpl(String id, Point2D.Double position, double radius) {
            super(id, position, radius);
        }

        @Override
        public void onCollision(Collidable collidable) {
            this.collided = true;
        }

        public boolean hasCollided() {
            return collided;
        }
    }

    private TestCollidableImpl c1;
    private TestCollidableImpl c2;

    @BeforeEach
    void setUp() {
        c1 = new TestCollidableImpl("c1", new Point2D.Double(0.0, 0.0), 5.0);
        c2 = new TestCollidableImpl("c2", new Point2D.Double(3.0, 4.0), 2.0);
    }

    @Test
    void testGetRadius() {
        assertEquals(5.0, c1.getRadius());
        assertEquals(2.0, c2.getRadius());
    }

    @Test
    void testOnCollisionCalled() {
        assertFalse(c1.hasCollided());
        c1.onCollision(c2);
        assertTrue(c1.hasCollided());
    }

    @Test
    void testInheritedMethods() {
        assertEquals("c1", c1.getId());
        assertEquals(new Point2D.Double(0.0, 0.0), c1.getPosition());
        assertEquals(5.0, c1.getDistance(c2), 1e-6);
    }
}