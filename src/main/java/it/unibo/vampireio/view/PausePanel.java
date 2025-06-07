package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import javax.swing.JButton;

class PausePanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private final JButton resumeButton;
    private final JButton exitButton;

    PausePanel(final FrameManager frameManager) {
        super(frameManager);
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
