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
import it.unibo.vampireio.controller.ItemData;
import it.unibo.vampireio.controller.PositionableData;
import it.unibo.vampireio.controller.LivingEntityData;

class GamePanel extends JPanel {
    private static final long serialVersionUID = 1L;

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

    private static final String LEFT_DIRECTION = "l";

    private static final Color TRANSLUCENT_WHITE = new Color(255, 255, 255, 150);

    private static final double ENEMY_OSCILLATION_MAX_ANGLE_RAD = Math.toRadians(10);
    private static final double ENEMY_OSCILLATION_SPEED = 0.005;

    private final GameViewImpl view;
    private GameData data;

    private final ImageManager imageManager;

    private String lastCharacterDirection = LEFT_DIRECTION;
    private int currentCharacterFrame;
    private long lastCharacterFrameTime;

    GamePanel(final GameViewImpl view) {
        this.view = view;
        this.imageManager = new ImageManager(this.view);
        setFocusable(true);
    }

    void setData(final GameData data) {
        if (this.data == null || data.getElapsedTime() < this.data.getElapsedTime()) {
            this.currentCharacterFrame = 0;
            this.lastCharacterFrameTime = 0;
        }
        this.data = data;
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);

        final Dimension fov = this.data.getVisibleMapSizeData().getDimension();
        final double scale = this.view.getFrameSize().getWidth() / fov.getWidth();

        final LivingEntityData character = this.data.getCharacterData();
        final Dimension characterDimension = new Dimension((int) (character.getRadius() * 2), (int) (character.getRadius() * 2));

        final int screenCenterX = (int) (this.getWidth() / 2);
        final int screenCenterY = (int) (this.getHeight() / 2);

        final int cameraOffsetX = (int) (screenCenterX - character.getPosition().getX() * scale);
        final int cameraOffsetY = (int) (screenCenterY - character.getPosition().getY() * scale);

        final int startTileX = (int) Math.floor((character.getPosition().getX() - screenCenterX / scale) 
                                / characterDimension.width);
        final int startTileY = (int) Math.floor((character.getPosition().getY() - screenCenterY / scale) 
                                / characterDimension.height);

        final int tilesNumX = (int) Math.ceil(fov.getWidth() / MAP_TILE_WIDTH) + 2;
        final int tilesNumY = (int) Math.ceil(fov.getHeight() / MAP_TILE_HEIGHT) + 2;

