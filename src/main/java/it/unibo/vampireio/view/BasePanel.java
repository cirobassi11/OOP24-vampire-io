package it.unibo.vampireio.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

abstract class BasePanel extends JPanel {
    protected GameViewImpl view;

    protected Dimension buttonSize;
    
    private final String backgroundPath = "/images/background.jpg";
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
}