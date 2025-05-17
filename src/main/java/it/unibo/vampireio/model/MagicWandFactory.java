package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public class MagicWandFactory extends AttackFactory {
    private final String attackID = "magicWand";
    
    public MagicWandFactory(GameWorld gameWorld) {
        super(gameWorld);
    }
    
    @Override
    public Attack createAttack() {
        double radius = 5.0; // DA LEGGERE DA FILE
        double speed = 4.5;
        int damage = 10;
        
        Character character = this.gameWorld.getCharacter();

        return new MagicWandProjectile("projectiles/magicWand", character.getPosition(), radius, character.getDirection(), speed, damage, gameWorld);
    }
}