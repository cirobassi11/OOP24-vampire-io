package it.unibo.vampireio.model.manager;

import java.util.Optional;
import java.util.Random;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.vampireio.model.impl.Enemy;

/**
 * EnemySpawner is responsible for spawning enemies in the game world.
 * It manages the spawn intervals, levels, and the specific enemies to spawn.
 * The spawner adjusts its behavior based on the game's progression and time
 * remaining.
 */
final class EnemySpawner {
    private static final long INITIAL_SPAWN_INTERVAL = 2000;
    private static final long MIN_SPAWN_INTERVAL = 300;
    private static final long DECREMENT_INTERVAL = 500;
    private static final long DECREMENT = 10;
    private static final long RANDOM_SPAWN_INTERVAL = 500;
    private static final long MAX_ENEMY_SPAWN = 4;
    private static final long LEVEL_INTERVAL = 30_000;

    private final Random random = new Random();
    private final EntityManager entityManager;
    private final EnemyFactory enemyFactory;

    private long spawnInterval;
    private long timeSinceLastSpawn;
    private long timeSinceLastDecrement;
    private long timeSinceLevelUp;
    private long timeRemaining;
    private int currentLevel;
    private boolean reaperSpawned;

    /**
     * Constructs an EnemySpawner with the specified parameters.
     *
     * @param entityManager the EntityManager instance to manage entities
     * @param enemyFactory  the EnemyFactory to create enemies
     * @param maxGameDuration the maximum duration of the game in milliseconds
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "The EntityManager instance is intentionally shared and is used in a controlled way within EnemySpawner."
    )
    EnemySpawner(final EntityManager entityManager, final EnemyFactory enemyFactory, final long maxGameDuration) {
        this.entityManager = entityManager;
        this.enemyFactory = enemyFactory;
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.timeSinceLastSpawn = 0;
        this.timeSinceLastDecrement = 0;
        this.timeSinceLevelUp = 0;
        this.timeRemaining = maxGameDuration;
        this.currentLevel = 0;
    }

    /**
     * Updates the spawner's state based on the elapsed time.
     *
     * @param tickTime the time elapsed since the last update in milliseconds
     */
    void update(final long tickTime) {
        this.timeRemaining -= tickTime;
        this.timeSinceLastSpawn += tickTime;
        this.timeSinceLastDecrement += tickTime;
        this.timeSinceLevelUp += tickTime;

        if (this.timeSinceLastDecrement >= DECREMENT_INTERVAL) {
            this.spawnInterval = Math.max(MIN_SPAWN_INTERVAL, this.spawnInterval - DECREMENT);
            this.timeSinceLastDecrement = 0;
        }

        if (this.timeSinceLevelUp >= LEVEL_INTERVAL && this.currentLevel < this.enemyFactory.getMaxEnemyLevel()) {
            this.currentLevel++;
            this.timeSinceLevelUp = 0;
        }

        if (this.timeSinceLastSpawn >= spawnInterval + random.nextLong(RANDOM_SPAWN_INTERVAL)) {
            spawnEnemy();
            this.timeSinceLastSpawn = 0;
        }
    }

    /**
     * Spawns enemies based on the current game state.
     * It spawns a reaper if it hasn't been spawned yet and the time is right,
     * and then spawns a random number of enemies based on the current level.
     */
    private void spawnEnemy() {
        if (!reaperSpawned && timeRemaining <= 0) {
            final Optional<Enemy> reaper = enemyFactory.createEnemy(enemyFactory.getMaxEnemyLevel());
            if (reaper.isPresent()) {
                this.entityManager.addEnemy(reaper.get());
            }
            reaperSpawned = true;
        }

        final long enemiesToSpawn = random.nextLong(MAX_ENEMY_SPAWN) + 1;
        final int levelCap = Math.min(currentLevel, enemyFactory.getMaxEnemyLevel() - 1);

        for (int i = 0; i < enemiesToSpawn; i++) {
            final Optional<Enemy> enemy = enemyFactory.createEnemy(random.nextInt(levelCap + 1));
            if (enemy.isPresent()) {
                this.entityManager.addEnemy(enemy.get());
            }
        }
    }
}
