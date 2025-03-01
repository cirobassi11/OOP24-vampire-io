package it.unibo.vampireio.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

class SettingsPanel extends BasePanel {

    SettingsPanel(GameViewImpl view) {
        super(view);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 30, 10);
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.SOUTH;

        gbc.gridy = 0;
        JLabel resolutionLabel = new JLabel("Resolution:");
        this.add(resolutionLabel, gbc);

        gbc.gridy = 1;
        JComboBox<String> resolutionComboBox = this.createStyledComboBox(this.buttonSize);
        for (Dimension res : GameViewImpl.resolutions) {
            resolutionComboBox.addItem((int) res.getWidth() + "x" + (int) res.getHeight());
        }
        resolutionComboBox.setSelectedItem((int) view.getCurrentFrameSize().getWidth() + "x" + (int) view.getCurrentFrameSize().getHeight());
        this.add(resolutionComboBox, gbc);

        //DIFFICOLTA??

        //SUONI??

        //SALVATAGGIO PROGRESSI ABILITATO??

        //PERCORSO SALVATAGGIO PROGRESSI??
        
        gbc.gridy = 2;
        JButton applyButton = createStyledButton("APPLY", this.buttonSize);
        applyButton.addActionListener(e -> {
            String selectedResolution = (String) resolutionComboBox.getSelectedItem();
            String[] resolution = selectedResolution.split("x");
            view.setResolution(new Dimension(Integer.parseInt(resolution[0]), Integer.parseInt(resolution[1])));
        });
        this.add(applyButton, gbc);

        gbc.gridy = 3;
        JButton backButton = createStyledButton("BACK", this.buttonSize);
        backButton.addActionListener(e -> view.showScreen(GameViewImpl.MAIN_MENU));
        this.add(backButton, gbc);
    }
}