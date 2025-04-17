package it.unibo.vampireio.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;

class PausePanel extends BasePanel {
    
    private JButton continueButton;
    private JButton exitButton;

    PausePanel(GameViewImpl view) {
        super(view);
        
        this.continueButton = this.addButton("CONTINUE", 0, 0);
        this.exitButton = this.addButton("EXIT", 0, 1);
    }

    void setContinueListener(ActionListener listener) {
        this.continueButton.addActionListener(listener);
    }

    void setExitListener(ActionListener listener) {
        this.exitButton.addActionListener(listener);
    }

}
