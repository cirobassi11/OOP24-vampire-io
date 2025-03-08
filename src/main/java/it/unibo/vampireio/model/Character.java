package it.unibo.vampireio.model;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Character extends LivingEntity {

    public Character(String id) {
        this(id, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, new Rectangle2D.Double());
    }

    public Character(String id, String name, int maxHealth, int armor, double speed, double moveSpeed, double recovery,
            double might, double area, double duration, double amount, double cooldown, double luck, double growth,
            double greed, double curse, double magnet, double revival, double reroll, double skip, double banish,
            double charm, double defang, Shape hitbox) {
        super(id, new Point2D.Double(0, 0), hitbox, new Point2D.Double(1, 0), moveSpeed);
    }

    @Override
    public void onCollision(Collidable collidable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollision'");
    }

}
