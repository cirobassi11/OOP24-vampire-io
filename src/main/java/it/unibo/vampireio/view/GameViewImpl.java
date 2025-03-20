package it.unibo.vampireio.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Taskbar;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import it.unibo.vampireio.controller.GameData;
import it.unibo.vampireio.controller.GameController;

public class GameViewImpl implements GameView {
    
    private final GameController controller;

    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final Map<String, JPanel> panels = new HashMap<>();

    private Dimension screenSize;
    private Dimension currentFrameSize;

    private Image backgroundImage;

    public static final List<Dimension> RESOLUTIONS = List.of(
        new Dimension(640, 360),
        new Dimension(1280, 720),
        new Dimension(1920, 1080),
        new Dimension(3840, 2160)
    );
    
    static final String frameTitle = "Vampire.io";

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

    public GameViewImpl(GameController controller) {
        this.controller = controller;
        
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        this.frame = new JFrame(frameTitle);

        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(this.cardLayout);

        this.initFrame();
        this.initPanels();

        this.showScreen(GameViewImpl.MAIN_MENU);

        new AudioManager();
    }

    private void initFrame() {
        this.setIcon(this.iconPath);
        this.setBackgroundImage(this.backgroundPath);
        this.setResolution(new Dimension(1280, 720)); /// DA LEGGERE DALLE IMPOSTAZIONII
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.add(this.cardPanel);
        this.frame.setVisible(true);
    }

    private void initPanels() {
        this.panels.put(MAIN_MENU, new MainMenuPanel(this));
        this.panels.put(SETTINGS, new SettingsPanel(this));
        this.panels.put(GAME, new GamePanel(this));
        this.panels.put(CHOOSE_CHARACTER, new ChooseCharacterPanel(this, this.controller));
        this.panels.put(END_GAME, new EndGamePanel(this));
        this.panels.put(PAUSE, new PausePanel(this));
        this.panels.put(UNLOCKABLE_POWERUPS, new UnlockablePowerUpPanel(this));
        this.panels.put(IN_GAME_POWERUPS, new InGamePowerUpPanel(this));

        this.panels.forEach((name, panel) -> cardPanel.add(panel, name));
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
        
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                // Set the icon for Windows OS
                frame.setIconImage(image);
            } else {
                // Set the icon for other OS
                Taskbar.getTaskbar().setIconImage(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public void update(GameData data) {
        var gamePanel = (GamePanel) panels.get(GAME);
        gamePanel.setData(data);
        gamePanel.repaint();
    }
}
