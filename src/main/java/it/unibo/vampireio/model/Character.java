package it.unibo.vampireio.model;

import java.util.LinkedList;
import java.util.List;
import java.awt.Shape;
import java.awt.geom.Point2D;

public class Character extends LivingEntity {

    private static final int MAX_WEAPONS = 6;
    private int level;
    private double levelPercentage;
    
    private List<Weapon> weapons = new LinkedList<>();

    public Character(String id, String name, Stats stats, Shape hitbox, Weapon weapon) {
        super(id, new Point2D.Double(0, 0), hitbox, new Point2D.Double(1, 0), stats.getStat(StatType.MOVE_SPEED), stats.getStat(StatType.MAX_HEALTH));
        this.level = 1;
        this.levelPercentage = 0;
        this.addWeapon(weapon);
    }

    public int getLevel() {
        return this.level;
    }

    public void incrementLevel() {
        this.level++;
    }

    public double getLevelPercentage() {
        return this.levelPercentage;
    }

    public void setLevelPercentage(double levelPercentage) {
        this.levelPercentage = levelPercentage;
    }

    @Override
    public void onCollision(Collidable collidable) {
        
    }

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
}
