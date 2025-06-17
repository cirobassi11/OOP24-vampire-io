package it.unibo.vampireio.view.manager;

import javax.swing.event.ListSelectionListener;
import it.unibo.vampireio.controller.manager.InputProcessor;
import it.unibo.vampireio.view.panels.SaveMenuPanel;
import it.unibo.vampireio.view.panels.SaveSelectionPanel;
import it.unibo.vampireio.view.panels.ScoreboardPanel;
import it.unibo.vampireio.view.panels.ShopPanel;
import it.unibo.vampireio.view.panels.StartMenuPanel;
import it.unibo.vampireio.view.panels.UnlockableItemShopPanel;
import it.unibo.vampireio.view.panels.ChooseCharacterPanel;
import it.unibo.vampireio.view.panels.EndGamePanel;
import it.unibo.vampireio.view.panels.GamePanel;
import it.unibo.vampireio.view.panels.ItemSelectionPanel;
import it.unibo.vampireio.view.panels.PausePanel;
import java.awt.event.ActionListener;

/**
 * This class binds the listeners to the various panels.
 * It provides static methods to set up the listeners for each panel.
 */
public final class ListenerBinder {

    /**
     * Private constructor to prevent instantiation.
     * This class is a utility class and should not be instantiated.
     */
    private ListenerBinder() {
    }

    /**
     * Binds the listeners to the SaveMenuPanel.
     *
     * @param saveMenuPanel    the SaveMenuPanel to bind listeners to
     * @param newSaveListener  the listener for creating a new save
     * @param showSaveListener the listener for showing existing saves
     */
    public static void bindSaveMenuPanelListener(
            final SaveMenuPanel saveMenuPanel,
            final ActionListener newSaveListener,
            final ActionListener showSaveListener) {
        saveMenuPanel.setNewSaveListener(newSaveListener);
        saveMenuPanel.setShowSaveListener(showSaveListener);
    }

    /**
     * Binds the listeners to the StartMenuPanel.
     *
     * @param startMenuPanel     the StartMenuPanel to bind listeners to
     * @param startListener      the listener for starting the game
     * @param scoreboardListener the listener for accessing the scoreboard
     * @param shopListener       the listener for accessing the shop
     * @param loadSaveListener   the listener for loading a save
     */
    public static void bindStartMenuPanelListener(
            final StartMenuPanel startMenuPanel,
            final ActionListener startListener,
            final ActionListener scoreboardListener,
            final ActionListener shopListener,
            final ActionListener loadSaveListener) {
        startMenuPanel.setStartListener(startListener);
        startMenuPanel.setScoreboardListener(scoreboardListener);
        startMenuPanel.setShopListener(shopListener);
        startMenuPanel.setLoadSaveListener(loadSaveListener);
    }

    /**
     * Binds the listeners to the SaveSelectionPanel.
     *
     * @param saveSelectionPanel the SaveSelectionPanel to bind listeners to
     * @param chooseSaveListener the listener for choosing a save
     * @param backListener       the listener for going back to the previous menu
     */
    public static void bindSaveSelectionPanelListener(
            final SaveSelectionPanel saveSelectionPanel,
            final ActionListener chooseSaveListener,
            final ActionListener backListener) {
        saveSelectionPanel.setChooseSaveListener(chooseSaveListener);
        saveSelectionPanel.setBackListener(backListener);
    }

    /**
     * Binds the listeners to the ChooseCharacterPanel.
     *
     * @param chooseCharacterPanel       the ChooseCharacterPanel to bind listeners
     *                                   to
     * @param characterSelectionListener the listener for character selection
     * @param confirmCharacterListener   the listener for confirming character
     *                                   selection
     * @param backListener               the listener for going back to the previous
     *                                   menu
     */
    public static void bindChooseCharacterPanelListener(
            final ChooseCharacterPanel chooseCharacterPanel,
            final ListSelectionListener characterSelectionListener,
            final ActionListener confirmCharacterListener,
            final ActionListener backListener) {
        chooseCharacterPanel.setCharacterSelectionListener(characterSelectionListener);
        chooseCharacterPanel.setConfirmCharacterListener(confirmCharacterListener);
        chooseCharacterPanel.setBackListener(backListener);
    }

