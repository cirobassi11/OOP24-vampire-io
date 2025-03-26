package it.unibo.vampireio.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

abstract class BasePanel extends JPanel {
    private static final Color BUTTON_BACKGROUND = new Color(50, 50, 50);
    private static final Color BUTTON_HOVER = new Color(200, 0, 0);
    private static final Color BUTTON_BORDER = new Color(255, 215, 0);
    private static final Color COMBOBOX_BACKGROUND = new Color(50, 50, 50);
    private static final Color COMBOBOX_BORDER = new Color(200, 200, 200);

    protected GameViewImpl view;
    protected Dimension buttonSize;

    private final List<JButton> buttons = new LinkedList<>();

    BasePanel(GameViewImpl view) {
        this.view = view;
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        this.updateButtonsSize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.view.getBackgroundImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    protected JButton createButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setFont(new Font("Serif", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(BUTTON_BORDER, 3));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(BUTTON_BACKGROUND);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(BUTTON_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(BUTTON_BACKGROUND);
            }
        });
        this.buttons.add(button);
        return button;
    }

    protected JComboBox<String> createComboBox(Dimension size) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setPreferredSize(size);
        comboBox.setFont(new Font("Arial", Font.BOLD, 16));
        comboBox.setBackground(COMBOBOX_BACKGROUND);
        comboBox.setForeground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(COMBOBOX_BORDER, 2));
        comboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton("▼");
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setContentAreaFilled(false);
                button.setForeground(Color.WHITE);
                return button;
            }
        });
        return comboBox;
    }

    protected void addButton(String text, int gridy, ActionListener action) {
        JButton button = createButton(text, this.buttonSize);
        button.addActionListener(action);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 30, 10);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        this.add(button, gbc);
    }

    protected void addComboBox(JComboBox<String> comboBox, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 30, 10);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        this.add(comboBox, gbc);
    }

    void updateButtonsSize() {
        this.buttonSize = new Dimension(this.view.getFrameSize().width / 6, this.view.getFrameSize().height / 15);
        this.buttons.forEach(button -> button.setPreferredSize(this.buttonSize));
        this.buttons.forEach(button -> button.setFont(new Font("Serif", Font.BOLD, this.view.getFrameSize().height / 30)));
    }
}