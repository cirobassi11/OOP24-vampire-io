package it.unibo.vampireio.view.panels;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import it.unibo.vampireio.controller.data.ScoreData;
import it.unibo.vampireio.view.manager.FrameManager;

/**
 * ScoreboardPanel is a panel that displays the scoreboard of the game.
 * It shows a list of scores, details of the selected score, and a button to go
 * back.
 */
public final class ScoreboardPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private final JButton backButton;
    private final JList<String> scoresList;
    private final JLabel scoreLabel;

    private transient List<ScoreData> scoresData;

    /**
     * Constructs a ScoreboardPanel with the specified FrameManager.
     *
     * @param frameManager the FrameManager to manage frames
     */
    public ScoreboardPanel(final FrameManager frameManager) {
        super(frameManager);

        this.scoresList = this.addScrollableList(List.of(), 0, 0);
        this.scoreLabel = this.addLabel("", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);

        this.scoresList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSelectedScoreDetails();
            }
        });
    }

    /**
     * Sets the data for the scores list and updates the display.
     *
     * @param scoresData the list of ScoreData to be displayed
     */
    public void setScoresData(final List<ScoreData> scoresData) {
        this.scoresData = List.copyOf(scoresData);
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

        if (!scoreNames.isEmpty()) {
            this.scoresList.setSelectedIndex(0);
            updateSelectedScoreDetails();
        }
    }

    /**
     * Sets the listener for the back button.
     *
     * @param listener the ActionListener to be set for the back button
     */
    public void setBackListener(final ActionListener listener) {
        this.backButton.addActionListener(listener);
    }

    private void updateSelectedScoreDetails() {
        final int index = this.scoresList.getSelectedIndex();
        if (index >= 0 && index < this.scoresData.size()) {
            final ScoreData selected = this.scoresData.get(index);
            final String details = String.format(
                    "<html>Score: %d<br>Character: %s<br>Session Time: %.2f seconds<br>Kills: %d<br>"
                            + "Level: %d<br>Coins: %d</html>",
                    selected.getScore(),
                    selected.getCharacterName(),
                    selected.getSessionTime() / 1000.0,
                    selected.getKillCounter(),
                    selected.getLevelCounter(),
                    selected.getCoinCounter());
            this.scoreLabel.setText(details);
        }
    }
}
