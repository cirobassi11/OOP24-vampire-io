package it.unibo.vampireio.controller;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import it.unibo.vampireio.model.Character;
import it.unibo.vampireio.model.Enemy;
import it.unibo.vampireio.model.Collectible;
import it.unibo.vampireio.model.GameModel;
import it.unibo.vampireio.model.GameWorld;
import it.unibo.vampireio.view.GameView;
import it.unibo.vampireio.view.GameViewImpl;

public class GameControllerImpl implements GameController {
    private GameModel model;
    private GameView view;

    private boolean running = true;

    private int frameRate = 60;
    private int tickRate = 60;

    public GameControllerImpl() {
        this.view = new GameViewImpl(this);
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void startGame(String selectedCharacter) {
        this.model = new GameWorld(selectedCharacter);
        new Thread(this::modelLoop).start();
        new Thread(this::viewLoop).start();
    }

    private void modelLoop() {
        long tickTime = 1000 / this.tickRate;
        while (this.isRunning()) {
            this.model.update(tickTime); //Movement input should be passed.
            try {
                Thread.sleep(tickTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void viewLoop() {
        long frameTime = 1000 / this.frameRate;
        while (this.isRunning()) {
            this.view.update(this.getPositionablesData());
            try {
                Thread.sleep(frameTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<PositionableDTO> getPositionablesData() {
        Character character = this.model.getCharacter();
        List<Enemy> enemies = this.model.getEnemies();
        List<Collectible> collectibles = this.model.getCollectibles();

        List<PositionableDTO> positionables = new LinkedList<>();
        positionables.add(new PositionableDTO(character.getId(), character.getPosition(), character.getDirection()));
        positionables.addAll(enemies.stream().map(enemy -> new PositionableDTO(enemy.getId(), enemy.getPosition(), enemy.getDirection())).collect(Collectors.toSet()));
        positionables.addAll(collectibles.stream().map(collectible -> new PositionableDTO(collectible.getId(), collectible.getPosition(), new Point2D.Double(0, 0))).collect(Collectors.toSet()));
        
        return positionables;
    }

    @Override
    public VisualSizeDTO getVisualSizeData() {
        return new VisualSizeDTO((int) this.model.getVisualSize().getWidth(), (int) this.model.getVisualSize().getHeight());
    }

    public List<UnlockablePowerUpDTO> getUnlockablePowerUpData(){
        //TODO
        return null;
    }
}
