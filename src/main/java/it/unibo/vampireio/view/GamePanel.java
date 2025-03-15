package it.unibo.vampireio.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import it.unibo.vampireio.controller.DTO;
import it.unibo.vampireio.controller.PositionableDTO;

class GamePanel extends JPanel {

    private GameViewImpl view;
    private DTO data;
    private final int tileSize = 64;
    private ImageManager imageManager = new ImageManager();

    private String lastCharacterDirection = "l";

    GamePanel(GameViewImpl view) {
        this.view = view;
    }

    public void setData(DTO data) {
        this.data = data;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Dimension fov = this.data.getVisualSizeData().getDimension();
        
        double scale = this.view.getScreenSize().getWidth() / fov.getWidth();

        int centerX = fov.width / 2;
        int centerY = fov.height / 2;

        PositionableDTO character = this.data.getCharacterData();

        // Calculate the offset
        int offsetX = (int) (centerX - character.getPosition().getX() * scale);
        int offsetY = (int) (centerY - character.getPosition().getY() * scale);

        // Calculate the start map tile position
        int startTileX = (int) ((character.getPosition().getX() * scale) - centerX) / (int) (tileSize * scale);
        int startTileY = (int) ((character.getPosition().getY() * scale) - centerY) / (int) (tileSize * scale);

        // Calculate the number of map tiles to draw
        int tileNumberX = (int) (fov.height / (tileSize * scale)) + 1;
        int tileNumberY = (int) (fov.width / (tileSize * scale)) + 1;

        // Draws the map
        for (int i = -1; i <= tileNumberY; i++) {
            for (int j = -1; j <= tileNumberX; j++) {
                int tileX = (int) (((i + startTileX) * tileSize * scale + offsetX) % (tileSize * scale));
                int tileY = (int) (((j + startTileY) * tileSize * scale + offsetY) % (tileSize * scale));
                Image tile = this.imageManager.getImage("map");
                if(tile != null) {
                    g.drawImage(tile, tileX, tileY, (int) (tileSize * scale), (int) (tileSize * scale), null);
                }
            }
        }

        //Draws the collectibles
        for(PositionableDTO collectible : this.data.getCollectiblesData()) {
            if(this.isVisible(collectible, fov, offsetX, offsetY)) {
                int collectibleX = (int) (collectible.getPosition().getX() * scale + offsetX);
                int collectibleY = (int) (collectible.getPosition().getY() * scale + offsetY);
                Image tile = this.imageManager.getImage(collectible.getId());
                if(tile != null) {
                    g.drawImage(tile, collectibleX, collectibleY, (int) (tileSize * scale), (int) (tileSize * scale), null);
                }
            }
        }

        //Draws the projectiles
        Graphics2D g2d = (Graphics2D) g;
        for (PositionableDTO projectile : this.data.getProjectilesData()) { //TODO: ANIMAZIONI??
            if(this.isVisible(projectile, fov, offsetX, offsetY)) {
                int projectileX = (int) (projectile.getPosition().getX() * scale + offsetX);
                int projectileY = (int) (projectile.getPosition().getY() * scale + offsetY);
                Image tile = this.imageManager.getImage(projectile.getId());
                if (tile != null) { // Da controllare
                    int width = (int) (tileSize * scale);
                    int height = (int) (tileSize * scale);
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
        for(PositionableDTO areaAttack : this.data.getAreaAttacksData()) {
            if(this.isVisible(areaAttack, fov, offsetX, offsetY)) {
                int areaAttackX = (int) (areaAttack.getPosition().getX() * scale + offsetX);
                int areaAttackY = (int) (areaAttack.getPosition().getY() * scale + offsetY);
                Image tile = this.imageManager.getImage(areaAttack.getId());
                if(tile != null) {
                    g.drawImage(tile, areaAttackX, areaAttackY, (int) (tileSize * scale), (int) (tileSize * scale), null); //TODO: ANIMAZIONI????
                }
            }
        }

        //Draws the enemies
        for(PositionableDTO enemy : this.data.getEnemiesData()) {
            if(this.isVisible(enemy, fov, offsetX, offsetY)) {
                int enemyX = (int) (enemy.getPosition().getX() * scale + offsetX);
                int enemyY = (int) (enemy.getPosition().getY() * scale + offsetY);
                String directionSuffix = "_" + (enemy.getDirection().getX() <= 0 ? "l" : "r");
                Image tile = this.imageManager.getImage(enemy.getId() + directionSuffix);
                if(tile != null) {
                    g.drawImage(tile, enemyX, enemyY, (int) (tileSize * scale), (int) (tileSize * scale), null); //TODO: ANIMAZIONI + DIVENTANO BIANCHI SE COLPITI
                }
            }
        }

        //Draws the character
        int characterX = (int) (character.getPosition().getX() * scale + offsetX);
        int characterY = (int) (character.getPosition().getY() * scale + offsetY);

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
        Image tile = this.imageManager.getImage(character.getId() + directionSuffix);
        if(tile != null) {
            g.drawImage(tile, characterX, characterY, (int) (tileSize * scale), (int) (tileSize * scale), null); //TODO: ANIMAZIONI + DIVENTA ROSSO SE COLPITO
        }
    }

    private boolean isVisible(PositionableDTO positionable, Dimension fov, int offsetX, int offsetY) {
        return true;
        //TODO
    }
}