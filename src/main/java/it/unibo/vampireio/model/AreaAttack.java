package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class AreaAttack extends CollidableEntity implements Attack {

    private int damage;

    public AreaAttack(String id, Point2D.Double position, double radius, int damage) {
        super(id, position, radius);
        this.damage = damage;
    }

    @Override
    public void execute(Double position, Double direction) {
        System.out.println("ecsechiut");
    }

    @Override
    public void onCollision(Collidable collidable) {
        //DANNO AL NEMICO
    }

    @Override
    public int getDamage() {
        return this.damage;
    }    
}
