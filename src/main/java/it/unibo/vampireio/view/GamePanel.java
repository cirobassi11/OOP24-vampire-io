package it.unibo.vampireio.view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import it.unibo.vampireio.controller.GameData;
import it.unibo.vampireio.controller.PositionableData;
import it.unibo.vampireio.controller.LivingEntityData;

class GamePanel extends JPanel {

    private static final int MAP_TILE_WIDTH = 64;
    private static final int MAP_TILE_HEIGHT = 64;

    private static final int HEALTH_BAR_WIDTH = 42;
    private static final int HEALTH_BAR_HEIGHT = 7;

    private static final int CHARACTER_FRAME_COUNT = 3;
    private static final int CHARACTER_FRAME_RATE = 8;
    private static final long CHARACTER_FRAME_DELAY = 1000 / CHARACTER_FRAME_RATE;

    private static final int LEVEL_BAR_Y_OFFSET = 2;
    private static final int LEVEL_BAR_HEIGHT = 20;
    private static final int LEVEL_BAR_BORDER_WIDTH = 3;
    private static final int LEVEL_TEXT_X_OFFSET = 50;
    private static final int LEVEL_TEXT_Y_OFFSET = 17;
    private static final int KILL_COUNTER_X_OFFSET = 170;
    private static final int COUNTER_Y_OFFSET = 48;
    private static final int KILL_ICON_X_OFFSET = 200;
    private static final int ICON_Y_OFFSET = 30;
    private static final int ICON_SIZE = 25;
    private static final int COIN_COUNTER_X_OFFSET = 70;
    private static final int COIN_ICON_X_OFFSET = 100;
    private static final int TILE_BORDER_ADJUSTMENT = 1;
    private static final int TILE_RENDER_PADDING = 2;
    private static final int FONT_SIZE_BASE = 15;
    private static final int TIMER_Y_EXTRA_OFFSET = 20;

    private static final double ENEMY_OSCILLATION_MAX_ANGLE_RAD = Math.toRadians(10);
    private static final double ENEMY_OSCILLATION_SPEED = 0.005;

    private GameViewImpl view;
    private GameData data;

    private ImageManager imageManager;

    private String lastCharacterDirection = "l";
    private int currentCharacterFrame = 0;
    private long lastCharacterFrameTime = 0;

    GamePanel(GameViewImpl view) {
        this.view = view;
        this.imageManager = new ImageManager(this.view);
        setFocusable(true);
    }

