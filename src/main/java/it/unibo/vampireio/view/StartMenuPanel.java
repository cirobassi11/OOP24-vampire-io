package it.unibo.vampireio.view;

import javax.swing.JButton;

class StartMenuPanel extends BasePanel {
    
    private JButton startButton;
    private JButton scoreboardButton;
    private JButton shopButton;
    private JButton loadSaveButton;
    private JButton quitButton;

    StartMenuPanel(GameViewImpl view) {
        super(view);

        this.startButton = this.addButton("START", 1, 0);
        this.startButton.addActionListener(e -> {
            this.view.showScreen(GameViewImpl.CHOOSE_CHARACTER);
        });

        this.scoreboardButton = this.addButton("SCOREBOARD", 0, 1);
        this.scoreboardButton.addActionListener(e -> {
            this.view.showScreen(GameViewImpl.SCOREBOARD);
        });

        this.shopButton = this.addButton("SHOP", 1, 1);
        this.shopButton.addActionListener(e -> {
            this.view.showScreen(GameViewImpl.SHOP);
        });
                
        this.loadSaveButton = this.addButton("LOAD SAVE", 2, 1);
        this.loadSaveButton.addActionListener(e -> {
            //this.view.updateSavesList();
            this.view.showScreen(GameViewImpl.SAVE_MENU);
        });
        
        this.quitButton = this.addButton("QUIT", 1, 2);
        this.quitButton.addActionListener(e -> {
            this.view.quit();
        });
    }
}