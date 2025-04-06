package it.unibo.vampireio.model;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import it.unibo.vampireio.model.Stats;

public class Character extends LivingEntity {

    public Character(String id) {
        this(id, "characterName", new Stats(), new Rectangle2D.Double());
    }

    public Character(String id, String name, Stats stats,  Shape hitbox) {
        super(id, new Point2D.Double(0, 0), hitbox, new Point2D.Double(1, 0), stats.getStat(StatType.MOVE_SPEED), stats.getStat(StatType.MAX_HEALTH));
    }

    @Override
    public void onCollision(Collidable collidable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollision'");
    }

}
