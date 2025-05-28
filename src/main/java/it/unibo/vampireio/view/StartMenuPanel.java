package it.unibo.vampireio.view;

import javax.swing.JButton;
import java.awt.event.ActionListener;

class StartMenuPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;
    
    private final JButton startButton;
    private final JButton scoreboardButton;
    private final JButton shopButton;
    private final JButton loadSaveButton;
    private final JButton quitButton;

    StartMenuPanel(final GameViewImpl view) {
        super(view);

        this.startButton = this.addButton("START", 1, 0);
        this.scoreboardButton = this.addButton("SCOREBOARD", 0, 1);
        this.shopButton = this.addButton("SHOP", 1, 1);
        this.loadSaveButton = this.addButton("LOAD SAVE", 2, 1);
        this.quitButton = this.addButton("QUIT", 1, 2);
    }

    void setStartListener(final ActionListener listener) {
        this.startButton.addActionListener(listener);
    }

    void setScoreboardListener(final ActionListener listener) {
        this.scoreboardButton.addActionListener(listener);
    }

    void setShopListener(final ActionListener listener) {
        this.shopButton.addActionListener(listener);
    }

    void setLoadSaveListener(final ActionListener listener) {
        this.loadSaveButton.addActionListener(listener);
    }

    void setQuitListener(final ActionListener listener) {
        this.quitButton.addActionListener(listener);
    }

}
