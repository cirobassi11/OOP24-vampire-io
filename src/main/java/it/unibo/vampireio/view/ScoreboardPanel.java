package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import it.unibo.vampireio.controller.ScoreData;

class ScoreboardPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private final JButton backButton;
    private final JList<String> scoresList;
    private final JLabel scoreLabel;

    private transient List<ScoreData> scoresData;

    ScoreboardPanel(final FrameManager frameManager) {
        super(frameManager);

        this.scoresList = this.addScrollableList(List.of(), 0, 0);
        this.scoreLabel = this.addLabel("", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);

        // Aggiorna i dettagli appena si seleziona un salvataggio
        this.scoresList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSelectedScoreDetails();
            }
        });
    }

    void setScoresData(final List<ScoreData> scoresData) {
        this.scoresData = scoresData;
        final List<String> scoreNames;

        if (scoresData == null || scoresData.isEmpty()) {
            scoreNames = List.of();
            this.scoreLabel.setText("");
        } else {
            scoreNames = scoresData.stream()
                    .map(ScoreData::getCharacterName)
                    .toList();
        }

        this.scoresList.setListData(scoreNames.toArray(new String[0]));

        // Seleziona automaticamente il primo elemento
        if (!scoreNames.isEmpty()) {
            this.scoresList.setSelectedIndex(0);
            updateSelectedScoreDetails();
        }
    }

    private void updateSelectedScoreDetails() {
        final int index = this.scoresList.getSelectedIndex();
        if (index >= 0 && index < this.scoresData.size()) {
            final ScoreData selected = this.scoresData.get(index);
            final String details = String.format(
                    "<html>Character: %s<br>Session Time: %.2f seconds<br>Kills: %d<br>"
                            + "Level: %d<br>Coins: %d<br>Score: %d</html>",
                    selected.getCharacterName(),
                    selected.getSessionTime() / 1000.0,
                    selected.getKillCounter(),
                    selected.getLevelCounter(),
                    selected.getCoinCounter(),
                    selected.getScore());
            this.scoreLabel.setText(details);
        }
    }

    void setBackListener(final ActionListener listener) {
        this.backButton.addActionListener(listener);
    }

}
