package it.unibo.vampireio.model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCollectible {

    private Collectible collectible;
    private static class TestCollectibleImpl extends AbstractCollectibleItem {
        public TestCollectibleImpl(final String id, final Point2D.Double position, final int value) {
            super(id, position, value);
        }

        @Override
        public void onCollision(Collidable collidable) {}
    }

    @BeforeEach
    void setUp() {
        collectible = new TestCollectibleImpl("c1", new Point2D.Double(0, 0), 42);
    }

    @Test
    void testGetValue() {
        assertEquals(42, collectible.getValue());
    }
}