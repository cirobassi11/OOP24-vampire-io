package it.unibo.vampireio.view;

import javax.swing.JButton;
import java.awt.event.ActionListener;

class SaveMenuPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private final JButton newSaveButton;
    private final JButton loadButton;

    SaveMenuPanel(final FrameManager frameManager) {
        super(frameManager);
        this.newSaveButton = this.addButton("NEW GAME", 0, 0);
        this.loadButton = this.addButton("LOAD GAME", 0, 1);
    }

    void setNewSaveListener(final ActionListener listener) {
        this.newSaveButton.addActionListener(listener);
    }

    void setShowSaveListener(final ActionListener listener) {
        this.loadButton.addActionListener(listener);
    }

}
