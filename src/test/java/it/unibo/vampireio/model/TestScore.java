package it.unibo.vampireio.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TestScore is a test class for the ScoreImpl class.
 * It tests the behavior of the score system, including kill counters, levels,
 * coin counters, session time,
 * and overall score calculation.
 */
class TestScore {

    private static final String CHARACTER_NAME = "Imelda";
    private static final int TEST_INCREMENT_VALUE = 2;
    private static final long TEST_TICKTIME = 200L;

    private ScoreImpl score;

    @BeforeEach
    void setUp() {
        score = new ScoreImpl(CHARACTER_NAME);
    }

    /**
     * Tests the initial values of the score system.
     * It checks if the character name is set correctly and if all counters are
     * initialized to zero.
     */
    @Test
    void testInitialValues() {
        assertEquals(CHARACTER_NAME, score.getCharacterName());
        assertEquals(0, score.getKillCounter());
        assertEquals(0, score.getLevel());
        assertEquals(0, score.getCoinCounter());
        assertEquals(0, score.getSessionTime());
    }

    /**
     * Tests the incrementKillCounter method.
     * It checks if the kill counter increments correctly.
     */
    @Test
    void testIncrementKillCounter() {
        score.incrementKillCounter();
        score.incrementKillCounter();
        assertEquals(TEST_INCREMENT_VALUE, score.getKillCounter());
    }

    /**
     * Tests the setLevel method.
     * It checks if the level is set correctly.
     */
    @Test
    void testSetLevel() {
        score.setLevel(TEST_INCREMENT_VALUE);
        assertEquals(TEST_INCREMENT_VALUE, score.getLevel());
    }

    /**
     * Tests the setCoinCounter method.
     * It checks if the coin counter is set correctly.
     */
    @Test
    void testSetCoinCounter() {
        score.setCoinCounter(TEST_INCREMENT_VALUE);
        assertEquals(TEST_INCREMENT_VALUE, score.getCoinCounter());
    }

    /**
     * Tests the incrementSessionTime method.
     * It checks if the session time increments correctly.
     */
    @Test
    void testIncrementSessionTime() {
        score.incrementSessionTime(TEST_TICKTIME);
        score.incrementSessionTime(TEST_TICKTIME);
        assertEquals(2 * TEST_TICKTIME, score.getSessionTime());
    }

    /**
     * Tests the getScore method.
     * It checks if the score is calculated correctly based on kills, level, and
     * session time.
     */
    @Test
    void testGetScore() {
        score.incrementKillCounter();
        score.incrementKillCounter();
        score.incrementKillCounter();
        score.setLevel(TEST_INCREMENT_VALUE);
        score.incrementSessionTime(TEST_TICKTIME);
        assertNotEquals(0, score.getScore());
    }
}
