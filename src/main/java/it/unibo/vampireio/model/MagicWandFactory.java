package it.unibo.vampireio.model;

public class MagicWandFactory extends AttackFactory {
    private static final String attackID = "attacks/magicWand";
    
    public MagicWandFactory(GameWorld gameWorld) {
        super(gameWorld);
    }
    
    @Override
    public Attack createAttack() {
        double radius = 5.0; // DA LEGGERE DA FILE
        double speed = 2;
        int damage = 20;
        long duration = 5000; // 5 second
        
        Character character = this.gameWorld.getCharacter();

        return new MagicWandAttack(attackID, character.getPosition(), radius, character.getDirection(), speed, damage, duration, gameWorld);
    }

    @Override
    public void increaseLevel() {
        super.increaseLevel();
        //spawna un proiettile in più
    }
}