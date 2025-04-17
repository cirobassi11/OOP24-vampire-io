package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JList;
import javax.swing.JButton;

class ChooseCharacterPanel extends BasePanel {

    private JButton confirmButton;
    private JButton backButton;
    private JList<String> characterList;

    ChooseCharacterPanel(GameViewImpl view) {
        super(view);

        List<String> characterNames = List.of("antonio");//prendere la lista dei personaggi

        this.characterList = this.addScrollableList(characterNames, 0, 0);///////////////
        this.confirmButton = this.addButton("CONFIRM", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
    }

    String getSelectedCharacter() {
        return this.characterList.getSelectedValue();
    }

    void setConfirmCharacterListener(ActionListener listener) {
        this.confirmButton.addActionListener(listener);
    }

    void setBackListener(ActionListener listener) {
        this.backButton.addActionListener(listener);
    }
}