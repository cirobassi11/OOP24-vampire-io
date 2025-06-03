package it.unibo.vampireio.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.geom.Point2D;
import static org.junit.jupiter.api.Assertions.*;

class LivingEntityTest {

    private AbstractLivingEntity entity;

    private static class TestLivingImpl extends AbstractLivingEntity {
        public TestLivingImpl(
            final String id,
            final Point2D.Double position,
            final double radius,
            final Point2D.Double direction,
            final double speed,
            final double maxHealth
        ) {
            super(id, position, radius, direction, speed, maxHealth);
        }

        @Override
        public void onCollision(Collidable collidable) { }
    }

    @BeforeEach
    void setUp() {
        entity = new TestLivingImpl(
                "testEntity",
                new Point2D.Double(0, 0),
                1.0,
                new Point2D.Double(1, 0),
                1.0,
                100.0
        );
    }

    @Test
    void testInitialHealth() {
        assertEquals(100.0, entity.getHealth());
        assertEquals(100.0, entity.getMaxHealth());
        assertFalse(entity.isGettingAttacked());
    }

    @Test
    void testDealDamage() {
        entity.dealDamage(25.0);
        assertEquals(75.0, entity.getHealth());

        entity.dealDamage(100.0);
        assertEquals(0.0, entity.getHealth());

        entity.dealDamage(-10.0);
        assertEquals(0.0, entity.getHealth());
    }

    @Test
    void testHeal() {
        entity.dealDamage(50.0);
        entity.heal(20.0);
        assertEquals(70.0, entity.getHealth());

        entity.heal(50.0);
        assertEquals(100.0, entity.getHealth());
    }
}
