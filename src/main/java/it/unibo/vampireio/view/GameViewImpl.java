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
import javax.swing.event.ListSelectionListener;
import it.unibo.vampireio.controller.GameData;
import it.unibo.vampireio.controller.InputHandler;
import it.unibo.vampireio.controller.ItemData;
import it.unibo.vampireio.controller.ScoreData;
import it.unibo.vampireio.controller.UnlockableItemData;

public class GameViewImpl implements GameView {
    static final String FRAME_TITLE = "Vampire.io";

    private final Map<String, JPanel> panels = new HashMap<>();
    private final FrameManager frameManager;
    private ImageManager imageManager;
    private ViewErrorListener errorListener;

    private Dimension currentFrameSize;
    private Image backgroundImage;

    public GameViewImpl() {
        this.imageManager = new ImageManager(this);
        this.frameManager = new FrameManager(FRAME_TITLE, this.imageManager.getImage("background"));
        this.initPanels();
        this.frameManager.addPanels(this.panels);
        this.showScreen(GameViewImpl.SAVE_MENU);
        new AudioManager(this);
    }

    private void initPanels() {
        this.panels.put(SAVE_MENU, new SaveMenuPanel(this.frameManager));
        this.panels.put(SAVE_SELECTION, new SaveSelectionPanel(this.frameManager));
        this.panels.put(START, new StartMenuPanel(this.frameManager));
        this.panels.put(SCOREBOARD, new ScoreboardPanel(this.frameManager));
        this.panels.put(CHOOSE_CHARACTER, new ChooseCharacterPanel(this.frameManager));
        this.panels.put(GAME, new GamePanel(this.frameManager, this.imageManager));
        this.panels.put(ITEM_SELECTION, new ItemSelectionPanel(this.frameManager));
        this.panels.put(END_GAME, new EndGamePanel(this.frameManager));
        this.panels.put(PAUSE, new PausePanel(this.frameManager));
        this.panels.put(SHOP, new ShopPanel(this.frameManager));
        this.panels.put(UNLOCKABLE_CHARACTERS, new UnlockableItemShopPanel(this.frameManager, this.imageManager));
        this.panels.put(UNLOCKABLE_POWERUPS, new UnlockableItemShopPanel(this.frameManager, this.imageManager));
    }

    @Override
    public void setSaveMenuPanelListeners(
            final ActionListener newSaveListener,
            final ActionListener showSaveListener,
            final ActionListener quitListener) {
        ListenerBinder.bindSaveMenuPanelListener(
                (SaveMenuPanel) this.panels.get(SAVE_MENU),
                newSaveListener,
                showSaveListener,
                quitListener);
    }

    @Override
    public void setStartMenuPanelListeners(
            final ActionListener startListener,
            final ActionListener scoreboardListener,
            final ActionListener shopListener,
            final ActionListener loadSaveListener,
            final ActionListener quitListener) {
        ListenerBinder.bindStartMenuPanelListener((StartMenuPanel) this.panels.get(START),
                startListener,
                scoreboardListener,
                shopListener,
                loadSaveListener,
                quitListener);
    }

    @Override
    public void setSaveSelectionPanelListener(
            final ActionListener chooseSaveListener,
            final ActionListener backListener) {
        ListenerBinder.bindSaveSelectionPanelListener((SaveSelectionPanel) this.panels.get(SAVE_SELECTION),
                chooseSaveListener,
                backListener);
    }

    @Override
    public void setChooseCharacterPanelListener(
            final ListSelectionListener characterSelectionListener,
            final ActionListener confirmCharacterListener,
            final ActionListener backListener) {
        ListenerBinder.bindChooseCharacterPanelListener(
                (ChooseCharacterPanel) this.panels.get(CHOOSE_CHARACTER),
                characterSelectionListener,
                confirmCharacterListener,
                backListener);
    }

    @Override
    public void setShopPanelListener(
            final ActionListener characterShopListener,
            final ActionListener powerUpsShopListener,
            final ActionListener backListener) {
        ListenerBinder.bindShopPanelListener((ShopPanel) this.panels.get(SHOP),
                characterShopListener,
                powerUpsShopListener,
                backListener);
    }

    @Override
    public void setScoreboardPanelListener(final ActionListener backListener) {
        ListenerBinder.bindScoreboardPanelListener((ScoreboardPanel) this.panels.get(SCOREBOARD), backListener);

    }

    @Override
    public void setEndGamePanelListener(final ActionListener returnMenuListener) {
        ListenerBinder.bindEndGamePanelListener((EndGamePanel) this.panels.get(END_GAME),
                returnMenuListener);

    }

    @Override
    public void setPausePanelListener(final ActionListener resumeListener,
            final ActionListener exitListener) {
        ListenerBinder.bindPausePanelListener((PausePanel) this.panels.get(PAUSE),
                resumeListener, exitListener);

    }

