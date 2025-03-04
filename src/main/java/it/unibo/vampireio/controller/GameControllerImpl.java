package it.unibo.vampireio.controller;

import java.util.Set;

import it.unibo.vampireio.model.Character;
import it.unibo.vampireio.model.Enemy;
import it.unibo.vampireio.model.Collectible;
import it.unibo.vampireio.model.GameModel;
import it.unibo.vampireio.model.GameWorld;
import it.unibo.vampireio.view.GameView;

public class GameControllerImpl implements GameController {
    private GameModel model;
    private GameView view;

    private boolean running = true;

    private final int frameRate = /*60*/ 1;

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void setView(GameView view) {
        this.view = view;
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

            System.out.println("\n\nNUOVO CICLOO");

            try {
                Thread.sleep(1000 / this.frameRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public CharacterDTO getCharacterData() {
        Character character = this.model.getCharacter();
        //return new CharacterDTO(character.getPosition(), character.getHealth(), character.getMaxHealth(), character.getDirection());
        return null;
    }

    @Override
    public Set<EnemyDTO> getEnemiesData() {
        Set<Enemy> enemies = this.model.getEnemies();
        //return enemies.stream().map(enemy -> new EnemyDTO(enemy.getPosition(), enemy.getHealth(), enemy.getMaxHealth(), enemy.getDirection())).collect(Collectors.toSet());
        return null;
    }

    @Override
    public Set<CollectibleDTO> getCollectiblesData() {
        Set<Collectible> collectibles = this.model.getCollectibles();
        //return collectibles.stream().map(collectible -> new CollectibleDTO(collectible.getPosition(), collectible.getType())).collect(Collectors.toSet());
        return null;
    }
}
