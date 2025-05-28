package it.unibo.vampireio.view;

import javax.swing.JButton;
import java.awt.event.ActionListener;

class SaveMenuPanel extends AbstractBasePanel {
    private JButton newSaveButton;
    private JButton loadButton;
    private JButton quitButton;

    SaveMenuPanel(final GameViewImpl view) {
        super(view);        
        this.newSaveButton = this.addButton("NEW GAME", 0, 0);
        this.loadButton = this.addButton("LOAD GAME", 0, 1);
        this.quitButton = this.addButton("QUIT", 0, 2);
    }

    void setNewSaveListener(final ActionListener listener) {
        this.newSaveButton.addActionListener(listener);
    }

    void setShowSaveListener(final ActionListener listener) {
        this.loadButton.addActionListener(listener);
    }

    void setQuitListener(final ActionListener listener) {
        this.quitButton.addActionListener(listener);
    }

}
