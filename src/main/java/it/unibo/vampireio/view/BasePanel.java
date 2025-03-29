package it.unibo.vampireio.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

abstract class BasePanel extends JPanel {
    protected static final Color BUTTON_BACKGROUND = new Color(50, 50, 50);
    protected static final Color BUTTON_HOVER = new Color(200, 0, 0);
    protected static final Color BUTTON_BORDER = new Color(255, 215, 0);
    protected static final Color COMBOBOX_BACKGROUND = new Color(50, 50, 50);
    protected static final Color COMBOBOX_BORDER = new Color(200, 200, 200);
    protected static final Font DEFAULT_FONT = new Font("Serif", Font.BOLD, 24);

    protected final GameViewImpl view;
    private final List<JButton> buttons = new LinkedList<>();

    BasePanel(GameViewImpl view) {
        this.view = view;
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.updateButtonsSize();
        g.drawImage(this.view.getBackgroundImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(DEFAULT_FONT);
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
        buttons.add(button);
        return button;
    }

    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(DEFAULT_FONT.deriveFont(16f)); // Font ridotto per le ComboBox
        comboBox.setBackground(COMBOBOX_BACKGROUND);
        comboBox.setForeground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(COMBOBOX_BORDER, 2));
        return comboBox;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(DEFAULT_FONT);
        label.setForeground(Color.WHITE);
        return label;
    }

    protected void addComponent(Component component, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        this.add(component, gbc);
    }

    protected void addButton(String text, int gridy, ActionListener action) {
        JButton button = createButton(text);
        button.addActionListener(action);
        addComponent(button, gridy);
    }

    protected void addComboBox(int gridy) {
        JComboBox<String> comboBox = createComboBox();
        addComponent(comboBox, gridy);
    }

    protected void addLabel(String text, int gridy) {
        JLabel label = createLabel(text);
        addComponent(label, gridy);
    }

    void updateButtonsSize() {
        Dimension newSize = new Dimension(this.view.getFrameSize().width / 6, this.view.getFrameSize().height / 15);
        buttons.forEach(button -> {
            button.setPreferredSize(newSize);
            button.setFont(DEFAULT_FONT.deriveFont((float) this.view.getFrameSize().height / 30));
        });
    }
}