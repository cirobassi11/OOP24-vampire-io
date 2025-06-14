package it.unibo.vampireio.model.manager;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.vampireio.model.api.Living;
import it.unibo.vampireio.model.data.DataLoader;
import it.unibo.vampireio.model.data.EnemyData;
import it.unibo.vampireio.model.impl.Enemy;
import it.unibo.vampireio.model.impl.GameWorld;

/**
 * EnemyFactory is responsible for creating enemy instances in the game world.
 * It manages the enemy data, spawn positions, and ensures that enemies do not
 * overlap with existing entities.
 */
public class EnemyFactory {
    private static final int MAX_SPAWN_ATTEMPTS = 10;

    private final EntityManager entityManager;
    private final List<EnemyData> enemiesData;
    private final int maxEnemyLevel;

    private final Random random = new Random();

    /**
     * Constructs an EnemyFactory with the specified EntityManager.
     *
     * @param entityManager the EntityManager instance to manage entities
     */
    @SuppressFBWarnings(
        value = "EI2", 
        justification = "The EntityManager instance is intentionally shared and is used in a controlled way within EnemyFactory"
    )
    public EnemyFactory(final EntityManager entityManager) {
        this.entityManager = entityManager;
        this.enemiesData = DataLoader.getInstance().getEnemyLoader().getAll();
        this.enemiesData.sort((e1, e2) -> Integer.compare(e1.getLevel(), e2.getLevel()));
        this.maxEnemyLevel = this.enemiesData.size() - 1;
    }

    /**
     * Creates an enemy based on the specified level.
     *
     * @param enemyLevel the level of the enemy to create
     * @return a new EnemyData instance corresponding to the specified level
     */
    public Optional<Enemy> createEnemy(final int enemyLevel) {
        final EnemyData enemyData = this.enemiesData.get(enemyLevel);

        Optional<Enemy> newEnemy = Optional.empty();
        if (enemyData != null) {
            final double radius = enemyData.getRadius();
            for (int attempts = 0; attempts < MAX_SPAWN_ATTEMPTS; attempts++) {
                final Point2D.Double pos = getRandomSpawnPosition(radius);
                if (isPositionFree(pos, radius)) {
                    newEnemy = Optional.of(new Enemy(
                            enemyData.getId(),
                            pos,
                            radius,
                            new Point2D.Double(0, 0),
                            enemyData.getSpeed(),
                            enemyData.getHealth(),
                            enemyData.getDamage()));
                    break;
                }
            }
        }
        return newEnemy;
    }

    /**
     * Returns the maximum level of enemies that can be created.
     *
     * @return the maximum enemy level
     */
    public int getMaxEnemyLevel() {
        return this.maxEnemyLevel;
    }

    //getrandomspawnPosition
    /**
     * Generates a random spawn position for an enemy, ensuring it is outside the
     * player's visual area.
     *
     * @param radius the radius of the enemy to be spawned
     * @return a Point2D.Double representing the random spawn position
     */
    private Point2D.Double getRandomSpawnPosition(final double radius) {
        final Dimension visualSize = GameWorld.VISUAL_SIZE;
        final Point2D.Double playerPos = this.entityManager.getCharacter().getPosition();

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
            default:
                break;
        }

        return new Point2D.Double(x, y);
    }

    /**
     * Checks if the specified position is free for spawning an enemy, ensuring it
     * does not overlap with existing enemies.
     *
     * @param pos    the position to check
     * @param radius the radius of the enemy to be spawned
     * @return true if the position is free, false otherwise
     */
    private boolean isPositionFree(final Point2D.Double pos, final double radius) {
        for (final Living e : this.entityManager.getEnemies()) {
            final double distance = pos.distance(e.getPosition());
            if (distance < radius + e.getRadius()) {
                return false;
            }
        }
        return true;
    }
}
