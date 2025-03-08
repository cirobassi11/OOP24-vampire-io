package it.unibo.vampireio.view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import it.unibo.vampireio.controller.CharacterDTO;
import it.unibo.vampireio.controller.CollectibleDTO;
import it.unibo.vampireio.controller.EnemyDTO;
import it.unibo.vampireio.controller.GameController;

class GamePanel extends JPanel {

    private GameViewImpl view;
    private GameController controller;
    private Map<String, Image> images = new HashMap<>();

    private final int tileSize = 64;

    GamePanel(GameViewImpl view, GameController controller) {
        this.view = view;
        this.controller = controller;

        try {
            // Caricare le immagini
            this.images.put("grass", ImageIO.read(getClass().getResource("/images/grass.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Ottieni dati dal controller
        CharacterDTO character = this.controller.getCharacterData();
        Set<EnemyDTO> enemies = this.controller.getEnemiesData();
        Set<CollectibleDTO> collectibles = this.controller.getCollectiblesData();
        
        double scale = this.view.getScaleFactor();

        int screenWidth = (int) this.controller.getVisualSizeData().getDimension().getWidth();
        int screenHeight = (int) this.controller.getVisualSizeData().getDimension().getHeight();
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

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
                
                g.drawImage(this.images.get("grass"), tileX, tileY, (int) (tileSize * scale), (int) (tileSize * scale), null);
            }
        }
    }
}