package it.unibo.vampireio.controller;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Collectors;
import it.unibo.vampireio.model.*;
import it.unibo.vampireio.model.Character;

public class DataBuilder {

    public static GameData getData(GameModel model) {
        Dimension visualSize = model.getVisualSize();
        Character character = model.getCharacter();
        List<Enemy> enemies = model.getEnemies();
        List<Attack> attacks = model.getAttacks();
        List<Collectible> collectibles = model.getCollectibles();

        VisibleMapSizeData visibleMapSizeData = new VisibleMapSizeData(
            visualSize.width, visualSize.height
        );

        LivingEntityData characterData = new LivingEntityData(
            character.getId(),
            new Point2D.Double(character.getPosition().getX(), character.getPosition().getY()),
            new Point2D.Double(character.getDirection().getX(), character.getDirection().getY()),
            character.getRadius(),
            character.getHealth(),
            character.getMaxHealth(),
            character.isGettingAttacked(),
            character.isMoving()
        );

        List<LivingEntityData> enemiesData = enemies.stream()
            .map(enemy -> new LivingEntityData(
                enemy.getId(),
                new Point2D.Double(enemy.getPosition().getX(), enemy.getPosition().getY()),
                new Point2D.Double(enemy.getDirection().getX(), enemy.getDirection().getY()),
                enemy.getRadius(),
                enemy.getHealth(),
                enemy.getMaxHealth(),
                enemy.isGettingAttacked(),
                enemy.isMoving()
            ))
            .collect(Collectors.toList());

        List<PositionableData> attacksData = attacks.stream()
            .map(attack -> new PositionableData(
                attack.getId(),
                new Point2D.Double(attack.getPosition().getX(), attack.getPosition().getY()),
                new Point2D.Double(attack.getDirection().getX(), attack.getDirection().getY()),
                attack.getRadius()
            ))
            .collect(Collectors.toList());

        List<PositionableData> collectiblesData = collectibles.stream()
            .map(collectible -> new PositionableData(
                collectible.getId(),
                new Point2D.Double(collectible.getPosition().getX(), collectible.getPosition().getY()),
                new Point2D.Double(0, 0),
                collectible.getRadius()
            ))
            .collect(Collectors.toList());

        List<ItemData> itemsData = model.getWeapons().stream()
            .map(item -> new ItemData(item.getId(), "", ""))
            .collect(Collectors.toList());

        return new GameData(
            visibleMapSizeData,
            model.getElapsedTime(),
            model.getPlayerLevel(),
            model.getPlayerLevelPercentage(),
            model.getKillCounter(),
            model.getCoinCounter(),
            characterData,
            enemiesData,
            attacksData,
            collectiblesData,
            itemsData
        );
    }

    public static List<ScoreData> getScores(GameModel model) {
        Save currentSave = model.getCurrentSave();
        if (currentSave == null) {
            return List.of();
        }
        return currentSave.getScores().stream()
            .map(score -> new ScoreData(
                score.getCharacterName(),
                score.getSessionTime(),
                score.getKillCounter(),
                score.getLevel(),
                score.getCoinCounter(),
                score.getScore()
            ))
            .collect(Collectors.toList());
    }

    public static ScoreData getCurrentScore(GameModel model) {
        Score score = model.exitGame();
        return new ScoreData(
            score.getCharacterName(),
            score.getSessionTime(),
            score.getKillCounter(),
            score.getLevel(),
            score.getCoinCounter(),
            score.getScore()
        );
    }
}