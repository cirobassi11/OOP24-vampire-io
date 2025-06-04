package it.unibo.vampireio.view;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import it.unibo.vampireio.controller.UnlockableItemData;

class UnlockableItemShopPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private final ImageManager imageManager;

    private List<UnlockableItemData> unlockableItemsData = List.of();
    private List<String> itemsNames = List.of();

    private final JButton buyButton;
    private final JButton backButton;
    private final JList<String> itemsList;
    private final JLabel coinsLabel;
    private final JLabel iconLabel;
    private final JLabel descriptionLabel;
    private final JLabel priceLabel;

    UnlockableItemShopPanel(final GameViewImpl view, final ImageManager imageManager) {
        super(view);
        this.imageManager = imageManager;

        final List<String> powerupNames = List.of();

        this.coinsLabel = this.addLabel("", 0, 0);
        this.itemsList = this.addScrollableList(this.itemsNames, 0, 1);
        this.iconLabel = this.addImage(new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)), 0, 2);
        this.descriptionLabel = this.addLabel(" ", 0, 3);
        this.priceLabel = this.addLabel(" ", 0, 4);
        this.buyButton = this.addButton("BUY", 0, 5);
        this.backButton = this.addButton("BACK", 0, 6);
        this.buyButton.setEnabled(false);
    }

    void setUnlockableItemData(final List<UnlockableItemData> unlockableItemsData) {
        this.unlockableItemsData = unlockableItemsData;
        this.itemsNames = List.of();

        if (unlockableItemsData != null && !unlockableItemsData.isEmpty()) {
            this.itemsNames = unlockableItemsData.stream()
                    .map(item -> item.getName() + ((item.getMaxLevel() != 0) ? (" ["
                            + item.getCurrentLevel() + "/"
                            + item.getMaxLevel() + "]") : ""))
                    .toList();
        }

        this.itemsList.setListData(this.itemsNames.toArray(new String[0]));
        this.itemsList.addListSelectionListener(e -> itemInfo());
    }

    String getSelectedItem() {
        final int selectedIndex = this.itemsList.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= this.unlockableItemsData.size()) {
            return null;
        }
        return this.unlockableItemsData.get(selectedIndex).getId();
    }

    private void itemInfo() {
        final int selectedIndex = this.itemsList.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < this.unlockableItemsData.size()) {
            final UnlockableItemData data = this.unlockableItemsData.get(selectedIndex);
            final Image itemIcon = this.imageManager.getImage(data.getId());
            this.iconLabel.setIcon(new ImageIcon(itemIcon));
            this.descriptionLabel.setText(data.getDescription());
            this.priceLabel.setText("Price: " + data.getPrice());
        } else {
            this.iconLabel.setIcon(new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)));
            this.descriptionLabel.setText("");
            this.priceLabel.setText("");
        }
    }

    void setBuyItemListener(final ActionListener listener) {
        this.buyButton.addActionListener(listener);
    }

    void setBackListener(final ActionListener listener) {
        this.backButton.addActionListener(listener);
    }

    void setListSelectionListener(final ListSelectionListener listener) {
        this.itemsList.addListSelectionListener(listener);
    }

    void disableBuyButton() {
        this.buyButton.setEnabled(false);
    }

    void enableBuyButton() {
        this.buyButton.setEnabled(true);
    }

    void setCoinsAmount(final int coins) {
        this.coinsLabel.setText("Coins: " + coins);
    }

}
