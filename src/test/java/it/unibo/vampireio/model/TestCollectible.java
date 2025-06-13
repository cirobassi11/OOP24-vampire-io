package it.unibo.vampireio.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.geom.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.vampireio.model.api.Collectible;
import it.unibo.vampireio.model.api.Collidable;
import it.unibo.vampireio.model.impl.collectibles.AbstractCollectibleItem;

/**
 * TestCollectible is a test class for the Collectible interface and its
 * implementation
 * in AbstractCollectibleItem.
 */
class TestCollectible {

    private static final Point2D.Double TEST_POSITION = new Point2D.Double(0, 0);
    private static final int TEST_VALUE = 11;

    private Collectible collectible;

    @BeforeEach
    void setUp() {
        collectible = new TestCollectibleImpl("c1", TEST_POSITION, TEST_VALUE);
    }

    /**
     * Tests the getValue method of the Collectible interface.
     * It checks if the value of the collectible is returned correctly.
     */
    @Test
    void testGetValue() {
        assertEquals(TEST_VALUE, collectible.getValue());
    }

    private static class TestCollectibleImpl extends AbstractCollectibleItem {
        TestCollectibleImpl(final String id, final Point2D.Double position, final int value) {
            super(id, position, value);
        }

        @Override
        public void onCollision(final Collidable collidable) {
        }
    }
}
