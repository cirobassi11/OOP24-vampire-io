package it.unibo.vampireio.view;

import java.util.List;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;

class UnlockablePowerUpsPanel extends BasePanel {
    
    private JButton buyButton;
    private JButton backButton;
    private JList powerupList;

    UnlockablePowerUpsPanel(GameViewImpl view) {
        super(view);
        
        List<String> powerupNames = List.of();/////

        this.powerupList = this.addScrollableList(powerupNames, 0, 0);
        this.buyButton = this.addButton("BUY", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
    }

    void setBuyPowerUpsListener(ActionListener listener) {
        this.buyButton.addActionListener(listener);
    }
    
    void setBackListener(ActionListener listener) {
        this.backButton.addActionListener(listener);
    }
}