package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
import java.awt.Dimension;

public class SantaWaterFactory extends AttackFactory {
    private static final String attackID = "attacks/santaWater";
    private final double SPAWN_AREA_PERCENTAGE = 0.8;
    AttackData attackData = this.getAttackDataById(attackID);
    
    public SantaWaterFactory(GameWorld gameWorld) {
        super(gameWorld);
    }
    
    @Override
    public Attack createAttack() {
        return new SantaWaterAttack(attackData.getId(), this.getRandomPosition(), attackData.getRadius(), attackData.getDamage(), attackData.getDuration(), gameWorld);
    }

    private Point2D.Double getRandomPosition() {
        Point2D.Double characterPosition = this.gameWorld.getCharacter().getPosition();
        Dimension dimension = gameWorld.getVisualSize();        
        double x = characterPosition.getX() + (Math.random() * dimension.getWidth() * SPAWN_AREA_PERCENTAGE) - (dimension.getWidth() * SPAWN_AREA_PERCENTAGE / 2);
        double y = characterPosition.getY() + (Math.random() * dimension.getHeight() * SPAWN_AREA_PERCENTAGE) - (dimension.getHeight() * SPAWN_AREA_PERCENTAGE / 2);
        return new Point2D.Double(x, y);
    }
    
    @Override
    public void increaseLevel() {
        super.increaseLevel();
        int currentDamage = this.attackData.getDamage();
        int newDamage = (int) (currentDamage * 1.5);
        this.attackData.setRadius(newDamage);
    }
}