    /**
     * Binds the InputHandler to the GamePanel.
     *
     * @param gamePanel     the GamePanel to bind the InputHandler to
     * @param inputListener the InputHandler that handles key bindings
     */
    public static void bindGamePanelListener(
            final GamePanel gamePanel,
            final InputProcessor inputListener) {
        inputListener.setupKeyBindings(gamePanel);
    }

    /**
     * Binds the listeners to the ShopPanel.
     *
     * @param shopPanel             the ShopPanel to bind listeners to
     * @param characterShopListener the listener for character shop
     * @param powerUpsShopListener  the listener for power-ups shop
     * @param backListener          the listener for going back to the previous menu
     */
    public static void bindShopPanelListener(
            final ShopPanel shopPanel,
            final ActionListener characterShopListener,
            final ActionListener powerUpsShopListener,
            final ActionListener backListener) {
        shopPanel.setCharactersShopListener(characterShopListener);
        shopPanel.setPowerUpsShopListener(powerUpsShopListener);
        shopPanel.setBackListener(backListener);

    }

    /**
     * Binds the listeners to the ScoreboardPanel.
     *
     * @param scoreboardPanel the ScoreboardPanel to bind listeners to
     * @param backListener    the listener for going back to the previous menu
     */
    public static void bindScoreboardPanelListener(
            final ScoreboardPanel scoreboardPanel,
            final ActionListener backListener) {
        scoreboardPanel.setBackListener(backListener);
    }

    /**
     * Binds the listeners to the EndGamePanel.
     *
     * @param endGamePanel       the EndGamePanel to bind listeners to
     * @param returnMenuListener the listener for returning to the main menu
     */
    public static void bindEndGamePanelListener(
            final EndGamePanel endGamePanel,
            final ActionListener returnMenuListener) {
        endGamePanel.setReturnMenuListener(returnMenuListener);
    }

    /**
     * Binds the listeners to the PausePanel.
     *
     * @param pausePanel     the PausePanel to bind listeners to
     * @param resumeListener the listener for resuming the game
     * @param exitListener   the listener for exiting to the main menu
     */
    public static void bindPausePanelListener(
            final PausePanel pausePanel,
            final ActionListener resumeListener,
            final ActionListener exitListener) {
        pausePanel.setResumeListener(resumeListener);
        pausePanel.setExitListener(exitListener);
    }

    /**
     * Binds the listeners to the ItemSelectionPanel.
     *
     * @param itemSelectionPanel the ItemSelectionPanel to bind listeners to
     * @param chooseItemListener the listener for choosing an item
     * @param chooseButtonListener the listener for the choose item button
     */
    public static void bindItemSelectionPanelListener(
            final ItemSelectionPanel itemSelectionPanel,
            final ListSelectionListener chooseItemListener,
            final ActionListener chooseButtonListener) {
        itemSelectionPanel.setChooseItemListener(chooseItemListener);
        itemSelectionPanel.setChooseItemButtonListener(chooseButtonListener);
    }

    /**
     * Binds the listeners to the UnlockableItemShopPanel.
     *
     * @param unlockableItemShopPanel the UnlockableItemShopPanel to bind listeners
     *                                to
     * @param buyItemListener         the listener for buying an item
     * @param listSelectionListener   the listener for item selection changes
     * @param backListener            the listener for going back to the previous
     *                                menu
     */
    public static void bindUnlockableItemShopPanelListener(
            final UnlockableItemShopPanel unlockableItemShopPanel,
            final ActionListener buyItemListener,
            final ListSelectionListener listSelectionListener,
            final ActionListener backListener) {
        unlockableItemShopPanel.setBuyItemListener(buyItemListener);
        unlockableItemShopPanel.setListSelectionListener(listSelectionListener);
        unlockableItemShopPanel.setBackListener(backListener);
    }
}
