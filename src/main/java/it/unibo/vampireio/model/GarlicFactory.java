package it.unibo.vampireio.model;

public class GarlicFactory extends AttackFactory {
    private static final String attackID = "attacks/garlic";
    AttackData attackData = this.getAttackDataById(attackID);
    
    public GarlicFactory(GameWorld gameWorld) {
        super(gameWorld);
    }
    
    @Override
    public Attack createAttack() {
        Character character = this.gameWorld.getCharacter();
        Stats stats = character.getStats();
        return new GarlicAttack(
            attackData.getId(),
            character.getPosition(),
            attackData.getRadius(),
            (int) (attackData.getDamage() * stats.getStat(StatType.MIGHT)),
            attackData.getDuration(),
            gameWorld
        );
    }

    @Override
    public void increaseLevel() {
        super.increaseLevel();
        double currentRadius = this.attackData.getRadius();
        double newRadius = currentRadius * 1.1;
        this.attackData.setRadius(newRadius);
    }
}