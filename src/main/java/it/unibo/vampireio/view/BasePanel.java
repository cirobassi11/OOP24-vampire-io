package it.unibo.vampireio.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

abstract class BasePanel extends JPanel {
    protected GameViewImpl view;

    protected Dimension buttonSize;
    
    private final String backgroundPath = "/images/background.png";
    private Image backgroundImage;

    BasePanel(GameViewImpl view) {
        this.view = view;
        try {
            backgroundImage = ImageIO.read(getClass().getResource(backgroundPath));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        
        this.buttonSize = new Dimension(view.getScreenSize().width / 6, view.getScreenSize().height / 15);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    protected JButton createStyledButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setFont(new Font("Serif", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(new Color(50, 50, 50));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(200, 0, 0));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 50, 50));
            }
        });

        return button;
    }

    protected JComboBox<String> createStyledComboBox(Dimension size) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setPreferredSize(size);
        comboBox.setFont(new Font("Arial", Font.BOLD, 16));
        comboBox.setBackground(new Color(50, 50, 50));
        comboBox.setForeground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
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
}