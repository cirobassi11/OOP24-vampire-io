package it.unibo.vampireio.view;

import java.util.List;

import javax.swing.JList;
import it.unibo.vampireio.controller.UnlockablePowerupData;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;

class UnlockablePowerUpsPanel extends BasePanel {
    private List<UnlockablePowerupData> unlockablePowerupsData = List.of();
    private List<String> powerupNames = List.of();

    private JButton buyButton;
    private JButton backButton;
    private JList<String> powerupsList;
    private JLabel coinsLabel;

    UnlockablePowerUpsPanel(GameViewImpl view) {
        super(view);
        
        List<String> powerupNames = List.of();

        this.coinsLabel = this.addLabel("", 0, 0);
        this.powerupsList = this.addScrollableList(powerupNames, 0, 1);
        this.buyButton = this.addButton("BUY", 0, 2);
        this.backButton = this.addButton("BACK", 0, 3);
    }

    void setUnlockablePowerupsData(List<UnlockablePowerupData> unlockablePowerupsData) {
        this.unlockablePowerupsData = unlockablePowerupsData;
        this.powerupNames = List.of();

        if(unlockablePowerupsData != null && !unlockablePowerupsData.isEmpty()) {
            this.powerupNames = unlockablePowerupsData.stream()
            .map(powerup -> powerup.getName() + " [" 
                        + powerup.getCurrentLevel() + "/" 
                        + powerup.getMaxLevel() + "]: "
                        + powerup.getDescription())
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

    void setCoinsAmount(int coins) {
        this.coinsLabel.setText("Coins: " + coins);
    }

}