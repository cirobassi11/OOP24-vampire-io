package it.unibo.vampireio.model;

public class GarlicFactory extends AbstractAttackFactory {
    private static final String attackID = "attacks/garlic";
    AttackData attackData = this.getAttackDataById(attackID);
    
    public GarlicFactory(EntityManager entityManager) {
        super(entityManager);
    }
    
    @Override
    public Attack createAttack() {
        Character character = this.entityManager.getCharacter();
        Stats stats = character.getStats();
        return new GarlicAttack(
            attackData.getId(),
            character.getPosition(),
            attackData.getRadius(),
            (int) (attackData.getDamage() * stats.getStat(StatType.MIGHT)),
            attackData.getDuration(),
            entityManager
        );
    }

    @Override
    public void increaseLevel() {
        super.increaseLevel();
        double currentRadius = this.attackData.getRadius();
        double newRadius = currentRadius * 1.02;
        this.attackData.setRadius(newRadius);
    }
}