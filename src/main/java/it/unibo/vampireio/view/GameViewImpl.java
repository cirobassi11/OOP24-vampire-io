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

public class GameViewImpl implements GameView {
    
    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private final GameController controller;

    private Dimension screenSize;

    private Dimension currentFrameSize;

    public static final List<Dimension> resolutions = List.of(
        new Dimension(960, 540),
        new Dimension(1280, 720),
        new Dimension(1920, 1080),
        new Dimension(2560, 1440),
        new Dimension(3840, 2160)
    );
    
    static final String MAIN_MENU = "mainMenu";
    static final String SETTINGS = "settings";
    static final String GAME = "game";
    static final String CHOOSE_CHARACTER = "chooseCharacter";
    static final String END_GAME = "endGame";
    static final String PAUSE = "pause";
    static final String UNLOCKABLE_POWERUPS = "unlockablePowerups";
    static final String IN_GAME_POWERUPS = "inGamePowerups";

    private final String iconPath = "/images/icon.png";

    private Image backgroundImage;

    public GameViewImpl(GameController controller) {
        this.controller = controller;
        
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        this.frame = new JFrame("Vampire.io");
        this.setIcon(this.iconPath);
        this.setResolution(resolutions.stream().filter(res -> res.width < this.screenSize.getWidth() && res.height < this.screenSize.getHeight()).reduce((first, second) -> second).get());
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(this.cardLayout);
        
        this.cardPanel.add(new MainMenuPanel(this), GameViewImpl.MAIN_MENU);
        this.cardPanel.add(new SettingsPanel(this), GameViewImpl.SETTINGS);
        this.cardPanel.add(new GamePanel(this), GameViewImpl.GAME);
        this.cardPanel.add(new ChooseCharacterPanel(this), GameViewImpl.CHOOSE_CHARACTER);
        this.cardPanel.add(new EndGamePanel(this), GameViewImpl.END_GAME);
        this.cardPanel.add(new PausePanel(this), GameViewImpl.PAUSE);
        this.cardPanel.add(new UnlockablePowerUpPanel(this), GameViewImpl.UNLOCKABLE_POWERUPS);
        this.cardPanel.add(new InGamePowerUpPanel(this), GameViewImpl.IN_GAME_POWERUPS);
        this.showScreen(GameViewImpl.MAIN_MENU);
        this.frame.add(this.cardPanel);

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

    public void setResolution(Dimension resolution) {
        this.screenSize = resolution;
        this.frame.setSize(resolution);
        this.currentFrameSize = resolution;
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
