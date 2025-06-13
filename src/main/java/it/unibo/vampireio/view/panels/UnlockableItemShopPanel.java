package it.unibo.vampireio.view.panels;

import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import it.unibo.vampireio.controller.data.UnlockableItemData;
import it.unibo.vampireio.view.manager.FrameManager;
import it.unibo.vampireio.view.manager.ImageManager;

/**
 * UnlockableItemShopPanel is a panel that displays a shop for unlockable items.
 * It allows players to view available items, their descriptions, prices, and
 * purchase them if they have enough coins.
 */
public final class UnlockableItemShopPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private final transient ImageManager imageManager;

    private List<UnlockableItemData> unlockableItemsData = List.of();

    private final JButton buyButton;
    private final JButton backButton;
    private final JList<String> itemsList;
    private final JLabel coinsLabel;
    private final JLabel iconLabel;
    private final JLabel descriptionLabel;
    private final JLabel priceLabel;

    /**
     * Constructs an UnlockableItemShopPanel with the specified FrameManager and
     * ImageManager.
     *
     * @param frameManager the FrameManager to manage frames
     * @param imageManager the ImageManager to handle images
     */
    public UnlockableItemShopPanel(final FrameManager frameManager, final ImageManager imageManager) {
        super(frameManager);
        this.imageManager = imageManager;

        int gridy = 0;

        this.coinsLabel = this.addLabel("", 0, 0);
        this.itemsList = this.addScrollableList(List.of(), 0, ++gridy);
        this.iconLabel = this.addImage(new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)), 0,
                ++gridy);
        this.descriptionLabel = this.addLabel(" ", 0, ++gridy);
        this.priceLabel = this.addLabel(" ", 0, ++gridy);
        this.buyButton = this.addButton("BUY", 0, ++gridy);
        this.backButton = this.addButton("BACK", 0, ++gridy);
        this.buyButton.setEnabled(false);
    }

    /**
     * Sets the data for unlockable items in the shop.
     *
     * @param itemsData a list of UnlockableItemData containing item information
     */
    public void setUnlockableItemData(final List<UnlockableItemData> itemsData) {
        List<String> itemsNames = List.of();

        if (itemsData != null && !itemsData.isEmpty()) {
            itemsNames = itemsData.stream()
                    .map(item -> item.getName() + ((item.getMaxLevel() != 0) ? (" ["
                            + item.getCurrentLevel() + "/"
                            + item.getMaxLevel() + "]") : ""))
                    .toList();
        }

        this.unlockableItemsData = List.copyOf(itemsData);
        this.itemsList.setListData(itemsNames.toArray(new String[0]));
        this.itemsList.addListSelectionListener(e -> itemInfo());
    }

    /**
     * Gets the currently selected item in the shop.
     *
     * @return the ID of the selected item, or null if no item is selected
     */
    public String getSelectedItem() {
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

    /**
     * Sets the listener for the buy button.
     *
     * @param listener the ActionListener to be set for the buy button
     */
    public void setBuyItemListener(final ActionListener listener) {
        this.buyButton.addActionListener(listener);
    }

    /**
     * Sets the listener for the back button.
     *
     * @param listener the ActionListener to be set for the back button
     */
    public void setBackListener(final ActionListener listener) {
        this.backButton.addActionListener(listener);
    }

    /**
     * Sets the listener for list selection events.
     *
     * @param listener the ListSelectionListener to be set for the items list
     */
    public void setListSelectionListener(final ListSelectionListener listener) {
        this.itemsList.addListSelectionListener(listener);
    }

    /**
     * Disables the buy button.
     */
    public void disableBuyButton() {
        this.buyButton.setEnabled(false);
    }

    /**
     * Enables the buy button.
     */
    public void enableBuyButton() {
        this.buyButton.setEnabled(true);
    }

    /**
     * Sets the amount of coins available to the player.
     *
     * @param coins the number of coins to display
     */
    public void setCoinsAmount(final int coins) {
        this.coinsLabel.setText("Coins: " + coins);
    }
}
