package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import java.util.List;
import it.unibo.vampireio.controller.ScoreData;
import javax.swing.JButton;
import javax.swing.JList;

class ScoreboardPanel extends BasePanel {
    
    private JButton backButton;
    private JList<String> scoresList;

    private List<ScoreData> scoresData;
    private List<String> scoreNames = List.of();

    ScoreboardPanel(GameViewImpl view) {
        super(view);

        this.scoresList = this.addScrollableList(this.scoreNames, 0, 0);
        this.backButton = this.addButton("BACK", 0, 1);
    }

    void setScoresData(List<ScoreData> scoresData) {
        this.scoresData = scoresData;
        if(scoresData == null || scoresData.isEmpty()) {
            this.scoreNames = List.of();
        }
        else {
            this.scoreNames = scoresData.stream()
                    .map(ScoreData::getCharacterName) /////
                    .toList();
        }
        this.scoresList.setListData(this.scoreNames.toArray(new String[0]));
    }

    void setBackListener(ActionListener listener) {
        this.backButton.addActionListener(listener);
    }
}