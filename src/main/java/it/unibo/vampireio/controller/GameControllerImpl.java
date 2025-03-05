package it.unibo.vampireio.controller;

import java.util.Set;
import java.util.stream.Collectors;
import it.unibo.vampireio.model.Character;
import it.unibo.vampireio.model.Enemy;
import it.unibo.vampireio.model.Collectible;
import it.unibo.vampireio.model.GameModel;
import it.unibo.vampireio.model.GameWorld;
import it.unibo.vampireio.model.UnlockablePowerUp;
import it.unibo.vampireio.view.GameView;
import it.unibo.vampireio.view.GameViewImpl;

public class GameControllerImpl implements GameController {
    private GameModel model;
    private GameView view;

    private boolean running = true;

    private final int frameRate = /*60*/ 4;

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
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (this.isRunning()) {
            this.model.update(); //bisogna passare l'input del movimento
            this.view.update();
            try {
                Thread.sleep(1000 / this.frameRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public VisualSizeDTO getVisualSizeData() {
        return new VisualSizeDTO((int) this.model.getVisualSize().getWidth(), (int) this.model.getVisualSize().getHeight());
    }

    @Override
    public CharacterDTO getCharacterData() {
        Character character = this.model.getCharacter();
        return new CharacterDTO(character.getId(), character.getPosition(), character.getDirection(), character.getHealth(), character.getMaxHealth());
    }

    @Override
    public Set<EnemyDTO> getEnemiesData() {
        Set<Enemy> enemies = this.model.getEnemies();
        return enemies.stream().map(enemy -> new EnemyDTO(enemy.getId(), enemy.getPosition(), enemy.getDirection())).collect(Collectors.toSet());
    }

    @Override
    public Set<CollectibleDTO> getCollectiblesData() {
        Set<Collectible> collectibles = this.model.getCollectibles();
        return collectibles.stream().map(collectible -> new CollectibleDTO(collectible.getId(), collectible.getPosition())).collect(Collectors.toSet());
    }

    public Set<UnlockablePowerUpDTO> getUnlockablePowerUpData(){
        //Set<UnlockablePowerUp> unlockablePowerUps = this.model.getUnlockablePowerUps();
        //return unlockablePowerUps.stream().
        return null;
    }
}
