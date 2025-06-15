package it.unibo.vampireio.view.panels;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import it.unibo.vampireio.controller.data.ScoreData;
import it.unibo.vampireio.view.manager.FrameManager;

/**
 * EndGamePanel is a panel that displays the end game screen with the player's score and options to return to the menu.
 * It shows the score details and provides a button to continue to the main menu.
 */
public final class EndGamePanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;
    private static final int SECONDS_PER_MINUTE = 60;

    private final JButton returnMenuButton;
    private final JLabel scoreLabel;

    /**
     * Constructs an EndGamePanel with the specified FrameManager.
     *
     * @param frameManager the FrameManager to manage frames
     */
    public EndGamePanel(final FrameManager frameManager) {
        super(frameManager);

        this.scoreLabel = this.addLabel("", 0, 0);
        this.returnMenuButton = addButton("CONTINUE", 0, 1);
    }

    /**
     * Sets the score details in the score label.
     *
     * @param score the ScoreData containing the score details to be displayed
     */
    public void setScore(final ScoreData score) {
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

    /**
     * Sets the listener for the return to menu button.
     *
     * @param listener the ActionListener to be set for the return to menu button
     */
    public void setReturnMenuListener(final ActionListener listener) {
        this.returnMenuButton.addActionListener(listener);
    }
}
