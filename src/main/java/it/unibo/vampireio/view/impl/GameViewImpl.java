package it.unibo.vampireio.view.impl;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionListener;
import it.unibo.vampireio.controller.data.GameData;
import it.unibo.vampireio.controller.data.ItemData;
import it.unibo.vampireio.controller.data.ScoreData;
import it.unibo.vampireio.controller.data.UnlockableItemData;
import it.unibo.vampireio.controller.manager.InputProcessor;
import it.unibo.vampireio.view.manager.FrameManager;
import it.unibo.vampireio.view.manager.ImageManager;
import it.unibo.vampireio.view.manager.ListenerBinder;
import it.unibo.vampireio.view.manager.AudioManager;
import it.unibo.vampireio.view.panels.ChooseCharacterPanel;
import it.unibo.vampireio.view.panels.EndGamePanel;
import it.unibo.vampireio.view.panels.GamePanel;
import it.unibo.vampireio.view.panels.ItemSelectionPanel;
import it.unibo.vampireio.view.panels.PausePanel;
import it.unibo.vampireio.view.panels.SaveMenuPanel;
import it.unibo.vampireio.view.panels.SaveSelectionPanel;
import it.unibo.vampireio.view.panels.ScoreboardPanel;
import it.unibo.vampireio.view.panels.ShopPanel;
import it.unibo.vampireio.view.panels.StartMenuPanel;
import it.unibo.vampireio.view.panels.UnlockableItemShopPanel;
import it.unibo.vampireio.view.api.GameView;
import it.unibo.vampireio.view.api.ViewErrorListener;

/**
 * The GameViewImpl class implements the GameView interface, providing the
 * functionality to manage different panels in the game view, handle user
 * interactions, and update the game state.
 */
public final class GameViewImpl implements GameView {
    static final String FRAME_TITLE = "Vampire.io";

    private final Map<String, JPanel> panels = new HashMap<>();
    private final FrameManager frameManager;
    private final ImageManager imageManager;
    private ViewErrorListener errorListener;

    /**
     * Constructs a new GameViewImpl instance, initializing the image manager,
     * frame manager, and panels.
     */
    public GameViewImpl() {
        this.imageManager = new ImageManager(this);
        this.frameManager = new FrameManager(FRAME_TITLE, this.imageManager.getImage("background"));
        this.initPanels();
        this.frameManager.addPanels(this.panels);
        this.showScreen(SAVE_MENU);
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
            final ActionListener showSaveListener) {
        ListenerBinder.bindSaveMenuPanelListener(
                (SaveMenuPanel) this.panels.get(SAVE_MENU),
                newSaveListener,
                showSaveListener);
    }

    @Override
    public void setStartMenuPanelListeners(
            final ActionListener startListener,
            final ActionListener scoreboardListener,
            final ActionListener shopListener,
            final ActionListener loadSaveListener) {
        ListenerBinder.bindStartMenuPanelListener((StartMenuPanel) this.panels.get(START),
                startListener,
                scoreboardListener,
                shopListener,
                loadSaveListener);
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
    public void setItemSelectionPanelListener(final ListSelectionListener chooseItemListener, 
        final ActionListener chooseButtonListener) {
        ListenerBinder.bindItemSelectionPanelListener((ItemSelectionPanel) this.panels.get(ITEM_SELECTION),
                chooseItemListener, chooseButtonListener);

    }

    @Override
    public void setUnlockableItemShopPanelListener(
            final ActionListener buyCharacterListener,
            final ActionListener buyPowerUpListener,
            final ListSelectionListener listSelectionListener,
            final ActionListener backListener) {
        ListenerBinder.bindUnlockableItemShopPanelListener(
                (UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_CHARACTERS),
                buyCharacterListener, listSelectionListener, backListener);
        ListenerBinder.bindUnlockableItemShopPanelListener(
                (UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_POWERUPS),
                buyPowerUpListener, listSelectionListener, backListener);

    }

    @Override
    public void setPlayerInputListener(final InputProcessor inputProcessor) {
        ListenerBinder.bindGamePanelListener((GamePanel) this.panels.get(GAME), inputProcessor);
    }

    @Override
    public void update(final GameData data) {
        final var gamePanel = (GamePanel) this.panels.get(GAME);
        gamePanel.setData(data);
        gamePanel.repaint();
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
    public void setViewErrorListener(final ViewErrorListener listener) {
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
    public void disableChooseItemButton() {
        ((ItemSelectionPanel) this.panels.get(ITEM_SELECTION)).disableChooseItemButton();
    }

    @Override
    public void enableChooseItemButton() {
        ((ItemSelectionPanel) this.panels.get(ITEM_SELECTION)).enableChooseItemButton();
    }

    @Override
    public void showError(final String message) {
        this.frameManager.showError(message);
    }
}
