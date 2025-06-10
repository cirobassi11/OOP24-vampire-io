package it.unibo.vampireio.view;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * AbstractBasePanel is an abstract class that serves as a base for creating
 * custom panels in the VampireIO game.
 * It provides methods to add buttons, labels, combo boxes, and scrollable lists,
 * along with common styling and layout configurations.
 */
abstract class AbstractBasePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    // Color constants
    private static final Color BUTTON_BACKGROUND = new Color(50, 50, 50);
    private static final Color BUTTON_HOVER = new Color(200, 0, 0);
    private static final Color BUTTON_BORDER = new Color(255, 215, 0);
    private static final Color COMBOBOX_BACKGROUND = new Color(50, 50, 50);
    private static final Color COMBOBOX_BORDER = new Color(200, 200, 200);
    private static final Color LIST_BACKGROUND = new Color(40, 40, 40);
    private static final Color LIST_BORDER_COLOR = Color.GRAY;

    // Font constants
    private static final Font DEFAULT_FONT = new Font("Serif", Font.BOLD, 24);
    private static final float SMALL_FONT_SCALE = 16f;
    private static final int FONT_SIZE_RATIO = 30;

    // Layout constants
    private static final int COMPONENT_INSET_TOP = 10;
    private static final int COMPONENT_INSET_LEFT = 10;
    private static final int COMPONENT_INSET_BOTTOM = 20;
    private static final int COMPONENT_INSET_RIGHT = 10;
    private static final int BUTTON_BORDER_THICKNESS = 3;
    private static final int COMBOBOX_BORDER_THICKNESS = 2;
    private static final int LIST_BORDER_THICKNESS = 2;
    private static final int LIST_VISIBLE_ROWS = 5;

    // Resize ratios
    private static final int BUTTON_WIDTH_RATIO = 6;
    private static final int BUTTON_HEIGHT_RATIO = 15;
    private static final int SCROLL_WIDTH_RATIO = 5;
    private static final int SCROLL_HEIGHT_RATIO = 5;

    private final FrameManager frameManager;
    private final List<Component> allComponents = new LinkedList<>();

    /**
     * Constructor for AbstractBasePanel.
     *
     * @param frameManager the FrameManager instance to manage the frame's background
     */
    AbstractBasePanel(final FrameManager frameManager) {
        this.frameManager = frameManager;
    }

    {
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        this.updateComponentSize();
        g.drawImage(this.frameManager.getBackgroundImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    private void addComponent(final Component component, final int gridx, final int gridy) {
        final GridBagConstraints gbc = new GridBagConstraints();
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

    protected final JButton addButton(final String text, final int gridx, final int gridy) {
        final JButton button = new JButton(text);
        button.setFont(DEFAULT_FONT);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(BUTTON_BORDER, BUTTON_BORDER_THICKNESS));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(BUTTON_BACKGROUND);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent evt) {
                button.setBackground(BUTTON_HOVER);
            }

            @Override
            public void mouseExited(final MouseEvent evt) {
                button.setBackground(BUTTON_BACKGROUND);
            }
        });
        this.addComponent(button, gridx, gridy);
        return button;
    }

    protected final JLabel addLabel(final String text, final int gridx, final int gridy) {
        final JLabel label = new JLabel(text);
        label.setFont(DEFAULT_FONT);
        label.setForeground(Color.WHITE);

        this.addComponent(label, gridx, gridy);
        return label;
    }

    protected final JComboBox<String> addComboBox(final int gridx, final int gridy) {
        final JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(DEFAULT_FONT.deriveFont(SMALL_FONT_SCALE));
        comboBox.setBackground(COMBOBOX_BACKGROUND);
        comboBox.setForeground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(COMBOBOX_BORDER, COMBOBOX_BORDER_THICKNESS));
        this.addComponent(comboBox, gridx, gridy);
        return comboBox;
    }

    protected final JList<String> addScrollableList(final List<String> items, final int gridx, final int gridy) {
        final DefaultListModel<String> model = new DefaultListModel<>();
        items.forEach(model::addElement);

        final JList<String> list = new JList<>(model);
        list.setFont(DEFAULT_FONT.deriveFont(SMALL_FONT_SCALE));
        list.setForeground(Color.WHITE);
        list.setBackground(LIST_BACKGROUND);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(LIST_VISIBLE_ROWS);

        final JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createLineBorder(LIST_BORDER_COLOR, LIST_BORDER_THICKNESS));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        this.addComponent(scrollPane, gridx, gridy);
        return list;
    }

    void updateComponentSize() {
        final Dimension frameSize = this.frameManager.getFrameSize();

        for (final Component c : allComponents) {
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

    protected final JLabel addImage(final ImageIcon image, final int gridx, final int gridy) {
        final JLabel label = new JLabel(image);
        this.addComponent(label, gridx, gridy);
        return label;
    }
}
