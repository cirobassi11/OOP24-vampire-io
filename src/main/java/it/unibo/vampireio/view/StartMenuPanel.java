package it.unibo.vampireio.view;

class StartMenuPanel extends BasePanel {
    
    StartMenuPanel(GameViewImpl view) {
        super(view);

        this.addButton("START", 1, 0, e -> view.showScreen(GameViewImpl.CHOOSE_CHARACTER));
        this.addButton("SCOREBOARD", 0, 1, e -> view.showScreen(GameViewImpl.SCOREBOARD));
        this.addButton("POWER UP", 1, 1, e -> view.showScreen(GameViewImpl.UNLOCKABLE_POWERUPS));
        this.addButton("LOAD SAVING", 2, 1, e -> {
            this.view.updateSavingsList();
            this.view.showScreen(GameViewImpl.SAVING_MENU);
        });
        this.addButton("QUIT", 1, 2, e -> view.quit());
    }
}