    @Override
    public void setItemSelectionPanelListener(final ActionListener chooseItemListener) {
        ListenerBinder.bindItemSelectionPanelListener((ItemSelectionPanel) this.panels.get(ITEM_SELECTION),
                chooseItemListener);

    }

    @Override
    public void setUnlockableItemShopPanelListener(final ActionListener buyItemListener,
            final ListSelectionListener listSelectionListener, final ActionListener backListener) {
        ListenerBinder.bindUnlockableItemShopPanelListener(
                (UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_CHARACTERS),
                buyItemListener, listSelectionListener, backListener);
        ListenerBinder.bindUnlockableItemShopPanelListener(
                (UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_POWERUPS),
                buyItemListener, listSelectionListener, backListener);

    }

    @Override
    public void setPlayerInputListener(final InputHandler inputHandler) {
        ListenerBinder.bindGamePanelListener((GamePanel) this.panels.get(GAME), inputHandler);
    }

    public void quit() {
        System.exit(0);
    }

    @Override
    public void update(final GameData data) {
        ((GamePanel) panels.get(GAME)).setData(data);
        panels.get(GAME).repaint();
    }

    @Override
    public void updateSaveList(final List<String> saveNames) {
        final var loadSavePanel = (SaveSelectionPanel) this.panels.get(SAVE_SELECTION);
        loadSavePanel.updateSavesList(saveNames);
    }

    @Override
    public void showScreen(final String name) {
        this.frameManager.showScreen(name);
    }

    @Override
    public void setViewErrorListener(ViewErrorListener listener) {
        this.errorListener = listener;
    }

    @Override
    public void notifyError(final String message) {
        if (this.errorListener != null) {
            this.errorListener.onError(message);
        }
    }

    @Override
    public void setChoosableCharactersData(final List<UnlockableItemData> choosableCharactersData) {
        ((ChooseCharacterPanel) this.panels.get(CHOOSE_CHARACTER)).setChoosableCharactersData(choosableCharactersData);
    }

    @Override
    public String getChoosedCharacter() {
        return ((ChooseCharacterPanel) this.panels.get(CHOOSE_CHARACTER)).getChoosedCharacter();
    }

    @Override
    public String getSelectedSave() {
        return ((SaveSelectionPanel) this.panels.get(SAVE_SELECTION)).getSelectedSave();
    }

    @Override
    public String getSelectedItem() {
        return ((ItemSelectionPanel) this.panels.get(ITEM_SELECTION)).getSelectedItem();
    }

    @Override
    public void setItemsData(final List<ItemData> itemsData) {
        ((ItemSelectionPanel) this.panels.get(ITEM_SELECTION)).setItemsData(itemsData);
    }

    @Override
    public void setCoinsAmount(final int coins) {
        ((UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_CHARACTERS)).setCoinsAmount(coins);
        ((UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_POWERUPS)).setCoinsAmount(coins);
    }

    @Override
    public void setUnlockableCharactersData(final List<UnlockableItemData> unlockableCharacterData) {
        ((UnlockableItemShopPanel) this.panels
                .get(UNLOCKABLE_CHARACTERS))
                .setUnlockableItemData(unlockableCharacterData);
    }

    @Override
    public void setUnlockablePowerUpsData(final List<UnlockableItemData> unlockablePowerUpData) {
        ((UnlockableItemShopPanel) this.panels
                .get(UNLOCKABLE_POWERUPS))
                .setUnlockableItemData(unlockablePowerUpData);
    }

    @Override
    public String getSelectedCharacter() {
        return ((UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_CHARACTERS)).getSelectedItem();
    }

    @Override
    public String getSelectedPowerUp() {
        return ((UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_POWERUPS)).getSelectedItem();
    }

    @Override
    public void setScoresData(final List<ScoreData> scores) {
        ((ScoreboardPanel) this.panels.get(SCOREBOARD)).setScoresData(scores);
    }

    @Override
    public void setCurrentScore(final ScoreData score) {
        ((EndGamePanel) this.panels.get(END_GAME)).setScore(score);
    }

    @Override
    public void disableBuyButton() {
        ((UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_CHARACTERS)).disableBuyButton();
        ((UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_POWERUPS)).disableBuyButton();
    }

    @Override
    public void enableBuyButton() {
        ((UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_CHARACTERS)).enableBuyButton();
        ((UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_POWERUPS)).enableBuyButton();
    }

    @Override
    public void disableConfirmCharacterButton() {
        ((ChooseCharacterPanel) this.panels.get(CHOOSE_CHARACTER)).disableConfirmCharacterButton();
    }

    @Override
    public void enableConfirmCharacterButton() {
        ((ChooseCharacterPanel) this.panels.get(CHOOSE_CHARACTER)).enableConfirmCharacterButton();
    }

    @Override
    public void showError(final String message) {
        this.frameManager.showError(message);
    }
}
