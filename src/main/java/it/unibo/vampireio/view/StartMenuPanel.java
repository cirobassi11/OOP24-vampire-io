package it.unibo.vampireio.view;

class StartMenuPanel extends BasePanel {
    
    StartMenuPanel(GameViewImpl view) {
        super(view);

        this.addButton("START", 0, 0, e -> view.showScreen(GameViewImpl.CHOOSE_CHARACTER));
        this.addButton("POWER UP", 0, 1, e -> view.showScreen(GameViewImpl.UNLOCKABLE_POWERUPS));
        this.addButton("SCOREBOARD", 0, 2, e -> view.showScreen(GameViewImpl.SCOREBOARD));
        this.addButton("LOAD GAME", 0, 3, e -> {
            this.view.updateSavingList();
            this.view.showScreen(GameViewImpl.SAVING_MENU);
        });
        this.addButton("QUIT", 0, 4, e -> view.quit());
    }
}