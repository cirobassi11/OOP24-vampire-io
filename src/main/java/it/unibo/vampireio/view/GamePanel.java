package it.unibo.vampireio.view;

import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;
import it.unibo.vampireio.controller.GameController;
import it.unibo.vampireio.controller.PositionableDTO;

class GamePanel extends JPanel {

    private GameViewImpl view;
    private GameController controller;

    private List<PositionableDTO> positionables;

    private final int tileSize = 64;

    private ImageManager imageManager = new ImageManager();

    GamePanel(GameViewImpl view, GameController controller) {
        this.view = view;
        this.controller = controller;
    }

    public void setPositionables(List<PositionableDTO> positionables) {
        this.positionables = positionables;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        double scale = this.view.getScaleFactor();

        int screenWidth = (int) this.controller.getVisualSizeData().getDimension().getWidth();
        int screenHeight = (int) this.controller.getVisualSizeData().getDimension().getHeight();
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        PositionableDTO character = this.positionables.get(0);

        // Offset basato sulla posizione del Character
        int offsetX = (int) (centerX - character.getPosition().getX() * scale);
        int offsetY = (int) (centerY - character.getPosition().getY() * scale);

        // Calcolo della posizione iniziale dei tile
        int startTileX = (int) ((character.getPosition().getX() * scale) - centerX) / (int) (tileSize * scale);
        int startTileY = (int) ((character.getPosition().getY() * scale) - centerY) / (int) (tileSize * scale);

        // Disegna la mappa infinita
        for (int i = -1; i <= screenWidth / (tileSize * scale) + 1; i++) {
            for (int j = -1; j <= screenHeight / (tileSize * scale) + 1; j++) {
                int tileX = (int) (((i + startTileX) * tileSize * scale + offsetX) % (tileSize * scale));
                int tileY = (int) (((j + startTileY) * tileSize * scale + offsetY) % (tileSize * scale));
                g.drawImage(this.imageManager.getImage("grass"), tileX, tileY, (int) (tileSize * scale), (int) (tileSize * scale), null);
            }
        }

        // Disegna i personaggi
        for (PositionableDTO positionable : this.positionables) {
            int x = (int) (positionable.getPosition().getX() * scale + offsetX);
            int y = (int) (positionable.getPosition().getY() * scale + offsetY);
            g.drawImage(this.imageManager.getImage(positionable.getId()), x, y, (int) (tileSize * scale), (int) (tileSize * scale), null);
        }
    }
}