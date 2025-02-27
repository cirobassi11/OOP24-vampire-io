package it.unibo.vampireio.view;

import java.awt.Dimension;
import javax.swing.JButton;

class ChooseCharacterPanel extends BasePanel {
    ChooseCharacterPanel(GameViewImpl view) {
        super(view);

        JButton backButton = createStyledButton("BACK", new Dimension(200, 50));
        backButton.addActionListener(e -> view.showScreen(GameViewImpl.MAIN_MENU));

        this.add(backButton);
    }
}