package it.unibo.vampireio.view;

import java.util.List;
import javax.swing.JList;
import it.unibo.vampireio.controller.UnlockablePowerupData;
import javax.swing.JButton;
import java.awt.event.ActionListener;

class UnlockablePowerUpsPanel extends BasePanel {
    private List<UnlockablePowerupData> unlockablePowerupsData = List.of();
    private List<String> powerupNames = List.of();

    private JButton buyButton;
    private JButton backButton;
    private JList<String> powerupsList;

    UnlockablePowerUpsPanel(GameViewImpl view) {
        super(view);
        
        List<String> powerupNames = List.of();

        this.powerupsList = this.addScrollableList(powerupNames, 0, 0);
        this.buyButton = this.addButton("BUY", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
    }

    void setUnlockablePowerupsData(List<UnlockablePowerupData> unlockablePowerupsData) {
        this.unlockablePowerupsData = unlockablePowerupsData;
        if(unlockablePowerupsData == null || unlockablePowerupsData.isEmpty()) {
            this.powerupNames = List.of();
        }
        else {
            this.unlockablePowerupsData = unlockablePowerupsData;
            this.powerupNames = unlockablePowerupsData.stream()
                    .map(UnlockablePowerupData::getName)
                    .toList();
        }
        this.powerupsList.setListData(this.powerupNames.toArray(new String[0]));
    }

    String getSelectedPowerup() {
        int selectedIndex = this.powerupsList.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= this.unlockablePowerupsData.size()) {
            return null;
        }
        return this.unlockablePowerupsData.get(selectedIndex).getId();
    }

    void setBuyPowerUpsListener(ActionListener listener) {
        this.buyButton.addActionListener(listener);
    }
    
    void setBackListener(ActionListener listener) {
        this.backButton.addActionListener(listener);
    }

}