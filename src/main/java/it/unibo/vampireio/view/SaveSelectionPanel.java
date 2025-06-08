package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JList;

class SaveSelectionPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private final JButton selectButton;
    private final JButton backButton;

    private final JList<String> savesList;

    SaveSelectionPanel(final FrameManager frameManager) {
        super(frameManager);
        this.savesList = this.addScrollableList(List.of(), 0, 0);
        this.selectButton = this.addButton("SELECT", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
    }

    void setChooseSaveListener(final ActionListener listener) {
        this.selectButton.addActionListener(listener);
    }

    void setBackListener(final ActionListener listener) {
        this.backButton.addActionListener(listener);
    }

    void updateSavesList(final List<String> saves) {
        this.savesList.setListData(saves.toArray(new String[0]));
    }

    public String getSelectedSave() {
        return this.savesList.getSelectedValue();
    }

}
