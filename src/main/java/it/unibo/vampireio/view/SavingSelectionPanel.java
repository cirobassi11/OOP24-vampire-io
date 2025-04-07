package it.unibo.vampireio.view;

import java.util.List;
import javax.swing.JList;

class SavingSelectionPanel extends BasePanel {
    private List<String> savings;
    private JList<String> savesList;

    SavingSelectionPanel(GameViewImpl view) {
        super(view);

        this.updateSavingList();
        this.addScrollableList(this.savings, 0, 0);

        this.addButton("SELECT", 0, 1, e -> {
            String selectedSaving = savesList.getSelectedValue();
            if (selectedSaving != null) {
                System.out.println("Selected saving: " + selectedSaving);
                this.view.getController().loadSaving(selectedSaving);
                this.view.showScreen(GameViewImpl.START);
            }
        });

        this.addButton("BACK", 0, 2, e -> view.showScreen(GameViewImpl.SAVING_MENU));
    }

    void updateSavingList() {
        this.savings = this.view.getController().getSavingNames();
        //Aggiornare jlist
    }
}