package it.unibo.vampireio.model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestMovable {

    private static class TestMovableImpl extends AbstractMovableEntity {
        public TestMovableImpl(String id, Point2D.Double position) {
            super(id, position, 10.0, new Point2D.Double(0, 0), 0.0);
        }

        @Override
        public void onCollision(Collidable collidable) { }
    }

    private TestMovableImpl movable;

    @BeforeEach
    void setUp() {
        movable = new TestMovableImpl("test", new Point2D.Double(0, 0));
    }

    @Test
    void testSetAndGetDirectionAndSpeed() {
        movable.setDirection(new Point2D.Double(1, 1));
        movable.setSpeed(2.0);

        assertEquals(new Point2D.Double(1, 1), movable.getDirection());
        assertEquals(2.0, movable.getSpeed());
    }

    @Test
    void testMove() {
        movable.setDirection(new Point2D.Double(1, 0));
        movable.setSpeed(1.0);
        movable.move(1000);
        assertEquals(new Point2D.Double(200.0, 0.0), movable.getPosition());
    }

    @Test
    void testGetFuturePosition() {
        movable.setPosition(new Point2D.Double(10.0, 5.0));
        movable.setDirection(new Point2D.Double(0, -1));
        movable.setSpeed(0.5);
        Point2D.Double future = movable.getFuturePosition(1000);
        assertEquals(new Point2D.Double(10.0, -95.0), future);
    }

    @Test
    void testIsMoving() {
        assertFalse(movable.isMoving());

        movable.setDirection(new Point2D.Double(0, 1));
        assertTrue(movable.isMoving());

        movable.setDirection(new Point2D.Double(0, 0));
        assertFalse(movable.isMoving());
    }

    @Test
    void testMoveWithZeroSpeed() {
        movable.setDirection(new Point2D.Double(1, 0));
        movable.setSpeed(0.0);
        movable.move(1000);

        assertEquals(new Point2D.Double(0.0, 0.0), movable.getPosition());
    }

    @Test
    void testMoveWithZeroDirection() {
        movable.setDirection(new Point2D.Double(0, 0));
        movable.setSpeed(5.0);
        movable.move(1000);
        assertEquals(new Point2D.Double(0.0, 0.0), movable.getPosition());
    }
}