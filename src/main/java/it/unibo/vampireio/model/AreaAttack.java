package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class AreaAttack extends CollidableEntity implements Attack {

    public AreaAttack(String id, Point2D.Double position, double radius, int damage) {
        super(id, position, radius);
    }

    @Override
    public void execute() {
        System.out.println("ecsechiut");
    }

    @Override
    public void onCollision(Collidable collidable) {
        //DANNO AL NEMICO
    }
}
