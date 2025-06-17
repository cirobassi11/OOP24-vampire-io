package it.unibo.vampireio.controller.data;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.List;
import it.unibo.vampireio.model.api.GameModel;
import it.unibo.vampireio.model.api.Living;
import it.unibo.vampireio.model.api.Score;
import it.unibo.vampireio.model.api.Attack;
import it.unibo.vampireio.model.api.Collectible;

/**
 * Utility class that constructs various data objects
 * from the GameModel, such as GameData, ScoreData, and others.
 * It provides methods to get game information in a structured format.
 */
public final class DataBuilder {

    private DataBuilder() {
    }

    /**
     * Constructs a GameData object from the provided GameModel.
     *
     * @param model the game model containing the current game state
     * @return a GameData object representing the current game state
     */
    public static GameData getData(final GameModel model) {
        final Dimension visualSize = model.getVisualSize();
        final Living character = model.getCharacter();
        final List<Living> enemies = model.getEnemies();
        final List<Attack> attacks = model.getAttacks();
        final List<Collectible> collectibles = model.getCollectibles();

        final VisibleMapSizeData visibleMapSizeData = new VisibleMapSizeData(
                visualSize.width, visualSize.height);

        final LivingEntityData characterData = new LivingEntityData(
                character.getId(),
                new Point2D.Double(character.getPosition().getX(), character.getPosition().getY()),
                new Point2D.Double(character.getDirection().getX(), character.getDirection().getY()),
                character.getRadius(),
                character.getHealth(),
                character.getMaxHealth(),
                character.isGettingAttacked(),
                character.isMoving());

        final List<LivingEntityData> enemiesData = enemies.stream()
                .map(enemy -> new LivingEntityData(
                        enemy.getId(),
                        new Point2D.Double(enemy.getPosition().getX(),
                                enemy.getPosition().getY()),
                        new Point2D.Double(enemy.getDirection().getX(),
                                enemy.getDirection().getY()),
                        enemy.getRadius(),
                        enemy.getHealth(),
                        enemy.getMaxHealth(),
                        enemy.isGettingAttacked(),
                        enemy.isMoving()))
                .toList();

        final List<PositionableData> attacksData = attacks.stream()
                .map(attack -> new PositionableData(
                        attack.getId(),
                        new Point2D.Double(attack.getPosition().getX(),
                                attack.getPosition().getY()),
                        new Point2D.Double(attack.getDirection().getX(),
                                attack.getDirection().getY()),
                        attack.getRadius()))
                .toList();

        final List<PositionableData> collectiblesData = collectibles.stream()
                .map(collectible -> new PositionableData(
                        collectible.getId(),
                        new Point2D.Double(collectible.getPosition().getX(),
                                collectible.getPosition().getY()),
                        new Point2D.Double(0, 0),
                        collectible.getRadius()))
                .toList();

        final List<ItemData> itemsData = model.getWeapons().stream()
                .map(item -> new ItemData(item.getId(), "", ""))
                .toList();

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
                itemsData);
    }

    /**
     * Gets the scores from the current save in the game model.
     *
     * @param model the game model containing the current game state
     * @return a list of ScoreData objects representing the scores
     */
    public static List<ScoreData> getScores(final GameModel model) {
        final List<Score> scoresList = model.getScores();
        if (scoresList == null) {
            return List.of();
        }
        return scoresList.stream()
                .map(score -> new ScoreData(
                        score.getCharacterName(),
                        score.getSessionTime(),
                        score.getKillCounter(),
                        score.getLevel(),
                        score.getCoinCounter(),
                        score.getScore()))
                .toList();
    }

    /**
     * Gets the current score from the game model.
     *
     * @param model the game model containing the current game state
     * @return a ScoreData object representing the current score
     */
    public static ScoreData getCurrentScore(final GameModel model) {
        final Score score = model.exitGame();
        return new ScoreData(
                score.getCharacterName(),
                score.getSessionTime(),
                score.getKillCounter(),
                score.getLevel(),
                score.getCoinCounter(),
                score.getScore());
    }
}
