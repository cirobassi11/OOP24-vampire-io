package it.unibo.vampireio.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import it.unibo.vampireio.controller.GameData;
import it.unibo.vampireio.controller.PositionableData;
import it.unibo.vampireio.controller.LivingEntityData;

class GamePanel extends JPanel {

    private GameViewImpl view;
    private GameData data;

    private ImageManager imageManager = new ImageManager();
    
    private final Dimension mapTileDimension = new Dimension(64, 64);
    private final Dimension livingEntityDimension = new Dimension(64, 64);
    private final Dimension projectileDimension = new Dimension(32, 32);
    private final Dimension areaAttackDimension = new Dimension(64, 64);
    private final Dimension collectibleDimension = new Dimension(32, 32);
    private final Dimension healthBarDimension = new Dimension(42, 7);

    private String lastCharacterDirection = "l";
    private int currentCharacterFrame = 0;
    private final int characterFrames = 3;
    private long lastCharacterFrameTime = 0;
    private final long characterFrameRate = 5;
    private final long characterFrameDelay = 1000 / this.characterFrameRate;

    GamePanel(GameViewImpl view) {
        this.view = view;
    }

    void setData(GameData data) {
        this.data = data;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Dimension fov = this.data.getVisibleMapSizeData().getDimension();
        
        double scale = this.view.getFrameSize().getWidth() / fov.getWidth();

        LivingEntityData character = this.data.getCharacterData();

        int screenCenterX = this.getWidth() / 2 - this.livingEntityDimension.width / 2;
        int screenCenterY = this.getHeight() / 2 - this.livingEntityDimension.height / 2;

        int cameraOffsetX = (int) (screenCenterX - character.getPosition().getX() * scale);
        int cameraOffsetY = (int) (screenCenterY - character.getPosition().getY() * scale);

        // Start map tile position
        int startTileX = (int) Math.floor((character.getPosition().getX() - screenCenterX / scale) / this.livingEntityDimension.width);
        int startTileY = (int) Math.floor((character.getPosition().getY() - screenCenterY / scale) / this.livingEntityDimension.height);

        // How many tiles to draw
        int tilesNumX = (int) Math.ceil(fov.getWidth() / this.mapTileDimension.width) + 2;
        int tilesNumY = (int) Math.ceil(fov.getHeight() / this.mapTileDimension.height) + 2;

        // Draw the tiles
        for (int y = 0; y < tilesNumY; y++) {
            for (int x = 0; x < tilesNumX; x++) {
                int worldX = (startTileX + x) * this.mapTileDimension.width;
                int worldY = (startTileY + y) * this.mapTileDimension.height;

                int screenX = (int) (worldX * scale + cameraOffsetX);
                int screenY = (int) (worldY * scale + cameraOffsetY);

                Image tile = this.imageManager.getImage("map");
                if (tile != null) {
                    g.drawImage(tile, screenX - 1, screenY - 1, (int) (this.mapTileDimension.width * scale) + 2, (int) (this.mapTileDimension.height * scale) + 2, null);
                }
            }
        }

        //Draws the collectibles
        for(PositionableData collectible : this.data.getCollectiblesData()) {
            if(this.isVisible(collectible, this.collectibleDimension, scale, cameraOffsetX, cameraOffsetY)) {
                int collectibleX = (int) (collectible.getPosition().getX() * scale + cameraOffsetX);
                int collectibleY = (int) (collectible.getPosition().getY() * scale + cameraOffsetY);
                Image tile = this.imageManager.getImage(collectible.getId());
                if(tile != null) {
                    g.drawImage(tile, collectibleX, collectibleY, (int) (this.collectibleDimension.width * scale), (int) (this.collectibleDimension.height * scale), null); //TODO: ANIMAZION
                }
            }
        }

        //Draws the projectiles
        Graphics2D g2d = (Graphics2D) g;
        for (PositionableData projectile : this.data.getProjectilesData()) { //TODO: ANIMAZIONI??
            if(this.isVisible(projectile, this.projectileDimension, scale, cameraOffsetX, cameraOffsetY)) {
                int projectileX = (int) (projectile.getPosition().getX() * scale + cameraOffsetX);
                int projectileY = (int) (projectile.getPosition().getY() * scale + cameraOffsetY);
                Image tile = this.imageManager.getImage(projectile.getId());
                if (tile != null) {
                    int width = (int) (this.projectileDimension.width * scale);
                    int height = (int) (this.projectileDimension.height * scale);
                    double rotationAngle = Math.atan2(projectile.getDirection().getY(), projectile.getDirection().getX());
                    AffineTransform transform = new AffineTransform();
                    transform.translate(projectileX + width / 2, projectileY + height / 2);
                    transform.rotate(rotationAngle);
                    transform.translate(-width / 2, -height / 2);
                    g2d.drawImage(tile, transform, null);
                }
            }
        }

        //Draws the area attacks
        for(PositionableData areaAttack : this.data.getAreaAttacksData()) {
            if(this.isVisible(areaAttack, this.areaAttackDimension, scale, cameraOffsetX, cameraOffsetY)) {
                int areaAttackX = (int) (areaAttack.getPosition().getX() * scale + cameraOffsetX);
                int areaAttackY = (int) (areaAttack.getPosition().getY() * scale + cameraOffsetY);
                Image tile = this.imageManager.getImage(areaAttack.getId());
                if(tile != null) {
                    g.drawImage(tile, areaAttackX, areaAttackY, (int) (this.areaAttackDimension.width * scale), (int) (this.areaAttackDimension.height * scale), null); //TODO: AN
                }
            }
        }

        //Draws the enemies
        for(LivingEntityData enemy : this.data.getEnemiesData()) {
            if(this.isVisible(enemy, this.livingEntityDimension, scale, cameraOffsetX, cameraOffsetY)) {
                int enemyX = (int) (enemy.getPosition().getX() * scale + cameraOffsetX);
                int enemyY = (int) (enemy.getPosition().getY() * scale + cameraOffsetY);
                String directionSuffix = "_" + (enemy.getDirection().getX() <= 0 ? "l" : "r");
                Image tile = this.imageManager.getImage(enemy.getId() + directionSuffix);
                if(tile != null) {
                    g.drawImage(tile, enemyX, enemyY, (int) (this.livingEntityDimension.width * scale), (int) (this.livingEntityDimension.height * scale), null); //TODO: ANIMAZIONI??? + DIVENTANO BIANCHI SE COLPITI
                }
            }
        }

        //Draws the character
        int characterX = (int) (character.getPosition().getX() * scale + cameraOffsetX);
        int characterY = (int) (character.getPosition().getY() * scale + cameraOffsetY);

        String directionSuffix = "_";
        if(character.getDirection().getX() < 0) {
            directionSuffix += "l";
            this.lastCharacterDirection = "l";
        } else if (character.getDirection().getX() > 0) {
            directionSuffix += "r";
            this.lastCharacterDirection = "r";
        }
        else {
            directionSuffix += this.lastCharacterDirection;
        }
        Image tile = this.imageManager.getImage(character.getId() + "/" + this.currentCharacterFrame + directionSuffix);
        if(tile != null) {
            g.drawImage(tile, characterX, characterY, (int) (this.livingEntityDimension.width * scale), (int) (this.livingEntityDimension.height * scale), null); //TODO: DIVENTA ROSSO SE COLPITO
        }
        
        long elapsedTime = this.data.getElapsedTime();
        if (character.isMoving() && elapsedTime - this.lastCharacterFrameTime >= this.characterFrameDelay) {
            this.currentCharacterFrame = (this.currentCharacterFrame + 1) % this.characterFrames;
            this.lastCharacterFrameTime = elapsedTime;
        }

        //Draws the HUD
        //Healthbar
        int healthPercent = (int) ((character.getHealth() / character.getMaxHealth()) * 100);
        healthPercent = (int) Math.round(healthPercent / 25.0) * 25;                            // arrotonda a multiplo di di 25
        Image healthBarImage = this.imageManager.getImage("hud/healthbar/" + healthPercent);
        if (healthBarImage != null) {
            g.drawImage(healthBarImage, characterX + (int) ((this.livingEntityDimension.width - this.healthBarDimension.width) * scale) / 2, characterY + (int) (this.livingEntityDimension.height * scale), (int) (this.healthBarDimension.width * scale), (int) (this.healthBarDimension.height * scale), null);
        }

        //Level bar
        int levelPercentage = this.data.getLevelPercentage();
        g.setColor(Color.BLACK);
        g.fillRect(0, (int) (2 * scale), this.getWidth(), (int) (20 * scale));
        g.setColor(Color.BLUE);
        g.fillRect(0, (int) (2 * scale), (int) ((this.getWidth() * levelPercentage) / 100), (int) (20 * scale));
        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke((int) (3 * scale)));
        g2d.drawRect(0, (int) (2 * scale), this.getWidth(), (int) (20 * scale));
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont((float) (15 * scale)));
        g.drawString("LV " + this.data.getLevel(), (int) (this.getWidth() - 50 * scale), (int) (15 * scale));
        
    }

    // True if the object is visible on the screen
    private boolean isVisible(PositionableData positionable, Dimension objectDimension, double scale, int cameraOffsetX, int cameraOffsetY) {
        int screenX = (int) (positionable.getPosition().getX() * scale + cameraOffsetX);
        int screenY = (int) (positionable.getPosition().getY() * scale + cameraOffsetY);
        int width = (int) (objectDimension.width * scale);
        int height = (int) (objectDimension.height * scale);

        return !(screenX + width < 0 || screenX > this.getWidth() || screenY + height < 0 || screenY > this.getHeight());
    }
}