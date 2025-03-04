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
import it.unibo.vampireio.controller.CharacterDTO;
import it.unibo.vampireio.controller.CollectibleDTO;
import it.unibo.vampireio.controller.EnemyDTO;
import it.unibo.vampireio.controller.GameController;

class GamePanel extends JPanel {   

    private GameViewImpl view;
    private GameController controller;
    private Map<String, Image> images = new HashMap<>();

    private final Dimension mapTileSize = new Dimension(64, 64); // VANNO MESSE QUA?????????????????????????
    private final Dimension characterSize = new Dimension(64, 64);
    private final Dimension enemySize = new Dimension(64, 64);
    private final Dimension collectibleSize = new Dimension(64, 64);/////boooooohhhhhhhhhh PROBABILMENTE È MENO

    GamePanel(GameViewImpl view, GameController controller) {
        this.view = view;
        this.controller = controller;
        //riempire map immagini FORSE E' MEGLIO METTERLO IN UN METODO INIT???
        try {
            //character

            //enemies

            //collectibles

            //map
            this.images.put("grass", ImageIO.read(getClass().getResource("/images/grass.png")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //get dto data
        //CharacterDTO character = this.controller.getCharacterData();
        //Set<EnemyDTO> enemies = this.controller.getEnemiesData();
        //Set<CollectibleDTO> collectibles = this.controller.getCollectiblesData();

        double scaleFactorX = this.view.getScreenSize().getWidth() / 1280; //non va qui 1280!!!
        double scaleFactorY = this.view.getScreenSize().getHeight() / 720;

        //disegno mappa
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                //draw resized image
                g.drawImage(this.images.get("grass"), (int) (i * this.mapTileSize.getWidth() * scaleFactorX), (int) (j * this.mapTileSize.getHeight() * scaleFactorY), (int) (this.mapTileSize.getWidth() * scaleFactorX), (int) (this.mapTileSize.getHeight() * scaleFactorY), null);
            }
        }
    }
}
