package it.unibo.vampireio.view;

class ScoreBoardPanel extends BasePanel {
    
    ScoreBoardPanel(GameViewImpl view) {
        super(view);

        //visualizza lista punteggi

        this.addButton("BACK", 2, e -> view.showScreen(GameViewImpl.START));
    }
}