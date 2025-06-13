package it.unibo.vampireio.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.vampireio.model.api.Collidable;
import it.unibo.vampireio.model.impl.attacks.AbstractAttack;
import it.unibo.vampireio.model.manager.EntityManager;
import java.awt.geom.Point2D;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TestAttack is a test class for the AbstractAttack class.
 * It tests the behavior of attacks, including their expiration and execution.
 */
class TestAttack {

    private static final Point2D.Double TEST_POSITION = new Point2D.Double(0.0, 0.0);
    private static final double TEST_RADIUS = 1.0;
    private static final Point2D.Double TEST_DIRECTION = new Point2D.Double(1.0, 0.0);
    private static final double TEST_SPEED = 1.0;
    private static final int TEST_DAMAGE = 10;
    private static final long TEST_SETUP_DURATION = 1000L;

    private static final long TEST_DURATION = 300;
    private static final long TEST_DURATION_2 = 400;
    private static final long TEST_DURATION_3 = 1000;
    private static final long TEST_DURATION_4 = 1500;
    private static final long TEST_TOT_DURATION = 700;

    private TestAttackImpl attack;

    @BeforeEach
    void setUp() {
        attack = new TestAttackImpl(
                "test_attack",
                TEST_POSITION,
                TEST_RADIUS,
                TEST_DIRECTION,
                TEST_SPEED,
                TEST_DAMAGE,
                TEST_SETUP_DURATION,
                null);
    }

    /**
     * Tests the initial state of the attack to ensure it is not expired.
     */
    @Test
    void testNotExpiredInitially() {
        assertFalse(attack.isExpired());
    }

    /**
     * Tests the expiration of the attack after a certain duration.
     * It checks if the attack is expired after executing for a duration longer than
     * its defined duration.
     */
    @Test
    void testExecuteWithinDuration() {
        attack.execute(TEST_DURATION);
        attack.execute(TEST_DURATION_2);
        assertFalse(attack.isExpired());
        assertEquals(TEST_TOT_DURATION, attack.getTotalUpdateCalled());
    }

    /**
     * Tests the behavior of the attack when it exceeds its defined duration.
     * It checks if the attack is marked as expired after executing for a total
     * duration longer than its defined duration.
     */
    @Test
    void testExecuteExceedingDuration() {
        attack.execute(TEST_DURATION);
        attack.execute(TEST_DURATION_3);
        assertTrue(attack.isExpired());
        assertEquals(TEST_DURATION, attack.getTotalUpdateCalled());
    }

    /**
     * Tests the behavior of the attack when it is executed after it has expired.
     * It checks if the total update count remains unchanged after executing an
     * expired attack.
     */
    @Test
    void testNoUpdateAfterExpired() {
        attack.execute(TEST_DURATION_4);
        assertTrue(attack.isExpired());

        final long before = attack.getTotalUpdateCalled();
        attack.execute(TEST_DURATION);
        assertEquals(before, attack.getTotalUpdateCalled());
    }

    private static class TestAttackImpl extends AbstractAttack {

        private long totalUpdateCalled;

        TestAttackImpl(
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
        public void onCollision(final Collidable collidable) {
        }

        public long getTotalUpdateCalled() {
            return totalUpdateCalled;
        }
    }
}
