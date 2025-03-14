package it.unibo.vampireio.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import it.unibo.vampireio.controller.DTO;
import it.unibo.vampireio.controller.PositionableDTO;

class GamePanel extends JPanel {

    private GameViewImpl view;
    private DTO data;
    private final int tileSize = 64;
    private ImageManager imageManager = new ImageManager();

    GamePanel(GameViewImpl view) {
        this.view = view;
    }

    public void setData(DTO data) {
        this.data = data;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Dimension visibleDimension = this.data.getVisualSizeData().getDimension();
        
        double scale = this.view.getScreenSize().getWidth() / visibleDimension.getWidth();

        int centerX = visibleDimension.width / 2;
        int centerY = visibleDimension.height / 2;

        PositionableDTO character = this.data.getCharacterData();

        // Calculate the offset
        int offsetX = (int) (centerX - character.getPosition().getX() * scale);
        int offsetY = (int) (centerY - character.getPosition().getY() * scale);

        // Calculate the start map tile position
        int startTileX = (int) ((character.getPosition().getX() * scale) - centerX) / (int) (tileSize * scale);
        int startTileY = (int) ((character.getPosition().getY() * scale) - centerY) / (int) (tileSize * scale);

        // Calculate the number of tiles to draw
        int tileNumberX = (int) (visibleDimension.height / (tileSize * scale)) + 1;
        int tileNumberY = (int) (visibleDimension.width / (tileSize * scale)) + 1;

        // Draws the map
        for (int i = -1; i <= tileNumberY; i++) {
            for (int j = -1; j <= tileNumberX; j++) {
                int tileX = (int) (((i + startTileX) * tileSize * scale + offsetX) % (tileSize * scale));
                int tileY = (int) (((j + startTileY) * tileSize * scale + offsetY) % (tileSize * scale));
                Image tile = this.imageManager.getImage("grass");
                if(tile != null) {
                    g.drawImage(tile, tileX, tileY, (int) (tileSize * scale), (int) (tileSize * scale), null);
                }
            }
        }

        //Draws the collectibles
        for(PositionableDTO collectible : this.data.getCollectiblesData()) {
            int collectibleX = (int) (collectible.getPosition().getX() * scale + offsetX);
            int collectibleY = (int) (collectible.getPosition().getY() * scale + offsetY);
            Image tile = this.imageManager.getImage(collectible.getId());
            if(tile != null) {
                g.drawImage(tile, collectibleX, collectibleY, (int) (tileSize * scale), (int) (tileSize * scale), null);
            }
        }

        //Draws the projectiles
        for(PositionableDTO projectile : this.data.getProjectilesData()) {
            int projectileX = (int) (projectile.getPosition().getX() * scale + offsetX);
            int projectileY = (int) (projectile.getPosition().getY() * scale + offsetY);
            Image tile = this.imageManager.getImage(projectile.getId());
            if(tile != null) {
                g.drawImage(tile, projectileX, projectileY, (int) (tileSize * scale), (int) (tileSize * scale), null); // TODO: DA RUOTARE IN BASE ALLA DIREZIONE
            }
        }

        //Draws the area attacks
        for(PositionableDTO areaAttack : this.data.getAreaAttacksData()) {
            int areaAttackX = (int) (areaAttack.getPosition().getX() * scale + offsetX);
            int areaAttackY = (int) (areaAttack.getPosition().getY() * scale + offsetY);
            Image tile = this.imageManager.getImage(areaAttack.getId());
            if(tile != null) {
                g.drawImage(tile, areaAttackX, areaAttackY, (int) (tileSize * scale), (int) (tileSize * scale), null); //TODO: ANIMAZIONI????
            }
        }

        //Draws the enemies
        for(PositionableDTO enemy : this.data.getEnemiesData()) {
            int enemyX = (int) (enemy.getPosition().getX() * scale + offsetX);
            int enemyY = (int) (enemy.getPosition().getY() * scale + offsetY);
            Image tile = this.imageManager.getImage(enemy.getId());
            if(tile != null) {
                g.drawImage(tile, enemyX, enemyY, (int) (tileSize * scale), (int) (tileSize * scale), null); //TODO: DA GIRARE A DESTRA O SINISTRA + ANIMAZIONI + DIVENTANO BIANCHI SE COLPITI
            }
        }

        //Draws the character
        int characterX = (int) (character.getPosition().getX() * scale + offsetX);
        int characterY = (int) (character.getPosition().getY() * scale + offsetY);
        Image tile = this.imageManager.getImage(character.getId());
        if(tile != null) {
            g.drawImage(tile, characterX, characterY, (int) (tileSize * scale), (int) (tileSize * scale), null); //TODO: DA GIRARE A DESTRA O SINISTRA + ANIMAZIONI + DIVENTA ROSSO SE COLPITO
        }
    }
}