    void setData(GameData data) {
        if (this.data == null || data.getElapsedTime() < this.data.getElapsedTime()) {
            this.currentCharacterFrame = 0;
            this.lastCharacterFrameTime = 0;
        }
        this.data = data;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension fov = this.data.getVisibleMapSizeData().getDimension();
        double scale = this.view.getFrameSize().getWidth() / fov.getWidth();

        LivingEntityData character = this.data.getCharacterData();
        Dimension characterDimension = new Dimension((int) (character.getRadius() * 2), (int) (character.getRadius() * 2));

        int screenCenterX = (int) (this.getWidth() / 2);
        int screenCenterY = (int) (this.getHeight() / 2);

        int cameraOffsetX = (int) (screenCenterX - character.getPosition().getX() * scale);
        int cameraOffsetY = (int) (screenCenterY - character.getPosition().getY() * scale);

        int startTileX = (int) Math.floor((character.getPosition().getX() - screenCenterX / scale) / characterDimension.width);
        int startTileY = (int) Math.floor((character.getPosition().getY() - screenCenterY / scale) / characterDimension.height);

        int tilesNumX = (int) Math.ceil(fov.getWidth() / MAP_TILE_WIDTH) + 2;
        int tilesNumY = (int) Math.ceil(fov.getHeight() / MAP_TILE_HEIGHT) + 2;

        // Draws the map
        for (int y = 0; y < tilesNumY; y++) {
            for (int x = 0; x < tilesNumX; x++) {
                int worldX = (startTileX + x) * MAP_TILE_WIDTH;
                int worldY = (startTileY + y) * MAP_TILE_HEIGHT;

                int screenX = (int) (worldX * scale + cameraOffsetX);
                int screenY = (int) (worldY * scale + cameraOffsetY);

                Image tile = this.imageManager.getImage("map");
                if (tile != null) {
                    g.drawImage(tile, screenX - TILE_BORDER_ADJUSTMENT, screenY - TILE_BORDER_ADJUSTMENT,
                        (int) (MAP_TILE_WIDTH * scale) + TILE_RENDER_PADDING,
                        (int) (MAP_TILE_HEIGHT * scale) + TILE_RENDER_PADDING,
                        null
                    );
                }
            }
        }

        // Draws the collectibles
        for (PositionableData collectible : this.data.getCollectiblesData()) {
            Dimension collectibleDimension = new Dimension((int) (collectible.getRadius() * 2), (int) (collectible.getRadius() * 2));
            if (this.isVisible(collectible, collectibleDimension, scale, cameraOffsetX, cameraOffsetY)) {
                int collectibleX = (int) (collectible.getPosition().getX() * scale + cameraOffsetX - collectibleDimension.width / 2);
                int collectibleY = (int) (collectible.getPosition().getY() * scale + cameraOffsetY - collectibleDimension.height / 2);
                Image tile = this.imageManager.getImage(collectible.getId());
                if (tile != null) {
                    g.drawImage(tile, collectibleX, collectibleY,
                        (int) (collectibleDimension.width * scale),
                        (int) (collectibleDimension.height * scale),
                        null
                    );
                }
            }
        }

        // Draws attacks
        Graphics2D g2d = (Graphics2D) g;
        for (PositionableData attack : this.data.getAttacksData()) {
            Dimension attackDimension = new Dimension((int) (attack.getRadius() * 2), (int) (attack.getRadius() * 2));
            if (this.isVisible(attack, attackDimension, scale, cameraOffsetX, cameraOffsetY)) {
                int projectileX = (int) (attack.getPosition().getX() * scale + cameraOffsetX - attackDimension.width / 2);
                int projectileY = (int) (attack.getPosition().getY() * scale + cameraOffsetY - attackDimension.height / 2);
                Image tile = this.imageManager.getImage(attack.getId());
                if (tile != null) {
                    int width = (int) (attackDimension.width * scale);
                    int height = (int) (attackDimension.height * scale);
                    double rotationAngle = Math.atan2(attack.getDirection().getY(), attack.getDirection().getX());
                    AffineTransform transform = new AffineTransform();
                    transform.translate(projectileX + width / 2, projectileY + height / 2);
                    transform.rotate(rotationAngle);
                    transform.translate(-width / 2, -height / 2);
                    g2d.drawImage(tile.getScaledInstance(width, height, Image.SCALE_SMOOTH), transform, null);
                }
            }
        }

        // Draws the enemies
        for (LivingEntityData enemy : this.data.getEnemiesData()) {
            Dimension enemyDimension = new Dimension((int) (enemy.getRadius() * 2), (int) (enemy.getRadius() * 2));
            if (this.isVisible(enemy, enemyDimension, scale, cameraOffsetX, cameraOffsetY)) {
                int enemyX = (int) (enemy.getPosition().getX() * scale + cameraOffsetX - enemyDimension.width / 2);
                int enemyY = (int) (enemy.getPosition().getY() * scale + cameraOffsetY - enemyDimension.height / 2);
                int enemyWidth = (int) (enemyDimension.width * scale);
                int enemyHeight = (int) (enemyDimension.height * scale);

                String directionSuffix = enemy.getDirection().getX() <= 0 ? "l" : "r";
                Image tile = this.imageManager.getImage(enemy.getId() + "/" + directionSuffix);

                if (tile != null) {
                    double angle = Math.sin(this.data.getElapsedTime() * ENEMY_OSCILLATION_SPEED) * ENEMY_OSCILLATION_MAX_ANGLE_RAD;
                    double centerX = enemyX + enemyWidth / 2.0;
                    double centerY = enemyY + enemyHeight / 2.0;
                    AffineTransform oldTransform = g2d.getTransform();

                    g2d.rotate(angle, centerX, centerY);

                    if(enemy.isBeingAttacked()) { // white image if the enemy is being attacked
                        BufferedImage whiteImage = new BufferedImage(enemyWidth, enemyHeight, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D whiteG = whiteImage.createGraphics();
                        whiteG.drawImage(tile.getScaledInstance(enemyWidth, enemyHeight, Image.SCALE_SMOOTH), 0, 0, null);
                        whiteG.setComposite(AlphaComposite.SrcAtop);
                        whiteG.setColor(new Color(255, 255, 255, 150));
                        whiteG.fillRect(0, 0, enemyWidth, enemyHeight);
                        whiteG.dispose();
                        g2d.drawImage(whiteImage, enemyX, enemyY, null);
                    }

                    else {
                        g2d.drawImage(tile, enemyX, enemyY, enemyWidth, enemyHeight, null);
                    }
                    
                    g2d.setTransform(oldTransform);
                }
            }
        }

        // Draws the character
        int characterX = (int) (character.getPosition().getX() * scale + cameraOffsetX - characterDimension.width / 2);
        int characterY = (int) (character.getPosition().getY() * scale + cameraOffsetY - characterDimension.height / 2);

        int characterWidth = (int) (characterDimension.width * scale);
        int characterHeight = (int) (characterDimension.height * scale);

        String directionSuffix = "_";
        if (character.getDirection().getX() < 0) {
            directionSuffix += "l";
            this.lastCharacterDirection = "l";
        } else if (character.getDirection().getX() > 0) {
            directionSuffix += "r";
            this.lastCharacterDirection = "r";
        } else {
            directionSuffix += this.lastCharacterDirection;
        }

        Image tile = this.imageManager.getImage(character.getId() + "/" + this.currentCharacterFrame + directionSuffix);

        if(tile != null) {
            if(character.isBeingAttacked()) { // red image if the character is being attacked
                BufferedImage redImage = new BufferedImage(characterWidth, characterHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D redG = redImage.createGraphics();
                redG.drawImage(tile.getScaledInstance(characterWidth, characterHeight, Image.SCALE_SMOOTH), 0, 0, null);
                redG.setComposite(AlphaComposite.SrcAtop);
                redG.setColor(new Color(255, 0, 0, 150));
                redG.fillRect(0, 0, characterWidth, characterHeight);
                redG.dispose();
                g.drawImage(redImage, characterX, characterY, null);
            }
            else {
                g.drawImage(tile, characterX, characterY, characterWidth, characterHeight, null);
            }
        }
        
        long elapsedTime = this.data.getElapsedTime();
        if (character.isMoving() && elapsedTime - this.lastCharacterFrameTime >= CHARACTER_FRAME_DELAY) {
            this.currentCharacterFrame = (this.currentCharacterFrame + 1) % CHARACTER_FRAME_COUNT;
            this.lastCharacterFrameTime = elapsedTime;
        }

        Font font = g.getFont().deriveFont((float) (FONT_SIZE_BASE * scale));

        // Draws the health bar
        Dimension healthBarDimension = new Dimension(HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
        int healthBarWidth = (int) (healthBarDimension.width * scale);
        int healthBarHeight = (int) (healthBarDimension.height * scale);
        int healthBarX = characterX + (characterWidth - healthBarWidth) / 2;
        int healthBarY = characterY + characterHeight + (int) (4 * scale);

        g.setColor(Color.BLACK);
        g.fillRect(healthBarX - 1, healthBarY - 1, healthBarWidth + 2, healthBarHeight + 2);

        int filledWidth = (int) (healthBarWidth * character.getHealth() / character.getMaxHealth());
        g.setColor(Color.RED);
        g.fillRect(healthBarX, healthBarY, filledWidth, healthBarHeight);

        // Draws the level bar
        double levelPercentage = this.data.getLevelPercentage();
        g.setColor(Color.BLACK);
        g.fillRect(0, (int) (LEVEL_BAR_Y_OFFSET * scale), this.getWidth(), (int) (LEVEL_BAR_HEIGHT * scale));
        g.setColor(Color.BLUE);
        g.fillRect(0, (int) (LEVEL_BAR_Y_OFFSET * scale), (int) ((this.getWidth() * levelPercentage) / 100), (int) (LEVEL_BAR_HEIGHT * scale));
        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke((int) (LEVEL_BAR_BORDER_WIDTH * scale)));
        g2d.drawRect(0, (int) (LEVEL_BAR_Y_OFFSET * scale), this.getWidth(), (int) (LEVEL_BAR_HEIGHT * scale));
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("LV " + this.data.getLevel(), (int) (this.getWidth() - LEVEL_TEXT_X_OFFSET * scale), (int) (LEVEL_TEXT_Y_OFFSET * scale));

        g.setColor(Color.WHITE);
        g.setFont(font);

        // Draws the kill counter
        g.drawString(String.valueOf(this.data.getKillCounter()), (int) (this.getWidth() - KILL_COUNTER_X_OFFSET * scale), (int) (COUNTER_Y_OFFSET * scale));
        Image killCounterImage = this.imageManager.getImage("hud/kill");
        if (killCounterImage != null) {
            g.drawImage(killCounterImage, (int) (this.getWidth() - KILL_ICON_X_OFFSET * scale), (int) (ICON_Y_OFFSET * scale), (int) (ICON_SIZE * scale), (int) (ICON_SIZE * scale), null);
        }

        // Draws the coin counter
        g.drawString(String.valueOf(this.data.getCoinCounter()), (int) (this.getWidth() - COIN_COUNTER_X_OFFSET * scale), (int) (COUNTER_Y_OFFSET * scale));
        Image coinCounterImage = this.imageManager.getImage("hud/coin");
        if (coinCounterImage != null) {
            g.drawImage(coinCounterImage, (int) (this.getWidth() - COIN_ICON_X_OFFSET * scale), (int) (ICON_Y_OFFSET * scale), (int) (ICON_SIZE * scale), (int) (ICON_SIZE * scale), null);
        }

        // Draws the timer
        long elapsedSeconds = (elapsedTime / 1000) % 60;
        long elapsedMinutes = (elapsedTime / 1000) / 60;
        String timeString = String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(timeString, (int) ((this.getWidth() - g.getFontMetrics().stringWidth(timeString)) / 2), (int) (LEVEL_BAR_Y_OFFSET * scale + LEVEL_BAR_HEIGHT * scale + TIMER_Y_EXTRA_OFFSET * scale));
    }

    // Checks if the object is visible on the screen
    private boolean isVisible(PositionableData positionable, Dimension objectDimension, double scale, int cameraOffsetX, int cameraOffsetY) {
        int screenX = (int) (positionable.getPosition().getX() * scale + cameraOffsetX);
        int screenY = (int) (positionable.getPosition().getY() * scale + cameraOffsetY);
        int width = (int) (objectDimension.width * scale);
        int height = (int) (objectDimension.height * scale);

        return !(screenX + width < 0 || screenX > this.getWidth() || screenY + height < 0 || screenY > this.getHeight());
    }

    public void setPlayerInputListener(KeyListener inputListener) {
        this.addKeyListener(inputListener);
        this.setFocusable(true);
        this.requestFocus();
    }
}
