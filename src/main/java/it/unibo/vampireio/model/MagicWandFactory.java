package it.unibo.vampireio.model;

public class MagicWandFactory extends AttackFactory {
    private static final String attackID = "attacks/magicWand";
    AttackData attackData = this.getAttackDataById(attackID);
    
    public MagicWandFactory(GameWorld gameWorld) {
        super(gameWorld);
    }
    
    @Override
    public Attack createAttack() {
        Character character = this.gameWorld.getCharacter();
        return new MagicWandAttack(attackData.getId(), character.getPosition(), attackData.getRadius(), character.getDirection(), attackData.getSpeed(), attackData.getDamage(), attackData.getDuration(), gameWorld);
    }

    @Override
    public void increaseLevel() {
        super.increaseLevel();
        Weapon weapon = this.gameWorld.getWeaponById("weapons/magicWand");
        weapon.multiplyCooldown(0.90);
    }
}