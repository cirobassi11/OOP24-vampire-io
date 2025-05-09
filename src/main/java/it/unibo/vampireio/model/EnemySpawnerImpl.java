package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

public class EnemySpawnerImpl implements EnemySpawner {

    private static final int INITIAL_SPAWN_INTERVAL = 2000;
    private static final int MIN_SPAWN_INTERVAL = 300;
    private static final int DECREMENT_INTERVAL = 2000;
    private static final int DECREMENT = 10;
    private static final int RANDOM_SPAWN_INTERVAL = 500;
    private static final int MIN_SPAWN_DISTANCE = 100;
    private static final int MAX_SPAWN_DISTANCE = 300;
    private static final int MAX_ENEMY_SPAWN = 4;

    private long spawnInterval;
    private long timeSinceLastSpawn;
    private long timeSinceLastDecrement;

    private List<EnemyData> enemies;

    private GameWorld gameWorld;
    private Random random = new Random();

    public EnemySpawnerImpl(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.enemies = this.gameWorld.getDataLoader().getEnemyLoader().getAll();
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.timeSinceLastSpawn = 0;
        this.timeSinceLastDecrement = 0;
    }

    @Override
    public void update(long tickTime) {
        this.timeSinceLastSpawn += tickTime;
        this.timeSinceLastDecrement += tickTime;

        if (this.timeSinceLastDecrement >= DECREMENT_INTERVAL) {
            this.spawnInterval = Math.max(MIN_SPAWN_INTERVAL, this.spawnInterval - DECREMENT);
            this.timeSinceLastDecrement = 0;
        }
        if (this.timeSinceLastSpawn >= (this.spawnInterval + this.random.nextInt(RANDOM_SPAWN_INTERVAL))) {
            this.spawnEnemy();
            this.timeSinceLastSpawn = 0;
        }

    }

    private void spawnEnemy() {
    int enemyToSpawn = this.random.nextInt(MAX_ENEMY_SPAWN) + 1;

    for (int i = 0; i < enemyToSpawn; i++) {
        EnemyData enemyData = this.enemies.get(this.random.nextInt(2)); /////TODO

        Point2D.Double spawnPosition = getRandomSpawnPosition();

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

    private Point2D.Double getRandomSpawnPosition() {
        Dimension visualSize = this.gameWorld.getVisualSize();
        Point2D.Double playerPos = this.gameWorld.getCharacter().getPosition();

        double spawnX = 0;
        double spawnY = 0;

        int side = random.nextInt(4);
        int distance = MIN_SPAWN_DISTANCE + random.nextInt(MAX_SPAWN_DISTANCE - MIN_SPAWN_DISTANCE);

        switch (side) {
            case 0: // Sinistra
                spawnX = playerPos.getX() - visualSize.width / 2 - distance;
                spawnY = playerPos.getY() - visualSize.height / 2 + random.nextInt(visualSize.height);
                break;
            case 1: // Destra
                spawnX = playerPos.getX() + visualSize.width / 2 + distance;
                spawnY = playerPos.getY() - visualSize.height / 2 + random.nextInt(visualSize.height);
                break;
            case 2: // Sopra
                spawnX = playerPos.getX() - visualSize.width / 2 + random.nextInt(visualSize.width);
                spawnY = playerPos.getY() - visualSize.height / 2 - distance;
                break;
            case 3: // Sotto
                spawnX = playerPos.getX() - visualSize.width / 2 + random.nextInt(visualSize.width);
                spawnY = playerPos.getY() + visualSize.height / 2 + distance;
                break;
        }

        return new Point2D.Double(spawnX, spawnY);
    }
}
