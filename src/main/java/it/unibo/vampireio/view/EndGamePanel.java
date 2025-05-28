package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import it.unibo.vampireio.controller.ScoreData;

class EndGamePanel extends AbstractBasePanel {

    private JButton returnMenuButton;
    private JLabel scoreLabel;

    EndGamePanel(final GameViewImpl view) {
        super(view);

        this.scoreLabel = this.addLabel("", 0, 0);
        this.returnMenuButton = addButton("CONTINUE", 0, 1);
    }

    void setScore(final ScoreData score) {
        this.scoreLabel.setText(
            "<html>"
            + "Score: " + score.getScore()
            + "<br>Character: " + score.getCharacterName()
            + "<br>Level: " + score.getLevelCounter()
            + "<br>Kills: " + score.getKillCounter()
            + "<br>Coins: " + score.getCoinCounter()
            + "<br>Time: " + (int) score.getSessionTime() / 1000 / 60 + "min " 
            + (int) score.getSessionTime() / 1000 % 60 + "sec"
            + "</html>"
        );
    }

    void setReturnMenuListener(final ActionListener listener) {
        this.returnMenuButton.addActionListener(listener);
    }
}
