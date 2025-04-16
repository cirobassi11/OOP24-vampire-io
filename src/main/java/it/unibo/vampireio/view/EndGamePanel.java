package it.unibo.vampireio.view;

import javax.swing.JButton;

import it.unibo.vampireio.controller.ScoreData;

class EndGamePanel extends BasePanel {

    private ScoreData score;

    private JButton continueButton;

    EndGamePanel(GameViewImpl view) {
        super(view);

        if(this.score != null) {
            //stampa le informazioni di score
        }

        this.continueButton = addButton("CONTINUE", 0, 0);
        this.continueButton.addActionListener(e -> {
            this.view.showScreen(GameViewImpl.START);
        });
    }

    public void setScore(ScoreData score) {
        this.score = score;
    }
}
