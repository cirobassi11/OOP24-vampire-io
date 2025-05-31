package it.unibo.vampireio.model;

public class MagicWandFactory extends AbstractAttackFactory {
    private static final String attackID = "attacks/magicWand";
    AttackData attackData = this.getAttackDataById(attackID);
    
    public MagicWandFactory(GameWorld gameWorld) {
        super(gameWorld);
    }
    
    @Override
    public Attack createAttack() {
        Character character = this.gameWorld.getCharacter();
        Stats stats = character.getStats();
        return new MagicWandAttack(
            attackData.getId(),
            character.getPosition(),
            attackData.getRadius(),
            character.getDirection(),
            attackData.getSpeed() * stats.getStat(StatType.SPEED),
            (int) (attackData.getDamage() * stats.getStat(StatType.MIGHT)),
            attackData.getDuration(),
            gameWorld
        );
    }

    @Override
    public void increaseLevel() {
        super.increaseLevel();
        Weapon weapon = this.gameWorld.getWeaponById("weapons/magicWand");
        weapon.multiplyCooldown(0.90);
    }
}