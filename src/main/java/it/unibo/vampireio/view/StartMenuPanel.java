package it.unibo.vampireio.view;

class StartMenuPanel extends BasePanel {
    
    StartMenuPanel(GameViewImpl view) {
        super(view);

        this.addButton("START", 0, e -> view.showScreen(GameViewImpl.CHOOSE_CHARACTER));
        this.addButton("POWER UP", 1, e -> view.showScreen(GameViewImpl.UNLOCKABLE_POWERUPS));
        this.addButton("SCOREBOARD", 2, e -> view.showScreen(GameViewImpl.SCOREBOARD));
        this.addButton("LOAD GAME", 3, e -> view.showScreen(GameViewImpl.SAVING));
        this.addButton("QUIT", 4, e -> view.quit());
    }
}