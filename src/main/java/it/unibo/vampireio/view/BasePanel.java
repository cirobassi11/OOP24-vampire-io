package it.unibo.vampireio.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

abstract class BasePanel extends JPanel {

    // Color constants
    protected static final Color BUTTON_BACKGROUND = new Color(50, 50, 50);
    protected static final Color BUTTON_HOVER = new Color(200, 0, 0);
    protected static final Color BUTTON_BORDER = new Color(255, 215, 0);
    protected static final Color COMBOBOX_BACKGROUND = new Color(50, 50, 50);
    protected static final Color COMBOBOX_BORDER = new Color(200, 200, 200);
    protected static final Color LIST_BACKGROUND = new Color(40, 40, 40);
    protected static final Color LIST_BORDER_COLOR = Color.GRAY;

    // Font constants
    protected static final Font DEFAULT_FONT = new Font("Serif", Font.BOLD, 24);
    protected static final float SMALL_FONT_SCALE = 16f;
    protected static final int FONT_SIZE_RATIO = 30;

    // Layout constants
    protected static final int COMPONENT_INSET_TOP = 10;
    protected static final int COMPONENT_INSET_LEFT = 10;
    protected static final int COMPONENT_INSET_BOTTOM = 20;
    protected static final int COMPONENT_INSET_RIGHT = 10;
    protected static final int BUTTON_BORDER_THICKNESS = 3;
    protected static final int COMBOBOX_BORDER_THICKNESS = 2;
    protected static final int LIST_BORDER_THICKNESS = 2;
    protected static final int LIST_VISIBLE_ROWS = 5;

    // Resize ratios
    protected static final int BUTTON_WIDTH_RATIO = 6;
    protected static final int BUTTON_HEIGHT_RATIO = 15;
    protected static final int SCROLL_WIDTH_RATIO = 5;
    protected static final int SCROLL_HEIGHT_RATIO = 5;

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
        gbc.insets = new Insets(
                COMPONENT_INSET_TOP, COMPONENT_INSET_LEFT,
                COMPONENT_INSET_BOTTOM, COMPONENT_INSET_RIGHT
        );
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        this.add(component, gbc);
        this.allComponents.add(component);
    }

    protected JButton addButton(String text, int gridx, int gridy) {
        JButton button = new JButton(text);
        button.setFont(DEFAULT_FONT);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(BUTTON_BORDER, BUTTON_BORDER_THICKNESS));
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
        this.addComponent(button, gridx, gridy);
        return button;
    }

    protected JLabel addLabel(String text, int gridx, int gridy) {
        JLabel label = new JLabel(text);
        label.setFont(DEFAULT_FONT);
        label.setForeground(Color.WHITE);

        this.addComponent(label, gridx, gridy);
        return label;
    }

    protected JComboBox<String> addComboBox(int gridx, int gridy) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(DEFAULT_FONT.deriveFont(SMALL_FONT_SCALE));
        comboBox.setBackground(COMBOBOX_BACKGROUND);
        comboBox.setForeground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(COMBOBOX_BORDER, COMBOBOX_BORDER_THICKNESS));
        this.addComponent(comboBox, gridx, gridy);
        return comboBox;
    }

    protected JList<String> addScrollableList(List<String> items, int gridx, int gridy) {
        DefaultListModel<String> model = new DefaultListModel<>();
        items.forEach(model::addElement);

        JList<String> list = new JList<>(model);
        list.setFont(DEFAULT_FONT.deriveFont(SMALL_FONT_SCALE));
        list.setForeground(Color.WHITE);
        list.setBackground(LIST_BACKGROUND);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(LIST_VISIBLE_ROWS);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createLineBorder(LIST_BORDER_COLOR, LIST_BORDER_THICKNESS));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        this.addComponent(scrollPane, gridx, gridy);
        return list;
    }

    void updateComponentSize() {
        Dimension frameSize = this.view.getFrameSize();

        for (Component c : allComponents) {
            if (c instanceof JButton) {
                c.setPreferredSize(new Dimension(
                        frameSize.width / BUTTON_WIDTH_RATIO,
                        frameSize.height / BUTTON_HEIGHT_RATIO
                ));
                c.setFont(DEFAULT_FONT.deriveFont((float) frameSize.height / FONT_SIZE_RATIO));
            } else if (c instanceof JLabel || c instanceof JComboBox) {
                c.setFont(DEFAULT_FONT.deriveFont((float) frameSize.height / FONT_SIZE_RATIO));
            } else if (c instanceof JScrollPane) {
                c.setPreferredSize(new Dimension(
                        frameSize.width / SCROLL_WIDTH_RATIO,
                        frameSize.height / SCROLL_HEIGHT_RATIO
                ));
            }
        }

        this.revalidate();
    }
}