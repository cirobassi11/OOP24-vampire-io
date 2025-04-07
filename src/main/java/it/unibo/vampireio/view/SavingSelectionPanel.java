package it.unibo.vampireio.view;

import java.util.List;
import javax.swing.JList;

class SavingSelectionPanel extends BasePanel {
    private List<String> savings;
    private JList<String> savingsList;

    SavingSelectionPanel(GameViewImpl view) {
        super(view);

        this.savingsList = this.addScrollableList(List.of(), 0, 0);
        
        this.addButton("SELECT", 0, 1, e -> {
            String selectedSaving = this.savingsList.getSelectedValue();
            if (selectedSaving != null) {
                this.view.getController().loadSaving(selectedSaving);
                this.view.showScreen(GameViewImpl.START);
            }
        });

        this.addButton("BACK", 0, 2, e -> view.showScreen(GameViewImpl.SAVING_MENU));
    }

    void updateSavingsList() {
        this.savings = this.view.getController().getSavingsNames();
        this.savingsList.setListData(this.savings.toArray(new String[0]));
    }
}