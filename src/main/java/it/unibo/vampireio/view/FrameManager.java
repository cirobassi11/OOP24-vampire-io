package it.unibo.vampireio.view;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Taskbar;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameManager {
    private static final double ASPECT_RATIO = 16.0 / 9.0;
    private static final Dimension DEFAULT_RESOLUTION = new Dimension(1280, 720);
    private static final Dimension MIN_RESOLUTION = new Dimension(640, 360);
    private static final String ICON_PATH = "/images/icon.png";

    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private Dimension currentFrameSize;
    private Image backgroundImage;

    public FrameManager(final String title, Image backgroundImage) {
        this.frame = new JFrame(title);
        this.backgroundImage = backgroundImage;
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(this.cardLayout);
        this.initFrame();
    }

    private void initFrame() {
        this.setIcon(ICON_PATH);
        this.setResolution(DEFAULT_RESOLUTION);

        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.add(cardPanel);
        this.frame.setVisible(true);
        this.frame.setFocusable(true);
        this.frame.requestFocus();

        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = Math.max(frame.getWidth(), MIN_RESOLUTION.width);
                int height = Math.max((int) (width / ASPECT_RATIO), MIN_RESOLUTION.height);
                setResolution(new Dimension(width, height));
                for (Component comp : cardPanel.getComponents()) {
                    if (comp instanceof AbstractBasePanel basePanel) {
                        basePanel.updateComponentSize();
                    }
                }
            }
        });
    }

    public void addPanels(Map<String, JPanel> panels) {
        panels.forEach(this.cardPanel::add);
    }

    private void setResolution(Dimension resolution) {
        frame.setSize(resolution);
        this.currentFrameSize = resolution;
    }

    private void setIcon(String path) {
        final Image image = new ImageIcon(getClass().getResource(path)).getImage();
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                frame.setIconImage(image);
            } else {
                Taskbar.getTaskbar().setIconImage(image);
            }
        } catch (UnsupportedOperationException e) { }
    }

    public void showScreen(String name) {
        this.cardLayout.show(cardPanel, name);
    }

    public void showError(String message) {
        javax.swing.JOptionPane.showMessageDialog(this.frame, message, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    public Dimension getFrameSize() {
        return this.currentFrameSize;
    }

    public JPanel getCardPanel() {
        return this.cardPanel;
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public Image getBackgroundImage() {
        return this.backgroundImage;
    }
}
