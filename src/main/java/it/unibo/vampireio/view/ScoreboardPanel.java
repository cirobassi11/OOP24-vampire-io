package it.unibo.vampireio.view;

import java.util.List;
import it.unibo.vampireio.controller.ScoreData;

class ScoreBoardPanel extends BasePanel {
    
    ScoreBoardPanel(GameViewImpl view) {
        super(view);

        List<ScoreData> scores = this.view.getController().getScores();
        //jlist con i "nomi" dei punteggi (?) e quando li selezioni mostra i dettagli a lato

        this.addButton("BACK", 0, 1, e -> view.showScreen(GameViewImpl.START));
    }
}