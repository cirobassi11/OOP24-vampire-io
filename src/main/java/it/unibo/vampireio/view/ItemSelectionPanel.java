package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JList;
import it.unibo.vampireio.controller.ItemData;

class ItemSelectionPanel extends AbstractBasePanel {

    private List<ItemData> itemsData = List.of();
    private List<String> itemNames = List.of();

    private JButton chooseItemButton;
    private JList<String> itemList;

    ItemSelectionPanel(final GameViewImpl view) {
        super(view);

        this.chooseItemButton = this.addButton("CHOOSE", 0, 1);
        this.itemList = this.addScrollableList(List.of(), 0, 0);
    }

    public void setChooseItemListener(final ActionListener listener) {
        this.chooseItemButton.addActionListener(listener);
    }

    void setItemsData(final List<ItemData> itemsData) {
        this.itemsData = itemsData;
        if (itemsData == null || itemsData.isEmpty()) {
            this.itemNames = List.of();
            return;
        } else {
            this.itemsData = itemsData;
            this.itemNames = itemsData.stream()
                    .map(ItemData::getName)
                    .toList();
            this.itemList.setListData(this.itemNames.toArray(new String[0]));
        }
    }

    public String getSelectedItem() {
        final int selectedIndex = this.itemList.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < this.itemsData.size()) {
            return this.itemsData.get(selectedIndex).getId();
        }
        return null;
    }
}
