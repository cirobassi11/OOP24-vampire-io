package it.unibo.vampireio.model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCollectible {

    private static class TestCollectibleImpl extends CollectibleItem {
        public TestCollectibleImpl(String id, Point2D.Double position, int value) {
            super(id, position, value);
        }

        @Override
        public void onCollision(Collidable collidable) {}
    }

    private Collectible collectible;

    @BeforeEach
    void setUp() {
        collectible = new TestCollectibleImpl("c1", new Point2D.Double(0, 0), 42);
    }

    @Test
    void testGetValue() {
        assertEquals(42, collectible.getValue());
    }
}