package it.unibo.vampireio.view;

import java.util.List;

import javax.swing.JList;
import it.unibo.vampireio.controller.UnlockablePowerupData;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;

class UnlockablePowerUpsPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private List<UnlockablePowerupData> unlockablePowerupsData = List.of();
    private List<String> powerupNames = List.of();

    private final JButton buyButton;
    private final JButton backButton;
    private final JList<String> powerupsList;
    private final JLabel coinsLabel;
    private final JLabel descriptionLabel;
    private final JLabel priceLabel;

    UnlockablePowerUpsPanel(final GameViewImpl view) {
        super(view);

        final List<String> powerupNames = List.of();

        this.coinsLabel = this.addLabel("", 0, 0);
        this.powerupsList = this.addScrollableList(powerupNames, 0, 1);
        this.descriptionLabel = this.addLabel(" ", 0, 2);
        this.priceLabel = this.addLabel(" ", 0, 3);
        this.buyButton = this.addButton("BUY", 0, 4);
        this.backButton = this.addButton("BACK", 0, 5);
    }

    void setUnlockablePowerupsData(final List<UnlockablePowerupData> unlockablePowerupsData) {
        this.unlockablePowerupsData = unlockablePowerupsData;
        this.powerupNames = List.of();

        if (unlockablePowerupsData != null && !unlockablePowerupsData.isEmpty()) {
            this.powerupNames = unlockablePowerupsData.stream()
            .map(powerup -> powerup.getName() + " [" 
                        + powerup.getCurrentLevel() + "/" 
                        + powerup.getMaxLevel() + "]: ")
            .toList();
        }

        this.powerupsList.setListData(this.powerupNames.toArray(new String[0]));
        this.powerupsList.addListSelectionListener(e -> getPowerupInfo());
    }

    String getSelectedPowerup() {
        final int selectedIndex = this.powerupsList.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= this.unlockablePowerupsData.size()) {
            return null;
        }
        return this.unlockablePowerupsData.get(selectedIndex).getId();
    }

    private void getPowerupInfo() {
        final int selectedIndex = this.powerupsList.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < this.unlockablePowerupsData.size()) {
            final UnlockablePowerupData data = this.unlockablePowerupsData.get(selectedIndex);
            this.descriptionLabel.setText("" + data.getDescription());
            this.priceLabel.setText("Price: " + data.getPrice());
        } else {
            this.descriptionLabel.setText("");
            this.priceLabel.setText("");
        }
    }

    void setBuyPowerUpsListener(final ActionListener listener) {
        this.buyButton.addActionListener(listener);
    }

    void setBackListener(final ActionListener listener) {
        this.backButton.addActionListener(listener);
    }

    void setCoinsAmount(final int coins) {
        this.coinsLabel.setText("Coins: " + coins);
    }

}
