package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JList;
import it.unibo.vampireio.controller.ItemData;

class ItemSelectionPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private List<ItemData> itemsData = List.of();

    private final JButton chooseItemButton;
    private final JList<String> itemList;

    ItemSelectionPanel(final FrameManager frameManager) {
        super(frameManager);

        this.chooseItemButton = this.addButton("CHOOSE", 0, 1);
        this.itemList = this.addScrollableList(List.of(), 0, 0);
    }

    void setChooseItemListener(final ActionListener listener) {
        this.chooseItemButton.addActionListener(listener);
    }

    void setItemsData(final List<ItemData> itemsData) {
        final List<String> itemNames;
        this.itemsData = itemsData;
        if (itemsData != null && !itemsData.isEmpty()) {
            this.itemsData = itemsData;
            itemNames = itemsData.stream()
                    .map(ItemData::getName)
                    .toList();
            this.itemList.setListData(itemNames.toArray(new String[0]));
        }
    }

    String getSelectedItem() {
        final int selectedIndex = this.itemList.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < this.itemsData.size()) {
            return this.itemsData.get(selectedIndex).getId();
        }
        return null;
    }
}
