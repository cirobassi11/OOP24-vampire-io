package it.unibo.vampireio.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.geom.Point2D;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * TestLiving is a test class for the Living interface and its implementation
 * in AbstractLivingEntity.
 */
class TestLiving {

    private static final Point2D.Double TEST_POSITION = new Point2D.Double(0.0, 0.0);
    private static final double TEST_RADIUS = 1.0;
    private static final Point2D.Double TEST_DIRECTION = new Point2D.Double(1.0, 0.0);
    private static final double TEST_SPEED = 1.0;
    private static final double TEST_MAX_HEALTH = 100.0;
    private static final double TEST_DAMAGE = 70.0;
    private static final double TEST_HEAL = 50.0;

    private AbstractLivingEntity entity;

    @BeforeEach
    void setUp() {
        entity = new TestLivingImpl(
                "testEntity",
                TEST_POSITION,
                TEST_RADIUS,
                TEST_DIRECTION,
                TEST_SPEED,
                TEST_MAX_HEALTH);
    }

    /**
     * Tests the initial health of the entity, ensuring it is set correctly
     * and that the entity is not in a state of being attacked.
     */
    @Test
    void testInitialHealth() {
        assertEquals(TEST_MAX_HEALTH, entity.getHealth());
        assertEquals(TEST_MAX_HEALTH, entity.getMaxHealth());
        assertFalse(entity.isGettingAttacked());
    }

    /**
     * Tests the behavior of the entity when it is attacked.
     * It checks if the entity's health decreases and if it enters the attacked
     * state.
     */
    @Test
    void testDealDamage() {
        entity.dealDamage(TEST_DAMAGE);
        assertEquals(Math.max(0, TEST_MAX_HEALTH - TEST_DAMAGE), entity.getHealth());

        entity.dealDamage(TEST_DAMAGE);
        assertEquals(Math.max(0, TEST_MAX_HEALTH - 2 * TEST_DAMAGE), entity.getHealth());
    }

    /**
     * Tests the behavior of the entity when it is healed.
     * It checks if the entity's health increases and does not exceed the maximum
     * health.
     */
    @Test
    void testHeal() {
        entity.dealDamage(TEST_DAMAGE);
        entity.heal(TEST_HEAL);
        assertEquals(TEST_MAX_HEALTH - TEST_DAMAGE + TEST_HEAL, entity.getHealth());

        entity.heal(TEST_HEAL);
        assertEquals(TEST_MAX_HEALTH, entity.getHealth());
    }

    private static class TestLivingImpl extends AbstractLivingEntity {
        TestLivingImpl(
                final String id,
                final Point2D.Double position,
                final double radius,
                final Point2D.Double direction,
                final double speed,
                final double maxHealth) {
            super(id, position, radius, direction, speed, maxHealth);
        }

        @Override
        public void onCollision(final Collidable collidable) {
        }
    }
}
