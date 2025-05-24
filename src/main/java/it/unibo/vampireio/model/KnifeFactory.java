package it.unibo.vampireio.model;

public class KnifeFactory extends AttackFactory {
    private static final String attackID = "attacks/knife";
    AttackData attackData = this.getAttackDataById(attackID);

    public KnifeFactory(GameWorld gameWorld) {
        super(gameWorld);
    }
    
    @Override
    public Attack createAttack() {
        Character character = this.gameWorld.getCharacter();
        return new KnifeAttack(attackData.getId(), character.getPosition(), attackData.getRadius(), character.getLastDirection(), attackData.getSpeed(), attackData.getDamage(), attackData.getDuration(), gameWorld);
    }

    @Override
    public void increaseLevel() {
        super.increaseLevel();
        Weapon weapon = this.gameWorld.getWeaponById("weapons/knife");
        weapon.multiplyCooldown(0.90);
    }
}