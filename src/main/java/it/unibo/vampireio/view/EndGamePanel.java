package it.unibo.vampireio.view;

class EndGamePanel extends BasePanel {

    EndGamePanel(GameViewImpl view) {
        super(view);

        //VISUALIZZAZIONE PUNTEGGIO PARTITA

        this.addButton("CONTINUE", 0, e -> view.showScreen(GameViewImpl.START));
    }
}
