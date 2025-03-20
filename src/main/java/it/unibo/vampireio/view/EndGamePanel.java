package it.unibo.vampireio.view;

class EndGamePanel extends BasePanel {

    EndGamePanel(GameViewImpl view) {
        super(view);

        this.addButton("CONTINUE", 0, e -> view.showScreen(GameViewImpl.MAIN_MENU));
    }
}
