package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public class GarlicFactory extends AttackFactory {
    private static final String attackID = "attacks/garlic";
    
    public GarlicFactory(GameWorld gameWorld) {
        super(gameWorld);
    }
    
    @Override
    public Attack createAttack() {
        double radius = 64.0;
        int damage = 20;
        long duration = 1000;
        
        Character character = this.gameWorld.getCharacter();

        return new GarlicAttack(attackID, character.getPosition(), radius, damage, duration, gameWorld);
    }
}