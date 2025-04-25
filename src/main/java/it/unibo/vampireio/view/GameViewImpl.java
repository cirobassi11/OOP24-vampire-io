package it.unibo.vampireio.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Taskbar;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import it.unibo.vampireio.controller.GameData;
import it.unibo.vampireio.controller.InputHandler;
import it.unibo.vampireio.controller.ScoreData;
import it.unibo.vampireio.controller.UnlockableCharacterData;

public class GameViewImpl implements GameView {

    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final Map<String, JPanel> panels = new HashMap<>();

    private Dimension currentFrameSize;
    private Image backgroundImage;

    // Aspect Ratio fisso (es. 16:9)
    private static final double ASPECT_RATIO = 16.0 / 9.0;

    private static final Dimension DEFAULT_RESOLUTION = new Dimension(1280, 720);
    private static final Dimension MIN_RESOLUTION = new Dimension(640, 360);

    static final String frameTitle = "Vampire.io";

    public static final String SAVE_MENU = "saveMenu";
    public static final String SAVE_SELECTION = "saveSelection";
    public static final String START = "mainMenu";
    public static final String SCOREBOARD = "scoreboard";
    public static final String CHOOSE_CHARACTER = "chooseCharacter";
    public static final String GAME = "game";
    public static final String END_GAME = "endGame";
    public static final String PAUSE = "pause";
    public static final String SHOP = "shop";
    public static final String UNLOCKABLE_CHARACTERS = "unlockableCharacters";
    public static final String UNLOCKABLE_POWERUPS = "unlockablePowerups";
    public static final String IN_GAME_POWERUPS = "inGamePowerups";

    private final String iconPath = "/images/icon.png";
    private final String backgroundPath = "/images/background.png";

    public GameViewImpl() {
        this.frame = new JFrame(frameTitle);
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(this.cardLayout);

        this.initFrame();

        this.showScreen(GameViewImpl.SAVE_MENU);

        new AudioManager();
    }

