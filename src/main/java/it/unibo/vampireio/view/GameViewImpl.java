package it.unibo.vampireio.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Taskbar;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import it.unibo.vampireio.controller.GameController;
import it.unibo.vampireio.controller.PositionableDTO;

public class GameViewImpl implements GameView {
    
    private final GameController controller;

    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private JPanel mainMenuPanel;
    private JPanel settingsPanel;
    private JPanel gamePanel;
    private JPanel chooseCharacterPanel;
    private JPanel endGamePanel;
    private JPanel pausePanel;
    private JPanel unlockablePowerUpPanel;
    private JPanel inGamePowerUpPanel;

    private Dimension screenSize;

    private Dimension currentFrameSize;

    public static final List<Dimension> resolutions = List.of(
        new Dimension(640, 360),
        new Dimension(1280, 720),
        new Dimension(1920, 1080),
        new Dimension(3840, 2160)
    );

    private double scaleFactor;
    
    static final String MAIN_MENU = "mainMenu";
    static final String SETTINGS = "settings";
    static final String GAME = "game";
    static final String CHOOSE_CHARACTER = "chooseCharacter";
    static final String END_GAME = "endGame";
    static final String PAUSE = "pause";
    static final String UNLOCKABLE_POWERUPS = "unlockablePowerups";
    static final String IN_GAME_POWERUPS = "inGamePowerups";

    private final String iconPath = "/images/icon.png";
    private final String backgroundPath = "/images/background.png";
    private Image backgroundImage;

    private AudioManager audioManager;

    public GameViewImpl(GameController controller) {
        this.controller = controller;
        
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        this.frame = new JFrame("Vampire.io");
        this.setIcon(this.iconPath);
        this.setBackgroundImage(this.backgroundPath);
        this.setResolution(new Dimension(1280, 720)); /// DA LEGGERE DALLE IMPOSTAZIONII
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.mainMenuPanel = new MainMenuPanel(this);
        this.settingsPanel = new SettingsPanel(this);
        this.gamePanel = new GamePanel(this, this.controller);
        this.chooseCharacterPanel = new ChooseCharacterPanel(this, this.controller);
        this.endGamePanel = new EndGamePanel(this);
        this.pausePanel = new PausePanel(this);
        this.unlockablePowerUpPanel = new UnlockablePowerUpPanel(this);
        this.inGamePowerUpPanel = new InGamePowerUpPanel(this);

        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(this.cardLayout);
        
        this.cardPanel.add(this.mainMenuPanel, GameViewImpl.MAIN_MENU);
        this.cardPanel.add(this.settingsPanel, GameViewImpl.SETTINGS);
        this.cardPanel.add(this.gamePanel, GameViewImpl.GAME);
        this.cardPanel.add(this.chooseCharacterPanel, GameViewImpl.CHOOSE_CHARACTER);
        this.cardPanel.add(this.endGamePanel, GameViewImpl.END_GAME);
        this.cardPanel.add(this.pausePanel, GameViewImpl.PAUSE);
        this.cardPanel.add(this.unlockablePowerUpPanel, GameViewImpl.UNLOCKABLE_POWERUPS);
        this.cardPanel.add(this.inGamePowerUpPanel, GameViewImpl.IN_GAME_POWERUPS);

        this.showScreen(GameViewImpl.MAIN_MENU);
        this.frame.add(this.cardPanel);

        this.frame.setVisible(true);

        this.audioManager = new AudioManager();
    }

    public void showScreen(String name) {
        this.cardLayout.show(this.cardPanel, name);
    }

    public Dimension getScreenSize() {
        return this.screenSize;
    }

    public Dimension getCurrentFrameSize() {
        return this.currentFrameSize;
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

    private void setBackgroundImage(String path) {
        this.backgroundImage = new ImageIcon(getClass().getResource(path)).getImage();
    }

    Image getBackgroundImage() {
        return this.backgroundImage;
    }

    void setResolution(Dimension resolution) {
        this.screenSize = resolution;
        this.frame.setSize(resolution);
        this.currentFrameSize = resolution;
    }

    public double getScaleFactor() {
        return this.scaleFactor;
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    @Override
    public void update(List<PositionableDTO> positionables) {
        ((GamePanel) this.gamePanel).setPositionables(positionables);
        this.gamePanel.repaint();
    }
}
