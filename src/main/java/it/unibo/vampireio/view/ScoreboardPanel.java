package it.unibo.vampireio.view;

import java.util.LinkedList;
import java.util.List;
import it.unibo.vampireio.controller.ScoreData;

class ScoreBoardPanel extends BasePanel {
    
    ScoreBoardPanel(GameViewImpl view) {
        super(view);

        List<ScoreData> scores = this.view.getController().getScores();
        List<String> scoreNames = new LinkedList<>();
        for (ScoreData score : scores) {
            scoreNames.add(String.valueOf(score.getScore()));
        }

        this.addScrollableList(scoreNames, 0, 0);

        this.addButton("BACK", 0, 1, e -> view.showScreen(GameViewImpl.START));
    }
}