        // Draws the map
        for (int y = 0; y < tilesNumY; y++) {
            for (int x = 0; x < tilesNumX; x++) {
                final int worldX = (startTileX + x) * MAP_TILE_WIDTH;
                final int worldY = (startTileY + y) * MAP_TILE_HEIGHT;

                final int screenX = (int) (worldX * scale + cameraOffsetX);
                final int screenY = (int) (worldY * scale + cameraOffsetY);

                final Image tile = this.imageManager.getImage("map");
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
        for (final PositionableData collectible : this.data.getCollectiblesData()) {
            final Dimension collectibleDimension = 
            new Dimension((int) (collectible.getRadius() * 2), (int) (collectible.getRadius() * 2));
            if (this.isVisible(collectible, collectibleDimension, scale, cameraOffsetX, cameraOffsetY)) {
                final int collectibleX = (int) (collectible.getPosition().getX() * scale 
                                    + cameraOffsetX - collectibleDimension.width / 2);
                final int collectibleY = (int) (collectible.getPosition().getY() * scale 
                                    + cameraOffsetY - collectibleDimension.height / 2);
                final Image tile = this.imageManager.getImage(collectible.getId());
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
        final Graphics2D g2d = (Graphics2D) g;
        for (final PositionableData attack : this.data.getAttacksData()) {
            final Dimension attackDimension = new Dimension((int) (attack.getRadius() * 2), (int) (attack.getRadius() * 2));
            if (this.isVisible(attack, attackDimension, scale, cameraOffsetX, cameraOffsetY)) {
                final int projectileX = (int) (attack.getPosition().getX() * scale + cameraOffsetX - attackDimension.width / 2);
                final int projectileY = (int) (attack.getPosition().getY() * scale + cameraOffsetY - attackDimension.height / 2);
                final Image tile = this.imageManager.getImage(attack.getId());
                if (tile != null) {
                    final int width = (int) (attackDimension.width * scale);
                    final int height = (int) (attackDimension.height * scale);
                    final double rotationAngle = Math.atan2(attack.getDirection().getY(), attack.getDirection().getX());
                    final AffineTransform transform = new AffineTransform();
                    transform.translate(projectileX + width / 2, projectileY + height / 2);
                    transform.rotate(rotationAngle);
                    transform.translate(-width / 2, -height / 2);
                    g2d.drawImage(tile.getScaledInstance(width, height, Image.SCALE_SMOOTH), transform, null);
                }
            }
        }

        // Draws the enemies
        for (final LivingEntityData enemy : this.data.getEnemiesData()) {
            final Dimension enemyDimension = new Dimension((int) (enemy.getRadius() * 2), (int) (enemy.getRadius() * 2));
            if (this.isVisible(enemy, enemyDimension, scale, cameraOffsetX, cameraOffsetY)) {
                final int enemyX = (int) (enemy.getPosition().getX() * scale + cameraOffsetX - enemyDimension.width / 2);
                final int enemyY = (int) (enemy.getPosition().getY() * scale + cameraOffsetY - enemyDimension.height / 2);
                final int enemyWidth = (int) (enemyDimension.width * scale);
                final int enemyHeight = (int) (enemyDimension.height * scale);

                final String directionSuffix = enemy.getDirection().getX() <= 0 ? "l" : "r";
                final Image tile = this.imageManager.getImage(enemy.getId() + "/" + directionSuffix);

                if (tile != null) {
                    final double angle = Math.sin(this.data.getElapsedTime() * ENEMY_OSCILLATION_SPEED) 
                                         * ENEMY_OSCILLATION_MAX_ANGLE_RAD;
                    final double centerX = enemyX + enemyWidth / 2.0;
                    final double centerY = enemyY + enemyHeight / 2.0;
                    final AffineTransform oldTransform = g2d.getTransform();

                    g2d.rotate(angle, centerX, centerY);

                    if (enemy.isBeingAttacked()) { // white image if the enemy is being attacked
                        final BufferedImage whiteImage = new BufferedImage(enemyWidth, enemyHeight, BufferedImage.TYPE_INT_ARGB);
                        final Graphics2D whiteG = whiteImage.createGraphics();
                        whiteG.drawImage(tile.getScaledInstance(enemyWidth, enemyHeight, Image.SCALE_SMOOTH), 0, 0, null);
                        whiteG.setComposite(AlphaComposite.SrcAtop);
                        whiteG.setColor(TRANSLUCENT_WHITE);
                        whiteG.fillRect(0, 0, enemyWidth, enemyHeight);
                        whiteG.dispose();
                        g2d.drawImage(whiteImage, enemyX, enemyY, null);
                    } else {
                        g2d.drawImage(tile, enemyX, enemyY, enemyWidth, enemyHeight, null);
                    }

                    g2d.setTransform(oldTransform);
                }
            }
        }

        // Draws the character
        final int characterX = (int) (character.getPosition().getX() * scale + cameraOffsetX - characterDimension.width / 2);
        final int characterY = (int) (character.getPosition().getY() * scale + cameraOffsetY - characterDimension.height / 2);

        final int characterWidth = (int) (characterDimension.width * scale);
        final int characterHeight = (int) (characterDimension.height * scale);

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

        final Image tile = this.imageManager.getImage(character.getId() + "/" + this.currentCharacterFrame + directionSuffix);

        if (tile != null) {
            if (character.isBeingAttacked()) { // red image if the character is being attacked
                final BufferedImage redImage = new BufferedImage(characterWidth, characterHeight, BufferedImage.TYPE_INT_ARGB);
                final Graphics2D redG = redImage.createGraphics();
                redG.drawImage(tile.getScaledInstance(characterWidth, characterHeight, Image.SCALE_SMOOTH), 0, 0, null);
                redG.setComposite(AlphaComposite.SrcAtop);
                redG.setColor(new Color(255, 0, 0, 150));
                redG.fillRect(0, 0, characterWidth, characterHeight);
                redG.dispose();
                g.drawImage(redImage, characterX, characterY, null);
            } else {
                g.drawImage(tile, characterX, characterY, characterWidth, characterHeight, null);
            }
        }

        final long elapsedTime = this.data.getElapsedTime();
        if (character.isMoving() && elapsedTime - this.lastCharacterFrameTime >= CHARACTER_FRAME_DELAY) {
            this.currentCharacterFrame = (this.currentCharacterFrame + 1) % CHARACTER_FRAME_COUNT;
            this.lastCharacterFrameTime = elapsedTime;
        }

        final Font font = g.getFont().deriveFont((float) (FONT_SIZE_BASE * scale));

        // Draws the health bar
        final Dimension healthBarDimension = new Dimension(HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
        final int healthBarWidth = (int) (healthBarDimension.width * scale);
        final int healthBarHeight = (int) (healthBarDimension.height * scale);
        final int healthBarX = characterX + (characterWidth - healthBarWidth) / 2;
        final int healthBarY = characterY + characterHeight + (int) (4 * scale);

        g.setColor(Color.BLACK);
        g.fillRect(healthBarX - 1, healthBarY - 1, healthBarWidth + 2, healthBarHeight + 2);

        final int filledWidth = (int) (healthBarWidth * character.getHealth() / character.getMaxHealth());
        g.setColor(Color.RED);
        g.fillRect(healthBarX, healthBarY, filledWidth, healthBarHeight);

        // Draws the level bar
        final double levelPercentage = this.data.getLevelPercentage();
        g.setColor(Color.BLACK);
        g.fillRect(0, (int) (LEVEL_BAR_Y_OFFSET * scale), this.getWidth(), (int) (LEVEL_BAR_HEIGHT * scale));
        g.setColor(Color.BLUE);
        g.fillRect(0, (int) (LEVEL_BAR_Y_OFFSET * scale), (int) ((this.getWidth() * levelPercentage) / 100),
        (int) (LEVEL_BAR_HEIGHT * scale));

        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke((int) (LEVEL_BAR_BORDER_WIDTH * scale)));
        g2d.drawRect(0, (int) (LEVEL_BAR_Y_OFFSET * scale), this.getWidth(), 
        (int) (LEVEL_BAR_HEIGHT * scale));

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("LV " + this.data.getLevel(), (int) (this.getWidth() - LEVEL_TEXT_X_OFFSET * scale), 
        (int) (LEVEL_TEXT_Y_OFFSET * scale));

        g.setColor(Color.WHITE);
        g.setFont(font);

        // Draws the kill counter
        g.drawString(String.valueOf(this.data.getKillCounter()), 
        (int) (this.getWidth() - KILL_COUNTER_X_OFFSET * scale), (int) (COUNTER_Y_OFFSET * scale));
        final Image killCounterImage = this.imageManager.getImage("hud/kill");
        if (killCounterImage != null) {
            g.drawImage(killCounterImage, (int) (this.getWidth() - KILL_ICON_X_OFFSET * scale), 
            (int) (ICON_Y_OFFSET * scale), (int) (ICON_SIZE * scale), (int) (ICON_SIZE * scale), null);
        }

        // Draws the coin counter
        g.drawString(String.valueOf(this.data.getCoinCounter()), 
        (int) (this.getWidth() - COIN_COUNTER_X_OFFSET * scale), (int) (COUNTER_Y_OFFSET * scale));
        final Image coinCounterImage = this.imageManager.getImage("hud/coin");
        if (coinCounterImage != null) {
            g.drawImage(coinCounterImage, (int) (this.getWidth() - COIN_ICON_X_OFFSET * scale), 
            (int) (ICON_Y_OFFSET * scale), (int) (ICON_SIZE * scale), (int) (ICON_SIZE * scale), null);
        }

        // Draws the timer
        final long elapsedSeconds = (elapsedTime / 1000) % 60;
        final long elapsedMinutes = (elapsedTime / 1000) / 60;
        final String timeString = String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(timeString, (int) ((this.getWidth() - g.getFontMetrics().stringWidth(timeString)) / 2), 
        (int) (LEVEL_BAR_Y_OFFSET * scale + LEVEL_BAR_HEIGHT * scale + TIMER_Y_EXTRA_OFFSET * scale));

        // Draws the weapons

        //disegna icone armi in alto a sinistra
        int weaponIconX = 1;
        final int weaponIconY = LEVEL_BAR_HEIGHT + LEVEL_BAR_BORDER_WIDTH + 1;
        for (final ItemData item : this.data.getItemsData()) {
            final Image itemIcon = this.imageManager.getImage(item.getId());
            if (itemIcon != null) {
                g.drawImage(itemIcon, (int) (weaponIconX * scale), (int) (weaponIconY * scale),
                (int) (ICON_SIZE * scale), (int) (ICON_SIZE * scale), null);
                weaponIconX += ICON_SIZE + 1;
            }
        }
    }

    // Checks if the object is visible on the screen
    private boolean isVisible(
        final PositionableData positionable,
        final Dimension objectDimension,
        final double scale,
        final int cameraOffsetX,
        final int cameraOffsetY) {

            final int screenX = (int) (positionable.getPosition().getX() * scale + cameraOffsetX);
            final int screenY = (int) (positionable.getPosition().getY() * scale + cameraOffsetY);
            final int width = (int) (objectDimension.width * scale);
            final int height = (int) (objectDimension.height * scale);

        return !(screenX + width < 0 || screenX > this.getWidth() || screenY + height < 0 || screenY > this.getHeight());
    }

    public void setPlayerInputListener(final KeyListener inputListener) {
        this.addKeyListener(inputListener);
        this.setFocusable(true);
        this.requestFocus();
    }
}
