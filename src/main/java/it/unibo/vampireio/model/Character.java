package it.unibo.vampireio.model;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Character extends LivingEntity {

    private int level;
    private double levelPercentage;

    public Character(String id) {
        this(id, "characterName", new Stats(), new Rectangle2D.Double());
    }

    public Character(String id, String name, Stats stats,  Shape hitbox) {
        super(id, new Point2D.Double(0, 0), hitbox, new Point2D.Double(1, 0), stats.getStat(StatType.MOVE_SPEED), stats.getStat(StatType.MAX_HEALTH));
        this.level = 1;
        this.levelPercentage = 0;
    }

    @Override
    public void onCollision(Collidable collidable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollision'");
    }

    public boolean isMoving() {
        boolean isMoving = this.getDirection().getX() != 0 || this.getDirection().getY() != 0;
        return isMoving;
    }

    public boolean isBeingAttacked() {
        return false;//TODOOO
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

}
