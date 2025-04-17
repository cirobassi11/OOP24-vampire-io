package it.unibo.vampireio.view;

import javax.swing.JButton;
import java.awt.event.ActionListener;

class StartMenuPanel extends BasePanel {
    
    private JButton startButton;
    private JButton scoreboardButton;
    private JButton shopButton;
    private JButton loadSaveButton;
    private JButton quitButton;

    StartMenuPanel(GameViewImpl view) {
        super(view);

        this.startButton = this.addButton("START", 1, 0);
        this.scoreboardButton = this.addButton("SCOREBOARD", 0, 1);
        this.shopButton = this.addButton("SHOP", 1, 1);                
        this.loadSaveButton = this.addButton("LOAD SAVE", 2, 1);        
        this.quitButton = this.addButton("QUIT", 1, 2);
    }
    
    void setStartListener(ActionListener listener) {
        this.startButton.addActionListener(listener);
    }

    void setScoreboardListener(ActionListener listener) {
        this.scoreboardButton.addActionListener(listener);
    }

    void setShopListener(ActionListener listener) {
        this.shopButton.addActionListener(listener);
    }

    void setLoadSaveListener(ActionListener listener) {
        this.loadSaveButton.addActionListener(listener);
    }

    void setQuitListener(ActionListener listener) {
        this.quitButton.addActionListener(listener);
    }

}