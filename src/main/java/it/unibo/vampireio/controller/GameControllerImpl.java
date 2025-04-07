package it.unibo.vampireio.controller;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Collectors;
import it.unibo.vampireio.model.AreaAttack;
import it.unibo.vampireio.model.Character;
import it.unibo.vampireio.model.Enemy;
import it.unibo.vampireio.model.Collectible;
import it.unibo.vampireio.model.GameModel;
import it.unibo.vampireio.model.GameWorld;
import it.unibo.vampireio.model.ProjectileAttack;
import it.unibo.vampireio.model.Saving;
import it.unibo.vampireio.view.GameView;
import it.unibo.vampireio.view.GameViewImpl;

public class GameControllerImpl implements GameController {
    private GameModel model;
    private GameView view;

    private long startTime;

    private boolean running = true;

    private final int frameRate = 60;
    private final int tickRate = 60;

    public GameControllerImpl() {
        this.model = new GameWorld();
        this.view = new GameViewImpl(this);
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public void startGame(String selectedCharacter) {
        this.model.initGame(selectedCharacter);
        this.startTime = System.currentTimeMillis();
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
            this.view.update(this.getData());
            try {
                Thread.sleep(frameTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private long getElapsedTime() {
        return System.currentTimeMillis() - this.startTime;
    }

    private GameData getData() {
        Dimension visualSize = this.model.getVisualSize();
        Character character = this.model.getCharacter();
        List<Enemy> enemies = this.model.getEnemies();
        List<ProjectileAttack> projectileAttacks = this.model.getProjectileAttacks();
        List<AreaAttack> areaAttacks = this.model.getAreaAttacks();
        List<Collectible> collectibles = this.model.getCollectibles();

        VisualSizeData visualSizeData = new VisualSizeData(
            visualSize.width, visualSize.height
        );

        LivingEntityData characterData = new LivingEntityData(
            character.getId(),
            new Point2D.Double(
                character.getPosition().getX(), 
                character.getPosition().getY()
            ),
            new Point2D.Double(
                character.getDirection().getX(), 
                character.getDirection().getY()
            ),
            character.getHealth(),
            character.getMaxHealth(),
            false,//character.isBeingAttacked(),
            true//character.isMoving()
        );

        List<LivingEntityData> enemiesData = enemies.stream()
            .map(enemy -> new LivingEntityData(
                enemy.getId(),
                new Point2D.Double(
                    enemy.getPosition().getX(),
                    enemy.getPosition().getY()
                ),
                new Point2D.Double(
                    enemy.getDirection().getX(),
                    enemy.getDirection().getY()
                ),
                enemy.getHealth(),
                enemy.getMaxHealth(),
                false,//enemy.isBeingAttacked(),
                true//enemy.isMoving()
            ))
            .collect(Collectors.toList());

        List<PositionableData> projectileAttacksData = projectileAttacks.stream()
            .map(projectileAttack -> new PositionableData(
                projectileAttack.getId(),
                new Point2D.Double(
                    projectileAttack.getPosition().getX(),
                    projectileAttack.getPosition().getY()
                ),
                new Point2D.Double(
                    projectileAttack.getDirection().getX(),
                    projectileAttack.getDirection().getY()
                )
            ))
            .collect(Collectors.toList());

        List<PositionableData> areaAttacksData = areaAttacks.stream()
            .map(areaAttack -> new PositionableData(
                areaAttack.getId(),
                new Point2D.Double(
                    areaAttack.getPosition().getX(),
                    areaAttack.getPosition().getY()
                ),
                new Point2D.Double(0, 0)
            ))
            .collect(Collectors.toList());

        List<PositionableData> collectiblesData = collectibles.stream()
            .map(collectible -> new PositionableData(
                collectible.getId(),
                new Point2D.Double(
                    collectible.getPosition().getX(),
                    collectible.getPosition().getY()
                ),
                new Point2D.Double(0, 0)
            ))
            .collect(Collectors.toList());

        return new GameData(
            this.getElapsedTime(),
            visualSizeData, 
            characterData, 
            enemiesData, 
            projectileAttacksData, 
            areaAttacksData, 
            collectiblesData
        );
    }

    @Override
    public List<String> getSavingsNames() {
        return this.model.getSavingsNames();
    }

    @Override
    public void createNewSaving() {
        this.model.createNewSaving();
    }

    @Override
    public void loadSaving(String selectedSaving) {
        this.model.loadSaving(selectedSaving);
    }

    @Override
    public List<ScoreData> getScores() {
        Saving currentSaving = this.model.getCurrentSaving();
        if (currentSaving == null) {
            return List.of();
        }
        return currentSaving.getScores().stream()
            .map(score -> new ScoreData(
                score.getCharacterName(),
                score.getSessionTime(),
                score.getKillCounter(),
                score.getLevelCounter(),
                score.getScore()
            ))
            .collect(Collectors.toList());
    }
}
