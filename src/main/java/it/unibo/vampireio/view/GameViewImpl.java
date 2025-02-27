package it.unibo.vampireio.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Taskbar;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.vampireio.controller.GameController;

public class GameViewImpl implements GameView {
    
    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    
    private static final double SCALE = 0.8;

    private Dimension screenSize;

    private final GameController controller;
    
    static final String MAIN_MENU = "mainMenu";
    static final String SETTINGS = "settings";
    static final String GAME = "game";
    static final String CHOOSE_CHARACTER = "chooseCharacter";
    static final String END_GAME = "endGame";
    static final String PAUSE = "pause";
    static final String POWERUPS = "powerups";

    private final String iconPath = "/images/icon.png";

    private Image backgroundImage;

    public GameViewImpl(GameController controller) {
        this.controller = controller;
        
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        this.frame = new JFrame("Vampire.io");

        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(this.cardLayout);
        
        this.cardPanel.add(new MainMenuPanel(this), GameViewImpl.MAIN_MENU);
        this.cardPanel.add(new SettingsPanel(this), GameViewImpl.SETTINGS);
        this.cardPanel.add(new GamePanel(this), GameViewImpl.GAME);
        this.cardPanel.add(new ChooseCharacterPanel(this), GameViewImpl.CHOOSE_CHARACTER);
        this.cardPanel.add(new EndGamePanel(this), GameViewImpl.END_GAME);
        this.cardPanel.add(new PausePanel(this), GameViewImpl.PAUSE);
        this.cardPanel.add(new PowerUpPanel(this), GameViewImpl.POWERUPS);
        this.showScreen(GameViewImpl.MAIN_MENU);
        this.frame.add(this.cardPanel);

        this.setIcon(this.iconPath);
        this.frame.setSize((int) (screenSize.width * GameViewImpl.SCALE), (int) (screenSize.height * GameViewImpl.SCALE));
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    public void showScreen(String name) {
        this.cardLayout.show(this.cardPanel, name);
    }

    public Image getBackgroundImage() {
        return this.backgroundImage;
    }

    public Dimension getScreenSize() {
        return this.screenSize;
    }

    private void setIcon(String path) {
        final Image image = new ImageIcon(getClass().getResource(path)).getImage();
        
        // Set the icon for Windows OS
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            this.frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(this.iconPath)));
        }
        // Set icon for macOS (and other systems which support this method)
        else {
            final Taskbar taskbar = Taskbar.getTaskbar();
            try {
                taskbar.setIconImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public GameController getController() {
        return this.controller;
    }

    @Override
    public void update() {
        System.out.println("NUOVO FRAMEE");
    }

}
