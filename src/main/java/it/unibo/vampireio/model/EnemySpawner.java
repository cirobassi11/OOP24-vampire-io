package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

public class EnemySpawner {
    private static final long INITIAL_SPAWN_INTERVAL = 2000;
    private static final long MIN_SPAWN_INTERVAL = 300;
    private static final long DECREMENT_INTERVAL = 500;
    private static final long DECREMENT = 10;
    private static final long RANDOM_SPAWN_INTERVAL = 500;
    private static final long MAX_ENEMY_SPAWN = 4;
    private static final long LEVEL_INTERVAL = 30_000;
    
    private long spawnInterval;
    private long timeSinceLastSpawn;
    private long timeSinceLastDecrement;
    private long timeSinceLevelUp;
    private long timeRemaining;
    private int currentLevel;
    private int maxEnemyLevel;
    private boolean reaperSpawned;

    private final List<EnemyData> enemiesData;
    private final GameWorld gameWorld;
    private final Random random = new Random();

    public EnemySpawner(final GameWorld gameWorld, final long maxGameDuration) {
        this.gameWorld = gameWorld;
        this.enemiesData = this.gameWorld.getDataLoader().getEnemyLoader().getAll();
        this.enemiesData.sort((e1, e2) -> Integer.compare(e1.getLevel(), e2.getLevel()));
        this.maxEnemyLevel = this.enemiesData.size() - 1;
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.timeSinceLastSpawn = 0;
        this.timeSinceLastDecrement = 0;
        this.timeSinceLevelUp = 0;
        this.timeRemaining = maxGameDuration;
        this.currentLevel = 0;
    }

    public void update(final long tickTime) {
        this.timeRemaining -= tickTime;
        this.timeSinceLastSpawn += tickTime;
        this.timeSinceLastDecrement += tickTime;
        this.timeSinceLevelUp += tickTime;

        if (this.timeSinceLastDecrement >= DECREMENT_INTERVAL) {
            this.spawnInterval = Math.max(MIN_SPAWN_INTERVAL, this.spawnInterval - DECREMENT);
            this.timeSinceLastDecrement = 0;
        }

        if (this.timeSinceLevelUp >= LEVEL_INTERVAL && this.currentLevel < this.maxEnemyLevel) {
            this.currentLevel++;
            this.timeSinceLevelUp = 0;
        }

        if (this.timeSinceLastSpawn >= spawnInterval + random.nextLong(RANDOM_SPAWN_INTERVAL)) {
            spawnEnemy();
            this.timeSinceLastSpawn = 0;
        }
    }

    private void spawnEnemy() {
        if (!reaperSpawned && timeRemaining <= 0) {
            spawnSpecificEnemy(enemiesData.getLast());
            reaperSpawned = true;
        }

        final long enemiesToSpawn = random.nextLong(MAX_ENEMY_SPAWN) + 1;
        final int levelCap = Math.min(currentLevel, enemiesData.size() - 2);

        for (int i = 0; i < enemiesToSpawn; i++) {
            final EnemyData enemyData = enemiesData.get(random.nextInt(levelCap + 1));
            spawnSpecificEnemy(enemyData);
        }
    }

    private void spawnSpecificEnemy(final EnemyData enemyData) {
        final double radius = enemyData.getRadius();
        for (int attempts = 0; attempts < 10; attempts++) {
            Point2D.Double pos = getRandomSpawnPosition(radius);
            if (isPositionFree(pos, radius)) {
                final Enemy newEnemy = new Enemy(
                    enemyData.getId(),
                    pos,
                    radius,
                    new Point2D.Double(0, 0),
                    enemyData.getSpeed(),
                    enemyData.getHealth(),
                    enemyData.getDamage()
                );
                gameWorld.addEnemy(newEnemy);
                break;
            }
        }
    }

    private Point2D.Double getRandomSpawnPosition(final double radius) {
        final Dimension visualSize = gameWorld.getVisualSize();
        final Point2D.Double playerPos = gameWorld.getCharacter().getPosition();

        final double halfWidth = visualSize.getWidth() / 2.0;
        final double halfHeight = visualSize.getHeight() / 2.0;

        final int side = random.nextInt(4);
        final int margin = (int) (radius * 2);

        double x = playerPos.getX();
        double y = playerPos.getY();

        switch (side) {
            case 0: // Left
                x -= halfWidth + margin;
                y -= halfHeight;
                y += random.nextInt(visualSize.height);
                break;
            case 1: // Right
                x += halfWidth + margin;
                y -= halfHeight;
                y += random.nextInt(visualSize.height);
                break;
            case 2: // Top
                y -= halfHeight + margin;
                x -= halfWidth;
                x += random.nextInt(visualSize.width);
                break;
            case 3: // Bottom
                y += halfHeight + margin;
                x -= halfWidth;
                x += random.nextInt(visualSize.width);
                break;
        }

        return new Point2D.Double(x, y);
    }

    private boolean isPositionFree(final Point2D.Double pos, final double radius) {
        for (final Enemy e : gameWorld.getEnemies()) {
            final double distance = pos.distance(e.getPosition());
            if (distance < radius + e.getRadius()) {
                return false;
            }
        }
        return true;
    }
}
