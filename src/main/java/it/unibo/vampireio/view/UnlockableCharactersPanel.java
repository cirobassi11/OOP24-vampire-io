package it.unibo.vampireio.view;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JList;
class UnlockableCharactersPanel extends BasePanel {
    private JButton buyButton;
    private JButton backButton;
    private JList charactersList;

    UnlockableCharactersPanel(GameViewImpl view) {
        super(view);
        
        /*scrollablelist con tutti i characters*/
        this.charactersList = this.addScrollableList(List.of(), 0, 0);

        this.buyButton = this.addButton("BUY", 0, 1);
        this.buyButton.addActionListener(e -> {});
        
        this.backButton = this.addButton("BACK", 0, 2);
        this.backButton.addActionListener(e -> {
            this.view.showScreen(GameViewImpl.SHOP);
        });
    }
}