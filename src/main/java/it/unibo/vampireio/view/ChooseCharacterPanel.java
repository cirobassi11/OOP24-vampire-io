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

        this.characterList = this.addScrollableList(List.of("antonio"), 0, 0);//////////

        this.confirmButton = this.addButton("CONFIRM", 0, 1);

        this.backButton = this.addButton("BACK", 0, 2);
        this.backButton.addActionListener(e -> {
            this.view.showScreen(GameViewImpl.START);//////////////
        });
    }

    String getSelectedCharacter() {
        return this.characterList.getSelectedValue();
    }

    void setStartListener(ActionListener listener) {
        this.confirmButton.addActionListener(listener);
    }

    void setBackListener(ActionListener listener) {
        this.backButton.addActionListener(listener);
    }


}