package it.unibo.vampireio.model;

import java.util.LinkedList;
import java.util.List;
import java.awt.geom.Point2D;

public class Character extends LivingEntity {

    private static final int MAX_WEAPONS = 3; //TODO: read from config
    
    private Stats stats;
    private int level;
    private double levelPercentage;
    private int coinCounter;
    private Point2D.Double lastDirection = new Point2D.Double(-1, 0);

    private boolean hasJustLevelledUp;

    private List<Weapon> weapons = new LinkedList<>();

    public Character(String id, String name, Stats stats, double radius, Weapon weapon) {
        super(id, new Point2D.Double(0, 0), radius, new Point2D.Double(1, 0), stats.getStat(StatType.MOVE_SPEED), stats.getStat(StatType.MAX_HEALTH));
        this.stats = stats;
        this.level = 1;
        this.levelPercentage = 0;
        this.coinCounter = 0;
        this.hasJustLevelledUp = false;
        this.addWeapon(weapon);
    }

    @Override
    public void setDirection(Point2D.Double direction) {
        if(direction.getX() != 0 || direction.getY() != 0) {
            lastDirection = direction;
        }
        super.setDirection(direction);
    }

    public Point2D.Double getLastDirection() {
        return this.lastDirection;
    }

    public Stats getStats() {
        return this.stats;
    }

    public int getLevel() {
        return this.level;
    }

    public double getLevelPercentage() {
        return this.levelPercentage;
    }

    public int getCoinCounter() {
        return this.coinCounter;
    }

    @Override
    public void onCollision(Collidable collidable) { }

    public boolean addWeapon(Weapon weapon) {
        if(this.weapons.size() >= MAX_WEAPONS) {
            return false;
        }
        this.weapons.add(weapon);
        return true;
    }

    public void updateWeapons(double tickTime) {
        for(Weapon weapon : this.weapons) {
            weapon.update(tickTime);
        }
    }
    
    public void collect(Collectible collectible) {
        if(collectible instanceof Coin) {
            this.coinCounter += collectible.getValue();
        }
        else if(collectible instanceof Food) {
            this.heal(collectible.getValue());
        }
        else if(collectible instanceof ExperienceGem) {
            this.levelPercentage += collectible.getValue() * 
            (Math.log(level + 2) / Math.pow(level + 1, 0.6)); // TODO: calculate a better formula
            if(this.levelPercentage >= 100) {
                this.levelPercentage -= 100;
                this.level++;
                this.hasJustLevelledUp = true;
            }
        }
    }

    public boolean hasJustLevelledUp() {
        boolean result = this.hasJustLevelledUp;
        this.hasJustLevelledUp = false;
        return result;
    }

    public List<Weapon> getWeapons() {
        return List.copyOf(this.weapons);
    }

    public boolean hasMaxWeapons() {
        return this.weapons.size() >= MAX_WEAPONS;
    }

    @Override
    public void dealDamage(double damage) {
        double reducedDamage = damage - damage * (this.stats.getStat(StatType.ARMOR) - 1);
        super.dealDamage(reducedDamage);
    }
}
