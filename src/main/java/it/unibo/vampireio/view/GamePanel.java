package it.unibo.vampireio.view;

import java.awt.Dimension;
import java.awt.Graphics;
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
                g.drawImage(this.imageManager.getImage("grass"), tileX, tileY, (int) (tileSize * scale), (int) (tileSize * scale), null);
            }
        }

        //disegnare le livingentity

        //disegnare i collectible

        //disegnare i proiettili

        //disegnare gli attacchi ad area
    }
}