package it.unibo.vampireio.view;

import java.util.List;
import javax.swing.JList;
import javax.swing.JButton;

class UnlockablePowerUpsPanel extends BasePanel {
    private JButton buyButton;
    private JButton backButton;
    private JList powerupList;

    UnlockablePowerUpsPanel(GameViewImpl view) {
        super(view);
        
        /* scrollablelist con tutti i powerup */
        this.powerupList = this.addScrollableList(List.of(), 0, 0);

        this.buyButton = this.addButton("BUY", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
        this.backButton.addActionListener(e -> {
            this.view.showScreen(GameViewImpl.SHOP);
        });
    }
}