package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import it.unibo.vampireio.controller.ScoreData;

class EndGamePanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;
    
    private static final int SECONDS_PER_MINUTE = 60;

    private final JButton returnMenuButton;
    private final JLabel scoreLabel;

    EndGamePanel(final FrameManager frameManager) {
        super(frameManager);

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
            + "<br>Time: " + (int) score.getSessionTime() / 1000 / SECONDS_PER_MINUTE + "min " 
            + (int) score.getSessionTime() / 1000 % SECONDS_PER_MINUTE + "sec"
            + "</html>"
        );
    }

    void setReturnMenuListener(final ActionListener listener) {
        this.returnMenuButton.addActionListener(listener);
    }
}
