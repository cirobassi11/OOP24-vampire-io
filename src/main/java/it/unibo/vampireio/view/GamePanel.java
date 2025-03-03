package it.unibo.vampireio.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import it.unibo.vampireio.controller.GameController;
import it.unibo.vampireio.model.Character;
import it.unibo.vampireio.model.Collectible;
import it.unibo.vampireio.model.Enemy;

class GamePanel extends JPanel {   

    private GameViewImpl view;
    private GameController controller;
    private Map<String, Image> images = new HashMap<>();

    // VANNO MESSE QUA?????????????????????????
    private final Dimension mapTileSize = new Dimension(64, 64);
    private final Dimension characterSize = new Dimension(64, 64);
    private final Dimension enemySize = new Dimension(64, 64);
    private final Dimension collectibleSize = new Dimension(64, 64);/////boooooohhhhhhhhhh PROBABILMENTE È MENO

    GamePanel(GameViewImpl view, GameController controller) {
        this.view = view;
        this.controller = controller;
        //riempire map immagini
        try {
            this.images.put("grass", ImageIO.read(getClass().getResource("/images/grass.png")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Character character = this.controller.getCharacter();
        Set<Enemy> enemies = this.controller.getEnemies();
        Set<Collectible> collectibles = this.controller.getCollectibles();

        double scaleFactorX = this.view.getScreenSize().getWidth() / 1280; //sistemareee
        double scaleFactorY = this.view.getScreenSize().getHeight() / 720;

        System.out.println("SCALE FACTOR X: " + scaleFactorX);
        System.out.println("SCALE FACTOR Y: " + scaleFactorY);

        //disegno mappa
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                //draw resized image
                g.drawImage(this.images.get("grass"), (int) (i * this.mapTileSize.getWidth() * scaleFactorX), (int) (j * this.mapTileSize.getHeight() * scaleFactorY), (int) (this.mapTileSize.getWidth() * scaleFactorX), (int) (this.mapTileSize.getHeight() * scaleFactorY), null);
            }
        }
    }
}
