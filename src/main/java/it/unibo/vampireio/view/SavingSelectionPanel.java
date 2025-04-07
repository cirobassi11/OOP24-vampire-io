package it.unibo.vampireio.view;

import java.util.List;
import javax.swing.JList;

class SavingSelectionPanel extends BasePanel {
    private List<String> savings;
    private JList<String> savingList;

    SavingSelectionPanel(GameViewImpl view) {
        super(view);

        this.savingList = this.addScrollableList(List.of(), 0, 0);
        
        this.addButton("SELECT", 0, 1, e -> {
            String selectedSaving = this.savingList.getSelectedValue();
            if (selectedSaving != null) {
                this.view.getController().loadSaving(selectedSaving);
                this.view.showScreen(GameViewImpl.START);
            }
        });

        this.addButton("BACK", 0, 2, e -> view.showScreen(GameViewImpl.SAVING_MENU));
    }

    void updateSavingList() {
        this.savings = this.view.getController().getSavingNames();
        String[] savingArray = new String[this.savings.size()];
        for (int i = 0; i < this.savings.size(); i++) {
            savingArray[i] = this.savings.get(i);
        }
        this.savingList.setListData(savingArray);
    }
}