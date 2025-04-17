package it.unibo.vampireio.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import it.unibo.vampireio.controller.ScoreData;

class EndGamePanel extends BasePanel {

    private ScoreData score;

    private JButton returnMenuButton;

    EndGamePanel(GameViewImpl view) {
        super(view);

        if(this.score != null) {
            //stampa le informazioni di score
        }

        this.returnMenuButton = addButton("CONTINUE", 0, 0);
    }

    public void setScore(ScoreData score) {
        this.score = score;
    }

    void setReturnMenuListener(ActionListener listener) {
        this.returnMenuButton.addActionListener(listener);
    }
}
