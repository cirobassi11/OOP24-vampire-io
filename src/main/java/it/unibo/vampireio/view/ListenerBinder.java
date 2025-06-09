package it.unibo.vampireio.view;

import javax.swing.event.ListSelectionListener;
import it.unibo.vampireio.controller.InputHandler;
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

    static void bindSaveMenuPanelListener(
            final SaveMenuPanel saveMenuPanel,
            final ActionListener newSaveListener,
            final ActionListener showSaveListener,
            final ActionListener quitListener) {
        saveMenuPanel.setNewSaveListener(newSaveListener);
        saveMenuPanel.setShowSaveListener(showSaveListener);
        saveMenuPanel.setQuitListener(quitListener);
    }

    static void bindStartMenuPanelListener(
            final StartMenuPanel startMenuPanel,
            final ActionListener startListener,
            final ActionListener scoreboardListener,
            final ActionListener shopListener,
            final ActionListener loadSaveListener,
            final ActionListener quitListener) {
        startMenuPanel.setStartListener(startListener);
        startMenuPanel.setScoreboardListener(scoreboardListener);
        startMenuPanel.setShopListener(shopListener);
        startMenuPanel.setLoadSaveListener(loadSaveListener);
        startMenuPanel.setQuitListener(quitListener);
    }

    static void bindSaveSelectionPanelListener(
            final SaveSelectionPanel saveSelectionPanel,
            final ActionListener chooseSaveListener,
            final ActionListener backListener) {
        saveSelectionPanel.setChooseSaveListener(chooseSaveListener);
        saveSelectionPanel.setBackListener(backListener);
    }

    static void bindChooseCharacterPanelListener(
            final ChooseCharacterPanel chooseCharacterPanel,
            final ListSelectionListener characterSelectionListener,
            final ActionListener confirmCharacterListener,
            final ActionListener backListener) {
        chooseCharacterPanel.setCharacterSelectionListener(characterSelectionListener);
        chooseCharacterPanel.setConfirmCharacterListener(confirmCharacterListener);
        chooseCharacterPanel.setBackListener(backListener);
    }

    static void bindGamePanelListener(
            final GamePanel gamePanel,
            final InputHandler inputListener) {
        inputListener.setupKeyBindings((GamePanel) gamePanel);
    }

    static void bindShopPanelListener(
            final ShopPanel shopPanel,
            final ActionListener characterShopListener,
            final ActionListener powerUpsShopListener,
            final ActionListener backListener) {
        shopPanel.setCharactersShopListener(characterShopListener);
        shopPanel.setPowerUpsShopListener(powerUpsShopListener);
        shopPanel.setBackListener(backListener);

    }

    static void bindScoreboardPanelListener(
            final ScoreboardPanel scoreboardPanel,
            final ActionListener backListener) {
        scoreboardPanel.setBackListener(backListener);
    }

    static void bindEndGamePanelListener(
            final EndGamePanel endGamePanel,
            final ActionListener returnMenuListener) {
        endGamePanel.setReturnMenuListener(returnMenuListener);
    }

    static void bindPausePanelListener(
            final PausePanel pausePanel,
            final ActionListener resumeListener,
            final ActionListener exitListener) {
        pausePanel.setResumeListener(resumeListener);
        pausePanel.setExitListener(exitListener);
    }

    static void bindItemSelectionPanelListener(
            final ItemSelectionPanel itemSelectionPanel,
            final ActionListener chooseItemListener) {
        itemSelectionPanel.setChooseItemListener(chooseItemListener);
    }

    static void bindUnlockableItemShopPanelListener(
            final UnlockableItemShopPanel unlockableItemShopPanel,
            final ActionListener buyItemListener,
            final ListSelectionListener listSelectionListener,
            final ActionListener backListener) {
        unlockableItemShopPanel.setBuyItemListener(buyItemListener);
        unlockableItemShopPanel.setListSelectionListener(listSelectionListener);
        unlockableItemShopPanel.setBackListener(backListener);
    }
}
