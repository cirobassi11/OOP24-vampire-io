package it.unibo.vampireio.view;

import java.util.Map;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionListener;
import it.unibo.vampireio.controller.InputHandler;
import java.awt.event.ActionListener;

public class ListenerBinder {

    private ListenerBinder() {
    }

    static void bindSaveMenuPanelListener(
            SaveMenuPanel saveMenuPanel,
            ActionListener newSaveListener,
            ActionListener showSaveListener,
            ActionListener quitListener) {
        saveMenuPanel.setNewSaveListener(newSaveListener);
        saveMenuPanel.setShowSaveListener(showSaveListener);
        saveMenuPanel.setQuitListener(quitListener);
    }

    static void bindStartMenuPanelListener(
            StartMenuPanel startMenuPanel,
            ActionListener startListener,
            ActionListener scoreboardListener,
            ActionListener shopListener,
            ActionListener loadSaveListener,
            ActionListener quitListener) {
        startMenuPanel.setStartListener(startListener);
        startMenuPanel.setScoreboardListener(scoreboardListener);
        startMenuPanel.setShopListener(shopListener);
        startMenuPanel.setLoadSaveListener(loadSaveListener);
        startMenuPanel.setQuitListener(quitListener);
    }

    static void bindSaveSelectionPanelListener(
            SaveSelectionPanel saveSelectionPanel,
            ActionListener chooseSaveListener,
            ActionListener backListener) {
        saveSelectionPanel.setChooseSaveListener(chooseSaveListener);
        saveSelectionPanel.setBackListener(backListener);
    }

    static void bindChooseCharacterPanelListener(
            ChooseCharacterPanel chooseCharacterPanel,
            ListSelectionListener characterSelectionListener,
            ActionListener confirmCharacterListener,
            ActionListener backListener) {
        chooseCharacterPanel.setCharacterSelectionListener(characterSelectionListener);
        chooseCharacterPanel.setConfirmCharacterListener(confirmCharacterListener);
        chooseCharacterPanel.setBackListener(backListener);
    }

    static void bindGamePanelListener(
            GamePanel gamePanel,
            InputHandler inputListener) {
        inputListener.setupKeyBindings((GamePanel) gamePanel);
    }

    static void bindShopPanelListener(
            ShopPanel shopPanel,
            ActionListener characterShopListener,
            ActionListener powerUpsShopListener,
            ActionListener backListener) {
        shopPanel.setCharactersShopListener(characterShopListener);
        shopPanel.setPowerUpsShopListener(powerUpsShopListener);
        shopPanel.setBackListener(backListener);

    }

    static void bindScoreboardPanelListener(
            ScoreboardPanel scoreboardPanel,
            ActionListener backListener) {
        scoreboardPanel.setBackListener(backListener);
    }

    static void bindEndGamePanelListener(
            EndGamePanel endGamePanel,
            ActionListener returnMenuListener) {
        endGamePanel.setReturnMenuListener(returnMenuListener);
    }

    static void bindPausePanelListener(
            PausePanel pausePanel,
            ActionListener resumeListener,
            ActionListener exitListener) {
        pausePanel.setResumeListener(resumeListener);
        pausePanel.setExitListener(exitListener);
    }

    static void bindItemSelectionPanelListener(
            ItemSelectionPanel itemSelectionPanel,
            ActionListener chooseItemListener) {
        itemSelectionPanel.setChooseItemListener(chooseItemListener);
    }

    static void bindUnlockableItemShopPanelListener(
            UnlockableItemShopPanel unlockableItemShopPanel,
            ActionListener buyItemListener,
            ListSelectionListener listSelectionListener,
            ActionListener backListener) {
        unlockableItemShopPanel.setBuyItemListener(buyItemListener);
        unlockableItemShopPanel.setListSelectionListener(listSelectionListener);
        unlockableItemShopPanel.setBackListener(backListener);
    }
}
