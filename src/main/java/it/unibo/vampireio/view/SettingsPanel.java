package it.unibo.vampireio.view;

import javax.swing.JComboBox;

import java.awt.Dimension;

class SettingsPanel extends BasePanel {

    SettingsPanel(GameViewImpl view) {
        super(view);
        
        JComboBox<String> resolutionComboBox = this.createComboBox(this.buttonSize);
        for (Dimension res : GameViewImpl.RESOLUTIONS) {
            resolutionComboBox.addItem((int) res.getWidth() + "x" + (int) res.getHeight());
        }
        resolutionComboBox.setSelectedItem((int) view.getCurrentFrameSize().getWidth() + "x" + (int) view.getCurrentFrameSize().getHeight());
        this.addComboBox(resolutionComboBox, 0);
        

        this.addButton("APPLY", 1, e -> {
            String selectedResolution = (String) resolutionComboBox.getSelectedItem();
            String[] resolution = selectedResolution.split("x");
            view.setResolution(new Dimension(Integer.parseInt(resolution[0]), Integer.parseInt(resolution[1])));
        });

        this.addButton("BACK", 2, e -> view.showScreen(GameViewImpl.MAIN_MENU));
    }
}