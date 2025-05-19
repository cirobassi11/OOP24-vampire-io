package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

public class EnemySpawnerImpl implements EnemySpawner {

    private static final int INITIAL_SPAWN_INTERVAL = 2000;
    private static final int MIN_SPAWN_INTERVAL = 300;
    private static final int DECREMENT_INTERVAL = 500;
    private static final int DECREMENT = 10;
    private static final int RANDOM_SPAWN_INTERVAL = 500;
    private static final int MIN_SPAWN_DISTANCE = 100;
    private static final int MAX_SPAWN_DISTANCE = 300;
    private static final int MAX_ENEMY_SPAWN = 4;
    private static final int LEVEL_INTERVAL = 30_000;
    private static final int MAX_LEVEL = 1;

    private long spawnInterval;
    private long timeSinceLastSpawn;
    private long timeSinceLastDecrement;
    private long timeSinceLevelUp;
    private int currentLevel;

    private final List<EnemyData> enemiesData;
    private final GameWorld gameWorld;
    private final Random random = new Random();

    public EnemySpawnerImpl(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.enemiesData = this.gameWorld.getDataLoader().getEnemyLoader().getAll();
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.timeSinceLastSpawn = 0;
        this.timeSinceLastDecrement = 0;
        this.timeSinceLevelUp = 0;
        this.currentLevel = 0;
    }

    @Override
    public void update(long tickTime) {
        this.timeSinceLastSpawn += tickTime;
        this.timeSinceLastDecrement += tickTime;
        this.timeSinceLevelUp += tickTime;

        if (this.timeSinceLastDecrement >= DECREMENT_INTERVAL) {
            this.spawnInterval = Math.max(MIN_SPAWN_INTERVAL, this.spawnInterval - DECREMENT);
            this.timeSinceLastDecrement = 0;
        }

        if (this.timeSinceLevelUp >= LEVEL_INTERVAL && this.currentLevel < MAX_LEVEL) {
            this.currentLevel++;
            this.timeSinceLevelUp = 0;
        }

        if (this.timeSinceLastSpawn >= (this.spawnInterval + this.random.nextInt(RANDOM_SPAWN_INTERVAL))) {
            this.spawnEnemy();
            this.timeSinceLastSpawn = 0;
        }
    }

    private void spawnEnemy() {
        int enemyToSpawn = this.random.nextInt(MAX_ENEMY_SPAWN) + 1;

        for (int i = 0; i < enemyToSpawn; i++) {
            int levelCap = Math.min(this.currentLevel + 1, this.enemiesData.size());
            EnemyData enemyData = this.enemiesData.get(this.random.nextInt(levelCap));

            Point2D.Double spawnPosition = null;
            int maxTries = 10;

            for (int attempts = 0; attempts < maxTries; attempts++) {
                Point2D.Double candidatePosition = getRandomSpawnPosition();
                if (isPositionFree(candidatePosition, enemyData.getRadius())) {
                    spawnPosition = candidatePosition;
                    break;
                }
            }

            if (spawnPosition != null) {
                Enemy newEnemy = new Enemy(
                    enemyData.getId(),
                    spawnPosition,
                    enemyData.getRadius(),
                    new Point2D.Double(0, 0),
                    enemyData.getSpeed(),
                    enemyData.getHealth(),
                    enemyData.getDamage()
                );
                this.gameWorld.addEnemy(newEnemy);
            }
        }
    }

    private Point2D.Double getRandomSpawnPosition() {
        Dimension visualSize = this.gameWorld.getVisualSize();
        Point2D.Double playerPos = this.gameWorld.getCharacter().getPosition();

        double spawnX = 0;
        double spawnY = 0;

        int side = this.random.nextInt(4);
        int distance = MIN_SPAWN_DISTANCE + this.random.nextInt(MAX_SPAWN_DISTANCE - MIN_SPAWN_DISTANCE);

        switch (side) {
            case 0: // Sinistra
                spawnX = playerPos.getX() - visualSize.width / 2 - distance;
                spawnY = playerPos.getY() - visualSize.height / 2 + this.random.nextInt(visualSize.height);
                break;
            case 1: // Destra
                spawnX = playerPos.getX() + visualSize.width / 2 + distance;
                spawnY = playerPos.getY() - visualSize.height / 2 + this.random.nextInt(visualSize.height);
                break;
            case 2: // Sopra
                spawnX = playerPos.getX() - visualSize.width / 2 + this.random.nextInt(visualSize.width);
                spawnY = playerPos.getY() - visualSize.height / 2 - distance;
                break;
            case 3: // Sotto
                spawnX = playerPos.getX() - visualSize.width / 2 + this.random.nextInt(visualSize.width);
                spawnY = playerPos.getY() + visualSize.height / 2 + distance;
                break;
        }

        return new Point2D.Double(spawnX, spawnY);
    }

    private boolean isPositionFree(Point2D.Double pos, double radius) {
        for (Enemy e : gameWorld.getEnemies()) {
            double distance = pos.distance(e.getPosition());
            if (distance < radius + e.getRadius()) {
                return false;
            }
        }
        return true;
    }
}