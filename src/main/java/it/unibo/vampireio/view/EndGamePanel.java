package it.unibo.vampireio.view;

import it.unibo.vampireio.controller.ScoreData;

class EndGamePanel extends BasePanel {

    private ScoreData score;

    EndGamePanel(GameViewImpl view) {
        super(view);

        if(this.score != null) {
            //stampa le informazioni di score
        }

        this.addButton("CONTINUE", 0, 0, e -> view.showScreen(GameViewImpl.START));
    }

    public void setScore(ScoreData score) {
        this.score = score;
    }
}
