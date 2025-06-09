package it.unibo.vampireio.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestScore {

    private static final String CHARACTER_NAME = "Imelda";
    private ScoreImpl score;

    @BeforeEach
    void setUp() {
        score = new ScoreImpl(CHARACTER_NAME);
    }

    @Test
    void testInitialValues() {
        assertEquals(CHARACTER_NAME, score.getCharacterName());
        assertEquals(0, score.getKillCounter());
        assertEquals(0, score.getLevel());
        assertEquals(0, score.getCoinCounter());
        assertEquals(0, score.getSessionTime());
    }

    @Test
    void testIncrementKillCounter() {
        score.incrementKillCounter();
        score.incrementKillCounter();
        assertEquals(2, score.getKillCounter());
    }

    @Test
    void testSetLevel() {
        score.setLevel(5);
        assertEquals(5, score.getLevel());
    }

    @Test
    void testSetCoinCounter() {
        score.setCoinCounter(120);
        assertEquals(120, score.getCoinCounter());
    }

    @Test
    void testIncrementSessionTime() {
        score.incrementSessionTime(15);
        score.incrementSessionTime(120);
        assertEquals(15 + 120, score.getSessionTime());
    }

    @Test
    void testGetScore() {
        score.incrementKillCounter();
        score.incrementKillCounter();
        score.incrementKillCounter();
        score.setLevel(2);
        score.incrementSessionTime(180);
        assertEquals(3 + 2 + 3, score.getScore());
    }
}