    private void initFrame() {
        this.setIcon(this.iconPath);
        this.setBackgroundImage(this.backgroundPath);
        this.setResolution(DEFAULT_RESOLUTION);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(true); // Allow resizing

        // Blocca aspect ratio
        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = Math.max(frame.getWidth(), MIN_RESOLUTION.width);
                int height = Math.max((int) (width / ASPECT_RATIO), MIN_RESOLUTION.height);
                setResolution(new Dimension(width, height));
                for (var panel : panels.values()) {
                    if(panel instanceof BasePanel) {
                        ((BasePanel) panel).updateComponentSize();
                    }
                }
            }
        });

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.add(this.cardPanel);
        this.frame.setVisible(true);
        this.frame.setFocusable(true);
        this.frame.requestFocus();

        this.initPanels();
        
        ((GamePanel) panels.get(GAME)).setFocusable(true);
        ((GamePanel) panels.get(GAME)).requestFocusInWindow();
    }

    private void initPanels() {
        this.panels.put(SAVE_MENU, new SaveMenuPanel(this));
        this.panels.put(SAVE_SELECTION, new SaveSelectionPanel(this));
        this.panels.put(START, new StartMenuPanel(this));
        this.panels.put(SCOREBOARD, new ScoreboardPanel(this));
        this.panels.put(CHOOSE_CHARACTER, new ChooseCharacterPanel(this));
        this.panels.put(GAME, new GamePanel(this));
        this.panels.put(END_GAME, new EndGamePanel(this));
        this.panels.put(PAUSE, new PausePanel(this));
        this.panels.put(SHOP, new ShopPanel(this));
        this.panels.put(UNLOCKABLE_CHARACTERS, new UnlockableCharactersPanel(this));
        this.panels.put(UNLOCKABLE_POWERUPS, new UnlockablePowerUpsPanel(this));
        this.panels.put(IN_GAME_POWERUPS, new InGamePowerUpPanel(this));
        this.panels.forEach((name, panel) -> cardPanel.add(panel, name));
    }

    public Dimension getFrameSize() {
        return this.currentFrameSize;
    }

    private void setResolution(Dimension resolution) {
        this.frame.setSize(resolution);
        this.currentFrameSize = resolution;
    }

    private void setIcon(String path) {
        final Image image = new ImageIcon(getClass().getResource(path)).getImage();
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                frame.setIconImage(image);
            } else {
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

    public void quit() {
        System.exit(0);
    }

    @Override
    public void update(GameData data) {
        var gamePanel = (GamePanel) panels.get(GAME);
        gamePanel.setData(data);
        gamePanel.repaint();
    }

    @Override
    public void updateSaveList(List<String> saveNames) {
        var loadSavePanel = (SaveSelectionPanel) panels.get(SAVE_SELECTION);
        loadSavePanel.updateSavesList(saveNames);
    }

    @Override
    public void showScreen(String name) {
        this.cardLayout.show(this.cardPanel, name);
    }

    @Override
    public void setPlayerInputListener(InputHandler listener) {
        listener.setupKeyBindings((GamePanel) panels.get(GAME));
    }

    @Override
	public void setChoosableCharactersData(List<UnlockableCharacterData> choosableCharactersData) {
		((ChooseCharacterPanel) panels.get(CHOOSE_CHARACTER)).setChoosableCharactersData(choosableCharactersData);
	}

    @Override
    public String getChoosedCharacter() {
        return ((ChooseCharacterPanel) panels.get(CHOOSE_CHARACTER)).getChoosedCharacter();
    }

    @Override
    public String getSelectedSave() {
        return ((SaveSelectionPanel) panels.get(SAVE_SELECTION)).getSelectedSave();
    }

    @Override
    public void setConfirmCharacterListener(ActionListener listener) {
        ((ChooseCharacterPanel) panels.get(CHOOSE_CHARACTER)).setConfirmCharacterListener(listener);
    }

    @Override
    public void setNewSaveListener(ActionListener listener) {
        ((SaveMenuPanel) panels.get(SAVE_MENU)).setNewSaveListener(listener);
    }

    @Override
    public void setShowSaveListener(ActionListener listener) {
        ((SaveMenuPanel) panels.get(SAVE_MENU)).setShowSaveListener(listener);
    }
    
    @Override
    public void setChooseSaveListener(ActionListener listener) {
        ((SaveSelectionPanel) panels.get(SAVE_SELECTION)).setChooseSaveListener(listener);
    }

    @Override
    public void setCharactersShopListener(ActionListener listener) {
        ((ShopPanel) panels.get(SHOP)).setCharactersShopListener(listener);
    }

    @Override
    public void setPowerUpsShopListener(ActionListener listener) {
        ((ShopPanel) panels.get(SHOP)).setPowerUpsShopListener(listener);
    }

    @Override
    public void setStartListener(ActionListener listener) {
        ((StartMenuPanel) panels.get(START)).setStartListener(listener);
    }

    @Override
    public void setScoreboardListener(ActionListener listener) {
        ((StartMenuPanel) panels.get(START)).setScoreboardListener(listener);
    }

    @Override
    public void setShopListener(ActionListener listener) {
        ((StartMenuPanel) panels.get(START)).setShopListener(listener);
    }

    @Override
    public void setLoadSaveListener(ActionListener listener) {
        ((StartMenuPanel) panels.get(START)).setLoadSaveListener(listener);
    }

    @Override
	public void setUnlockableCharactersData(List<UnlockableCharacterData> unlockableCharactersData) {
		((UnlockableCharactersPanel) panels.get(UNLOCKABLE_CHARACTERS)).setUnlockableCharactersData(unlockableCharactersData);
	}

    @Override
    public String getSelectedCharacter() {
        return ((UnlockableCharactersPanel) panels.get(UNLOCKABLE_CHARACTERS)).getSelectedCharacter();
    }

    @Override
    public void setBuyCharactersListener(ActionListener listener) {
        ((UnlockableCharactersPanel) panels.get(UNLOCKABLE_CHARACTERS)).setBuyCharactersListener(listener);
    }

    @Override
    public void setBuyPowerUpsListener(ActionListener listener) {
        ((UnlockablePowerUpsPanel) panels.get(UNLOCKABLE_POWERUPS)).setBuyPowerUpsListener(listener);
    }

    @Override
    public void setReturnMenuListener(ActionListener listener) {
        ((EndGamePanel) panels.get(END_GAME)).setReturnMenuListener(listener);
    }

    @Override
    public void setResumeListener(ActionListener listener) {
        ((PausePanel) panels.get(PAUSE)).setResumeListener(listener);
    }

    @Override
    public void setExitListener(ActionListener listener) {
        ((PausePanel) panels.get(PAUSE)).setExitListener(listener);
    }

    @Override
    public void setScoreList(List<ScoreData> scores) {
        ((ScoreboardPanel) panels.get(SCOREBOARD)).setScoreList(scores);
    }
    
    @Override
    public void setBackListener(ActionListener listener) {
        ((SaveSelectionPanel) panels.get(SAVE_SELECTION)).setBackListener(listener);
        ((ScoreboardPanel) panels.get(SCOREBOARD)).setBackListener(listener);
        ((ShopPanel) panels.get(SHOP)).setBackListener(listener);
        ((ChooseCharacterPanel) panels.get(CHOOSE_CHARACTER)).setBackListener(listener);
        ((UnlockableCharactersPanel) panels.get(UNLOCKABLE_CHARACTERS)).setBackListener(listener);
        ((UnlockablePowerUpsPanel) panels.get(UNLOCKABLE_POWERUPS)).setBackListener(listener);
    }

    @Override
    public void setQuitListener(ActionListener listener) {
        ((SaveMenuPanel) panels.get(SAVE_MENU)).setQuitListener(listener);
        ((StartMenuPanel) panels.get(START)).setQuitListener(listener);
    }

    @Override
    public void showError(String message) {
        javax.swing.JOptionPane.showMessageDialog(frame, message, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}