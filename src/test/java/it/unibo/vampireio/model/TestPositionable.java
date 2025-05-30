package it.unibo.vampireio.model;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.geom.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestPositionable {

    private static class TestPositionableImpl extends AbstractPositionableEntity {
        public TestPositionableImpl(String id, Point2D.Double position) {
            super(id, position);
        }
    }

    private TestPositionableImpl pos;

    @BeforeEach
    void setUp() {
        pos = new TestPositionableImpl("p1", new Point2D.Double(0.0, 0.0));
    }

    @Test
    void testGetId() {
        assertEquals("p1", pos.getId());
    }

    @Test
    void testGetAndSetPosition() {
        assertEquals(new Point2D.Double(0.0, 0.0), pos.getPosition());

        Point2D.Double newPosition = new Point2D.Double(10.5, -7.3);
        pos.setPosition(newPosition);

        assertEquals(newPosition, pos.getPosition());
    }

    @Test
    void testGetDistance() {
        Positionable other = new TestPositionableImpl("p2", new Point2D.Double(3.0, 4.0));
        assertEquals(5.0, pos.getDistance(other), 1e-6);
    }

    @Test
    void testGetDistanceZero() {
        Positionable other = new TestPositionableImpl("p3", new Point2D.Double(0.0, 0.0));
        assertEquals(0.0, pos.getDistance(other));
    }
}