package it.unibo.vampireio.model;

public class KnifeFactory extends AbstractAttackFactory {
    private static final String attackID = "attacks/knife";
    AttackData attackData = this.getAttackDataById(attackID);

    public KnifeFactory(GameWorld gameWorld) {
        super(gameWorld);
    }
    
    @Override
    public Attack createAttack() {
        Character character = this.gameWorld.getCharacter();
        Stats stats = character.getStats();
        return new KnifeAttack(
            attackData.getId(),
            character.getPosition(),
            attackData.getRadius(),
            character.getLastDirection(),
            attackData.getSpeed() * stats.getStat(StatType.SPEED),
            (int) (attackData.getDamage() * stats.getStat(StatType.MIGHT)),
            attackData.getDuration(),
            gameWorld
        );
    }

    @Override
    public void increaseLevel() {
        super.increaseLevel();
        Weapon weapon = this.gameWorld.getWeaponById("weapons/knife");
        weapon.multiplyCooldown(0.90);
    }
}