package it.unibo.vampireio.model;

import java.util.LinkedList;
import java.util.List;
import java.awt.geom.Point2D;

/**
 * Represents a character in the game.
 */
public final class Character extends AbstractLivingEntity {

    private static final double XP_GROWTH_EXPONENT = 0.7;
    private final Stats stats;
    private final int weaponSlots;
    private int level;
    private double levelPercentage;
    private int coinCounter;
    private Point2D.Double lastDirection = new Point2D.Double(-1, 0);

    private boolean hasJustLevelledUp;

    private final List<Weapon> weapons = new LinkedList<>();

    /**
     * Constructs a new character with the specified parameters.
     *
     * @param id          the unique identifier for the character
     * @param stats       the stats of the character
     * @param radius      the radius of the character
     * @param weapon      the initial weapon of the character
     * @param weaponSlots the maximum number of weapons the character can hold
     */
    public Character(final String id, final Stats stats, final double radius, final Weapon weapon,
            final int weaponSlots) {
        super(id, new Point2D.Double(0, 0), radius, new Point2D.Double(1, 0), stats.getStat(StatType.MOVE_SPEED),
                stats.getStat(StatType.MAX_HEALTH));
        this.stats = new Stats(stats);
        this.level = 1;
        this.levelPercentage = 0;
        this.coinCounter = 0;
        this.weaponSlots = weaponSlots;
        this.hasJustLevelledUp = false;
        this.addWeapon(weapon);
    }

    /**
     * Copy constructor that creates a new Character from an existing one.
     *
     * @param character the Character to copy
     */
    public Character(final Character character) {
        super(character);
        this.stats = new Stats(character.getStats());
        this.level = character.getLevel();
        this.levelPercentage = character.getLevelPercentage();
        this.coinCounter = character.getCoinCounter();
        this.weaponSlots = character.getWeaponSlots();
        this.hasJustLevelledUp = character.hasJustLevelledUp();
        this.lastDirection = new Point2D.Double(character.lastDirection.getX(), character.lastDirection.getY());
        this.weapons.addAll(character.getWeapons());
    }

    @Override
    public void setDirection(final Point2D.Double direction) {
        if (direction.getX() != 0 || direction.getY() != 0) {
            lastDirection = new Point2D.Double(direction.getX(), direction.getY());
        }
        super.setDirection(direction);
    }

    /**
     * Returns the last direction the character moved in.
     *
     * @return the last direction as a Point2D.Double
     */
    public Point2D.Double getLastDirection() {
        return new Point2D.Double(lastDirection.getX(), lastDirection.getY());
    }

    /**
     * Returns the stats of the character.
     *
     * @return the stats of the character
     */
    public Stats getStats() {
        return new Stats(this.stats);
    }

    /**
     * Returns the level of the character.
     *
     * @return the level of the character
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns the percentage of the current level.
     *
     * @return the level percentage
     */
    public double getLevelPercentage() {
        return this.levelPercentage;
    }

    /**
     * Returns the number of coins collected by the character.
     *
     * @return the coin counter
     */
    public int getCoinCounter() {
        return this.coinCounter;
    }

    @Override
    public void onCollision(final Collidable collidable) {
    }

    /**
     * Adds a weapon to the character's inventory.
     *
     * @param weapon the weapon to add
     * @return true if the weapon was added, false if the character has no more
     *         slots
     */
    public boolean addWeapon(final Weapon weapon) {
        if (this.weapons.size() >= this.weaponSlots) {
            return false;
        }
        this.weapons.add(weapon);
        return true;
    }

    /**
     * Updates all weapons of the character.
     *
     * @param tickTime the current game tick time
     */
    public void updateWeapons(final long tickTime) {
        for (final Weapon weapon : this.weapons) {
            weapon.update(tickTime);
        }
    }

    /**
     * Collects a collectible item, which can be a coin, food, or experience gem.
     *
     * @param collectible the collectible item to collect
     */
    public void collect(final Collectible collectible) {
        if (collectible instanceof Coin) {
            this.coinCounter += collectible.getValue();
        } else if (collectible instanceof Food) {
            this.heal(collectible.getValue());
        } else if (collectible instanceof ExperienceGem) {
            this.levelPercentage += collectible.getValue() * (1.0 / Math.pow(level + 1, XP_GROWTH_EXPONENT));
            if (this.levelPercentage >= 100) {
                this.levelPercentage -= 100;
                this.level++;
                this.hasJustLevelledUp = true;
            }
        }
    }

    /**
     * Checks if the character has just levelled up.
     *
     * @return true if the character has just levelled up, false otherwise
     */
    public boolean hasJustLevelledUp() {
        return this.hasJustLevelledUp;
    }

    /**
     * Resets the flag indicating that the character has just levelled up.
     * This should be called after the level-up effects have been applied.
     */
    public void resetHasJustLevelledUp() {
        this.hasJustLevelledUp = false;
    }

    /**
     * Returns a copy of the list of weapons the character has.
     *
     * @return a list of weapons
     */
    public List<Weapon> getWeapons() {
        return List.copyOf(this.weapons);
    }

    /**
     * Checks if the character has reached the maximum number of weapons.
     *
     * @return true if the character has the maximum number of weapons, false
     *         otherwise
     */
    public boolean hasMaxWeapons() {
        return this.weapons.size() >= this.weaponSlots;
    }

    /**
     * Returns the maximum number of weapon slots the character has.
     *
     * @return the number of weapon slots
     */
    public int getWeaponSlots() {
        return this.weaponSlots;
    }

    @Override
    public void dealDamage(final double damage) {
        final double reducedDamage = damage - damage * (this.stats.getStat(StatType.ARMOR) - 1);
        super.dealDamage(reducedDamage);
    }
}
