package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import it.unibo.vampireio.controller.ScoreData;
import javax.swing.JButton;

class ScoreboardPanel extends BasePanel {
    
    private JButton backButton;
    private List<ScoreData> scores;

    ScoreboardPanel(GameViewImpl view) {
        super(view);

        List<String> scoreNames = List.of();
        /*for (ScoreData score : scores) {
            scoreNames.add(String.valueOf(score.getScore()));
        }*/

        this.addScrollableList(scoreNames, 0, 0);
        
        this.backButton = this.addButton("BACK", 0, 1);
        this.backButton.addActionListener(e -> {
            this.view.showScreen(GameViewImpl.START);
        });
    }

    void setScoreList(List<ScoreData> scores) {
        this.scores = scores;
    }

    void setBackListener(ActionListener listener) {
        this.backButton.addActionListener(listener);
    }
}