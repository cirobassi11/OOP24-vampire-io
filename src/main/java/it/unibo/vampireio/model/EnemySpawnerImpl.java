package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.Random;

public class EnemySpawnerImpl implements EnemySpawner {

    private static final int INITIAL_SPAWN_INTERVAL = 2000;
    private static final int MIN_SPAWN_INTERVAL = 300;
    private static final int DECREMENT_INTERVAL = 1000;
    private static final int DECREMENT = 10;

    private static final int MAX_ENEMY_SPAWN = 5;

    private long spawnInterval;
    private long timeSinceLastSpawn;
    private long timeSinceLastDecrement;

    private GameModel gameWorld;
    private Random random = new Random();

    public EnemySpawnerImpl(GameModel gameWorld) {
        this.gameWorld = gameWorld;
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
        if (this.timeSinceLastSpawn >= (this.spawnInterval + this.random.nextInt(500))) {
            this.spawnEnemy();
            this.timeSinceLastSpawn = 0;
        }

    }

    private void spawnEnemy() {
        //andando avanti col gioco i nemici devono essere sempre più forti
        //crea un numero casuale di nemici
        for (int i = 0; i < random.nextInt(MAX_ENEMY_SPAWN) + 1; i++) {
            Enemy newEnemy = new Enemy("", this.getRandomSpawnPosition(), null, null, 0, 0, 0);
            this.gameWorld.addEnemy(newEnemy);
        }
    }

    private Point2D.Double getRandomSpawnPosition() {
        Dimension visualSize = this.gameWorld.getVisualSize();
        Point2D.Double currentCharacterPosition = this.gameWorld.getCharacter().getPosition();
        //returna una nuova posizione casuale, distante di almeno visualsize/2 da character
        return new Point2D.Double(
            currentCharacterPosition.getX() + visualSize.getWidth() / 2 + this.random.nextInt((int) visualSize.getWidth()),
            currentCharacterPosition.getY() + visualSize.getHeight() / 2 + this.random.nextInt((int) visualSize.getHeight())
        );
    }
}
