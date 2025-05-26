package it.unibo.vampireio.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;

class PausePanel extends BasePanel {
    
    private JButton resumeButton;
    private JButton exitButton;

    PausePanel(final GameViewImpl view) {
        super(view);
        
        this.resumeButton = this.addButton("RESUME", 0, 0);
        this.exitButton = this.addButton("EXIT", 0, 1);
    }

    void setResumeListener(final ActionListener listener) {
        this.resumeButton.addActionListener(listener);
    }

    void setExitListener(final ActionListener listener) {
        this.exitButton.addActionListener(listener);
    }

}
