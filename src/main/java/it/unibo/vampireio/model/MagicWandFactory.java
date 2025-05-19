package it.unibo.vampireio.model;

public class MagicWandFactory extends AttackFactory {
    private static final String attackID = "projectiles/magicWand";
    
    public MagicWandFactory(GameWorld gameWorld) {
        super(gameWorld);
    }
    
    @Override
    public Attack createAttack() {
        double radius = 5.0; // DA LEGGERE DA FILE
        double speed = 2;
        int damage = 20;
        
        Character character = this.gameWorld.getCharacter();

        return new MagicWandProjectile(attackID, character.getPosition(), radius, character.getDirection(), speed, damage, gameWorld);
    }
}