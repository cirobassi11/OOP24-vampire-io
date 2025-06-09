package it.unibo.vampireio.model;

public class GarlicFactory extends AbstractAttackFactory {
    private static final String ATTACK_ID = "attacks/garlic";
    private static final double RADIUS_INCREASE_RATIO = 1.02;
    AttackData attackData = this.getAttackDataById(ATTACK_ID);

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
        double newRadius = currentRadius * RADIUS_INCREASE_RATIO;
        this.attackData.setRadius(newRadius);
    }
}
