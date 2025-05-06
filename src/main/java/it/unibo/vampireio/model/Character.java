package it.unibo.vampireio.model;

import java.util.LinkedList;
import java.util.List;
import java.awt.Shape;
import java.awt.geom.Point2D;

public class Character extends LivingEntity {

    private static final int MAX_WEAPONS = 6;
    private Stats stats;
    
    private int level;
    private double levelPercentage;
    private int coinCounter;

    private List<Weapon> weapons = new LinkedList<>();

    public Character(String id, String name, Stats stats, Shape hitbox, Weapon weapon) {
        super(id, new Point2D.Double(0, 0), hitbox, new Point2D.Double(1, 0), stats.getStat(StatType.MOVE_SPEED), stats.getStat(StatType.MAX_HEALTH));
        this.stats = stats;//????
        this.level = 1;
        this.levelPercentage = 0;
        this.coinCounter = 0;
        this.addWeapon(weapon);
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
    public void onCollision(Collidable collidable) { }  ///////////

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
            this.levelPercentage += collectible.getValue() / Math.pow(this.level, 0.10); //DA CAMBIARE?
            if(this.levelPercentage >= 100) {
                this.levelPercentage -= 100;
                this.level++;
            }
        }
    }
}
