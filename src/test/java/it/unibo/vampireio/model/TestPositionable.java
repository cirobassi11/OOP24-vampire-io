package it.unibo.vampireio.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.geom.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.vampireio.model.api.Positionable;
import it.unibo.vampireio.model.impl.AbstractPositionableEntity;

/**
 * TestPositionable is a test class for the Positionable interface and its
 * implementation in AbstractPositionableEntity.
 */
class TestPositionable {

    private static final Point2D.Double START_POSITION = new Point2D.Double(0.0, 0.0);
    private static final Point2D.Double TEST_POSITION_1 = new Point2D.Double(0.0, 0.0);
    private static final Point2D.Double TEST_POSITION_2 = new Point2D.Double(3.0, 4.0);
    private static final double EXACT_DISTANCE = 5.0;
    private static final double DELTA = 1e-6;

    private TestPositionableImpl pos;

    @BeforeEach
    void setUp() {
        pos = new TestPositionableImpl("p1", START_POSITION);
    }

    /*
     * * Tests the getPosition and setPosition methods of the Positionable
     * interface.
     * It checks if the initial position is set correctly and if it can be changed.
     */
    @Test
    void testGetAndSetPosition() {
        assertEquals(START_POSITION, pos.getPosition());
        pos.setPosition(TEST_POSITION_1);
        assertEquals(TEST_POSITION_1, pos.getPosition());
    }

    /**
     * Tests the getDistance method of the Positionable interface.
     * It checks if the distance between two Positionable entities is calculated
     * correctly.
     */
    @Test
    void testGetDistance() {
        final Positionable otherPos = new TestPositionableImpl("p2", TEST_POSITION_2);
        assertEquals(EXACT_DISTANCE, pos.getDistance(otherPos), DELTA);
    }

    private static class TestPositionableImpl extends AbstractPositionableEntity {
        TestPositionableImpl(final String id, final Point2D.Double position) {
            super(id, position);
        }
    }
}
