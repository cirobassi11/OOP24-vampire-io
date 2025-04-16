package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;

class SaveSelectionPanel extends BasePanel {

    private JButton selectButton;
    private JButton backButton;

    private List<String> saves;
    private JList<String> savesList;

    SaveSelectionPanel(GameViewImpl view) {
        super(view);

        this.savesList = this.addScrollableList(List.of(), 0, 0);
        
        this.selectButton = this.addButton("SELECT", 0, 1);

        this.backButton = this.addButton("BACK", 0, 2);
        this.backButton.addActionListener(e -> {
            this.view.showScreen(GameViewImpl.SAVE_MENU);
        });

    }

    void setChooseSaveListener(ActionListener listener) {
        this.selectButton.addActionListener(listener);
    }

    void updateSavesList(List<String> saves) {
        this.saves = saves;
        this.savesList.setListData(this.saves.toArray(new String[0]));
    }

	public String getSelectedSave() {
		return this.savesList.getSelectedValue();
    }
}