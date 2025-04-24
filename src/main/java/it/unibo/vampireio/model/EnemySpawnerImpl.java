package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.awt.Rectangle;
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
        //andando avanti col gioco i nemici devono essere sempre più forti
        //crea un numero casuale di nemici
        
        int enemyToSpawn = this.random.nextInt(MAX_ENEMY_SPAWN) + 1;
        
        for (int i = 0; i < enemyToSpawn; i++) {
            
            EnemyData enemyData = this.enemies.get(this.random.nextInt(2)); //////////TODO

            Point2D.Double spawnPosition = getRandomSpawnPosition();
            ///controlla
            Enemy newEnemy = new Enemy(enemyData.getId(), spawnPosition, new Rectangle(64, 64), new Point2D.Double(0, 0), enemyData.getSpeed(), enemyData.getHealth(), enemyData.getDamage());
            ////////
            this.gameWorld.addEnemy(newEnemy);
        }
    }

    private Point2D.Double getRandomSpawnPosition() {
        Dimension visualSize = this.gameWorld.getVisualSize();
        Point2D.Double currentCharacterPosition = this.gameWorld.getCharacter().getPosition();
        return new Point2D.Double(
            currentCharacterPosition.getX() + visualSize.getWidth() / 2 + this.random.nextInt((int) visualSize.getWidth()),
            currentCharacterPosition.getY() + visualSize.getHeight() / 2 + this.random.nextInt((int) visualSize.getHeight())
        );
    }
}
