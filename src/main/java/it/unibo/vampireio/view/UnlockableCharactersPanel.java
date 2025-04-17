package it.unibo.vampireio.view;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.event.ActionListener;

class UnlockableCharactersPanel extends BasePanel {
    private JButton buyButton;
    private JButton backButton;
    private JList charactersList;

    UnlockableCharactersPanel(GameViewImpl view) {
        super(view);
        
        List<String> unlockableCharacters = List.of();

        /*scrollablelist con tutti i characters*/
        this.charactersList = this.addScrollableList(unlockableCharacters, 0, 0);
        this.buyButton = this.addButton("BUY", 0, 1);        
        this.backButton = this.addButton("BACK", 0, 2);
    }
    
    void setBuyCharactersListener(ActionListener listener) {
        this.buyButton.addActionListener(listener);
    }
    
    void setBackListener(ActionListener listener) {
        this.backButton.addActionListener(listener);
    }

}