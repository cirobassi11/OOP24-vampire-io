package it.unibo.vampireio.view;

class MainMenuPanel extends BasePanel {
    
    MainMenuPanel(GameViewImpl view) {
        super(view);

        this.addButton("START", 0, e -> view.showScreen(GameViewImpl.CHOOSE_CHARACTER));
        this.addButton("POWER UP", 1, e -> view.showScreen(GameViewImpl.UNLOCKABLE_POWERUPS));
        this.addButton("SETTINGS", 2, e -> view.showScreen(GameViewImpl.SETTINGS));
    }
}