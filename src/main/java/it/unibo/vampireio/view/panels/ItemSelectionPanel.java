package it.unibo.vampireio.view.panels;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JList;
import it.unibo.vampireio.controller.data.ItemData;
import it.unibo.vampireio.view.manager.FrameManager;

/**
 * ItemSelectionPanel is a panel that allows the user to select an item from a
 * list.
 * It provides methods to set listeners for choosing an item and updating the
 * list of items displayed.
 */
public final class ItemSelectionPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private List<ItemData> itemsData = List.of();

    private final JButton chooseItemButton;
    private final JList<String> itemList;

    /**
     * Constructs an ItemSelectionPanel with the specified FrameManager.
     *
     * @param frameManager the FrameManager to manage frames
     */
    public ItemSelectionPanel(final FrameManager frameManager) {
        super(frameManager);

        this.chooseItemButton = this.addButton("CHOOSE", 0, 1);
        this.itemList = this.addScrollableList(List.of(), 0, 0);
    }

    /**
     * Sets a listener for the choose item button.
     *
     * @param listener the ActionListener to set for the choose item button
     */
    public void setChooseItemListener(final ActionListener listener) {
        this.chooseItemButton.addActionListener(listener);
    }

    /**
     * Sets the data for the items to be displayed in the item list.
     * If the provided list is not empty, it updates the item list with the names of
     * the items.
     *
     * @param itemsData a list of ItemData objects representing the items to display
     */
    public void setItemsData(final List<ItemData> itemsData) {
        final List<String> itemNames;
        this.itemsData = List.copyOf(itemsData);
        if (itemsData != null && !itemsData.isEmpty()) {
            itemNames = itemsData.stream()
                    .map(ItemData::getName)
                    .toList();
            this.itemList.setListData(itemNames.toArray(new String[0]));
        }
    }

    /**
     * Returns the ID of the selected item.
     * If no item is selected, returns null.
     *
     * @return the ID of the selected item or null if no item is selected
     */
    public String getSelectedItem() {
        final int selectedIndex = this.itemList.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < this.itemsData.size()) {
            return this.itemsData.get(selectedIndex).getId();
        }
        return null;
    }
}
