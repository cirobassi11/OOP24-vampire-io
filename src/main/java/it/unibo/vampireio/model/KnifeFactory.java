package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public class KnifeFactory extends AttackFactory {
    private static final String attackID = "attacks/knife";

    
    public KnifeFactory(GameWorld gameWorld) {
        super(gameWorld);
    }
    
    @Override
    public Attack createAttack() {
        double radius = 16;
        double speed = 3;
        int damage = 20;
        long duration = 10000;
        
        Character character = this.gameWorld.getCharacter();

        return new KnifeAttack(attackID, character.getPosition(), radius, character.getLastDirection(), speed, damage, duration, gameWorld);
    }
}