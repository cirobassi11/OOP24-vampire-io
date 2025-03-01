package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

public class GameWorld implements GameModel {

    private Set<Positionable> positionables;

    public static final Dimension WORLD_SIZE = new Dimension(800, 600);

    public GameWorld() {
        this.positionables = new HashSet<>();
    }

    @Override
    public void addPositionable(Positionable positionable) {
        this.positionables.add(positionable);
    }

    @Override
    public void removePositionable(Positionable positionable) {
        this.positionables.remove(positionable);
    }

    @Override
    public void update() {
        System.out.println("AGGIORNAMENTO MODELLOOO");
        //spanw enemies
        //check collisions
        //update positionables
    }
    
}
