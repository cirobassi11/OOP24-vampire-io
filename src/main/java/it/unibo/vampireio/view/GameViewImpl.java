package it.unibo.vampireio.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameViewImpl implements GameView {
    
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    private static final double SCALE = 0.666;
    
    static final String MAIN_MENU = "mainMenu";
    static final String SETTINGS = "settings";
    static final String GAME = "game";
    static final String END_GAME = "endGame";
    static final String PAUSE = "pause";
    static final String POWERUPS = "powerups";

    public GameViewImpl() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        
        this.frame = new JFrame("Vampire.io");
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(this.cardLayout);
        
        this.cardPanel.add(new MainMenuPanel(this), GameViewImpl.MAIN_MENU);
        this.cardPanel.add(new SettingsPanel(this), GameViewImpl.SETTINGS);
        this.cardPanel.add(new GamePanel(this), GameViewImpl.GAME);
        this.cardPanel.add(new EndGamePanel(this), GameViewImpl.END_GAME);
        this.cardPanel.add(new PausePanel(this), GameViewImpl.PAUSE);
        this.cardPanel.add(new PowerUpPanel(this), GameViewImpl.POWERUPS);

        this.showScreen(GameViewImpl.MAIN_MENU);
        
        this.frame.add(this.cardPanel);
        this.frame.setSize((int) (screenSize.width * GameViewImpl.SCALE), (int) (screenSize.height * GameViewImpl.SCALE));
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    void showScreen(String name) {
        this.cardLayout.show(this.cardPanel, name);
    }

}
