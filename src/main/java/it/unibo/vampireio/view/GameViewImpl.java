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
    public void setPlayerInputListener(final InputHandler listener) {
        listener.setupKeyBindings((GamePanel) this.panels.get(GAME));
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
    public void setConfirmCharacterListener(final ActionListener listener) {
        ((ChooseCharacterPanel) this.panels.get(CHOOSE_CHARACTER)).setConfirmCharacterListener(listener);
    }

    @Override
    public void setNewSaveListener(final ActionListener listener) {
        ((SaveMenuPanel) this.panels.get(SAVE_MENU)).setNewSaveListener(listener);
    }

    @Override
    public void setShowSaveListener(final ActionListener listener) {
        ((SaveMenuPanel) this.panels.get(SAVE_MENU)).setShowSaveListener(listener);
    }

    @Override
    public void setChooseSaveListener(final ActionListener listener) {
        ((SaveSelectionPanel) this.panels.get(SAVE_SELECTION)).setChooseSaveListener(listener);
    }

    @Override
    public void setChooseItemListener(final ActionListener listener) {
        ((ItemSelectionPanel) this.panels.get(ITEM_SELECTION)).setChooseItemListener(listener);
    }

    @Override
    public void setCharactersShopListener(final ActionListener listener) {
        ((ShopPanel) this.panels.get(SHOP)).setCharactersShopListener(listener);
    }

    @Override
    public void setPowerUpsShopListener(final ActionListener listener) {
        ((ShopPanel) this.panels.get(SHOP)).setPowerUpsShopListener(listener);
    }

    @Override
    public void setStartListener(final ActionListener listener) {
        ((StartMenuPanel) this.panels.get(START)).setStartListener(listener);
    }

    @Override
    public void setScoreboardListener(final ActionListener listener) {
        ((StartMenuPanel) this.panels.get(START)).setScoreboardListener(listener);
    }

    @Override
    public void setShopListener(final ActionListener listener) {
        ((StartMenuPanel) this.panels.get(START)).setShopListener(listener);
    }

    @Override
    public void setLoadSaveListener(final ActionListener listener) {
        ((StartMenuPanel) this.panels.get(START)).setLoadSaveListener(listener);
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
    public void setBuyCharactersListener(final ActionListener listener) {
        ((UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_CHARACTERS)).setBuyItemListener(listener);
    }

    @Override
    public void setBuyPowerUpsListener(final ActionListener listener) {
        ((UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_POWERUPS)).setBuyItemListener(listener);
    }

    @Override
    public void setListSelectionListener(final ListSelectionListener listener) {
        ((UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_CHARACTERS)).setListSelectionListener(listener);
        ((UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_POWERUPS)).setListSelectionListener(listener);
    }

    @Override
    public void setReturnMenuListener(final ActionListener listener) {
        ((EndGamePanel) this.panels.get(END_GAME)).setReturnMenuListener(listener);
    }

    @Override
    public void setResumeListener(final ActionListener listener) {
        ((PausePanel) this.panels.get(PAUSE)).setResumeListener(listener);
    }

    @Override
    public void setExitListener(final ActionListener listener) {
        ((PausePanel) this.panels.get(PAUSE)).setExitListener(listener);
    }

    @Override
    public void setScoresData(final List<ScoreData> scores) {
        ((ScoreboardPanel) this.panels.get(SCOREBOARD)).setScoresData(scores);
    }

    @Override
    public void setBackListener(final ActionListener listener) {
        ((SaveSelectionPanel) this.panels.get(SAVE_SELECTION)).setBackListener(listener);
        ((ScoreboardPanel) this.panels.get(SCOREBOARD)).setBackListener(listener);
        ((ShopPanel) this.panels.get(SHOP)).setBackListener(listener);
        ((ChooseCharacterPanel) this.panels.get(CHOOSE_CHARACTER)).setBackListener(listener);
        ((UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_CHARACTERS)).setBackListener(listener);
        ((UnlockableItemShopPanel) this.panels.get(UNLOCKABLE_POWERUPS)).setBackListener(listener);
    }

    @Override
    public void setQuitListener(final ActionListener listener) {
        ((SaveMenuPanel) this.panels.get(SAVE_MENU)).setQuitListener(listener);
        ((StartMenuPanel) this.panels.get(START)).setQuitListener(listener);
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
    public void setCharacterSelectionListener(final ListSelectionListener listener) {
        ((ChooseCharacterPanel) this.panels.get(CHOOSE_CHARACTER)).setCharacterSelectionListener(listener);
    }

    @Override
    public void showError(final String message) {
        this.frameManager.showError(message);
    }
}
