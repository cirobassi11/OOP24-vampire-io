package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

public class EnemySpawnerImpl implements EnemySpawner {

    private static final int INITIAL_SPAWN_INTERVAL = 2000;
    private static final int MIN_SPAWN_INTERVAL = 300;
    private static final int DECREMENT_INTERVAL = 1000;
    private static final int DECREMENT = 10;
    private static final int RANDOM_SPAWN_INTERVAL = 500;

    private static final int MAX_ENEMY_SPAWN = 5;

    private long spawnInterval;
    private long timeSinceLastSpawn;
    private long timeSinceLastDecrement;

    private List<EnemyData> enemies;

    private GameWorld gameWorld;
    private Random random = new Random();

    public EnemySpawnerImpl(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.enemies = this.gameWorld.getDataLoader().getEnemyLoader().loadAllEnemies();
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
            new Ellipse2D.Double(
                spawnPosition.getX(),
                spawnPosition.getY(),
                64,
                64
            ),
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
        Point2D.Double currentCharacterPosition = this.gameWorld.getCharacter().getPosition();

        int spawnAreaWidth = visualSize.width * 2;  // Spawn area più larga della finestra
        int spawnAreaHeight = visualSize.height * 2; // Spawn area più alta della finestra

        int spawnSide = this.random.nextInt(4);  // 0 = sinistra, 1 = destra, 2 = sopra, 3 = sotto

        double spawnX = 0;
        double spawnY = 0;

        switch (spawnSide) {
            case 0: // Sinistra
                spawnX = currentCharacterPosition.getX() - spawnAreaWidth - this.random.nextInt(spawnAreaWidth);
                spawnY = currentCharacterPosition.getY() - spawnAreaHeight / 2 + this.random.nextInt(spawnAreaHeight);
                break;
            case 1: // Destra
                spawnX = currentCharacterPosition.getX() + visualSize.getWidth() + this.random.nextInt(spawnAreaWidth);
                spawnY = currentCharacterPosition.getY() - spawnAreaHeight / 2 + this.random.nextInt(spawnAreaHeight);
                break;
            case 2: // Sopra
                spawnX = currentCharacterPosition.getX() - spawnAreaWidth / 2 + this.random.nextInt(spawnAreaWidth);
                spawnY = currentCharacterPosition.getY() - spawnAreaHeight - this.random.nextInt(spawnAreaHeight);
                break;
            case 3: // Sotto
                spawnX = currentCharacterPosition.getX() - spawnAreaWidth / 2 + this.random.nextInt(spawnAreaWidth);
                spawnY = currentCharacterPosition.getY() + visualSize.getHeight() + this.random.nextInt(spawnAreaHeight);
                break;
        }

        return new Point2D.Double(spawnX, spawnY);
    }
}
