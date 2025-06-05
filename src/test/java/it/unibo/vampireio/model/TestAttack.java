package it.unibo.vampireio.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class AbstractAttackTest {

    private TestAttack attack;

    // Classe concreta per testare AbstractAttack
    private static class TestAttack extends AbstractAttack {

        private long totalUpdateCalled;

        public TestAttack(
                final String id,
                final Point2D.Double position,
                final double radius,
                final Point2D.Double direction,
                final double speed,
                final int damage,
                final long duration,
                final EntityManager entityManager) {
            super(id, position, radius, direction, speed, damage, duration, entityManager);
        }

        @Override
        protected void update(final long tickTime) {
            totalUpdateCalled += tickTime;
        }

        @Override
        public void onCollision(Collidable collidable) { }

        public long getTotalUpdateCalled() {
            return totalUpdateCalled;
        }
    }

    @BeforeEach
    void setUp() {
        attack = new TestAttack(
                "test_attack",
                new Point2D.Double(0, 0),
                1.0,
                new Point2D.Double(1, 0),
                1.0,
                10,
                1000L,
                null
        );
    }

    @Test
    void testNotExpiredInitially() {
        assertFalse(attack.isExpired());
    }

    @Test
    void testExecuteWithinDuration() {
        attack.execute(400);
        attack.execute(300);
        assertFalse(attack.isExpired());
        assertEquals(700, attack.getTotalUpdateCalled());
    }

    @Test
    void testExecuteExceedingDuration() {
        attack.execute(500);
        attack.execute(600);
        assertTrue(attack.isExpired());
        assertEquals(500, attack.getTotalUpdateCalled());
    }

    @Test
    void testNoUpdateAfterExpired() {
        attack.execute(1500);
        assertTrue(attack.isExpired());

        final long before = attack.getTotalUpdateCalled();
        attack.execute(100);
        assertEquals(before, attack.getTotalUpdateCalled());
    }
}
