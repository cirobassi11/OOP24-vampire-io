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
    private final List<Component> allComponents = new LinkedList<>();

    BasePanel(GameViewImpl view) {
        this.view = view;
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.updateComponentSize();
        g.drawImage(this.view.getBackgroundImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    private void addComponent(Component component, int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        this.add(component, gbc);
        allComponents.add(component);
    }

    protected JButton addButton(String text, int gridx, int gridy, ActionListener action) {
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
        button.addActionListener(action);
        addComponent(button, gridx, gridy);
        return button;
    }

    protected JLabel addLabel(String text, int gridx, int gridy) {
        JLabel label = new JLabel(text);
        label.setFont(DEFAULT_FONT);
        label.setForeground(Color.WHITE);
        addComponent(label, gridx, gridy);
        return label;
    }

    protected JComboBox<String> addComboBox(int gridx, int gridy) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(DEFAULT_FONT.deriveFont(16f));
        comboBox.setBackground(COMBOBOX_BACKGROUND);
        comboBox.setForeground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(COMBOBOX_BORDER, 2));
        addComponent(comboBox, gridx, gridy);
        return comboBox;
    }

    protected JList<String> addScrollableList(List<String> items, int gridx, int gridy) {
        DefaultListModel<String> model = new DefaultListModel<>();
        items.forEach(model::addElement);

        JList<String> list = new JList<>(model);
        list.setFont(DEFAULT_FONT.deriveFont(16f));
        list.setForeground(Color.WHITE);
        list.setBackground(new Color(40, 40, 40));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(5);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        addComponent(scrollPane, gridx, gridy);
        return list;
    }

    void updateComponentSize() {
        Dimension frameSize = this.view.getFrameSize();

        for (Component c : allComponents) {
            if (c instanceof JButton) {
                c.setPreferredSize(new Dimension(frameSize.width / 6, frameSize.height / 15));
                c.setFont(DEFAULT_FONT.deriveFont((float) frameSize.height / 30));
            } else if (c instanceof JLabel || c instanceof JComboBox) {
                c.setFont(DEFAULT_FONT.deriveFont((float) frameSize.height / 30));
            } else if (c instanceof JScrollPane) {
                c.setPreferredSize(new Dimension(frameSize.width / 5, frameSize.height / 5));
            }
        }

        this.revalidate();
    }
}