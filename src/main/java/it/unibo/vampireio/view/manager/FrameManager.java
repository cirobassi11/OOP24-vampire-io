package it.unibo.vampireio.view.manager;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import it.unibo.vampireio.view.panels.AbstractBasePanel;

/**
 * FrameManager is responsible for managing the main application frame,
 * including setting up the layout, adding panels, and handling resizing.
 */
public final class FrameManager {
    private static final double ASPECT_RATIO = 16.0 / 9.0;
    private static final Dimension DEFAULT_RESOLUTION = new Dimension(1280, 720);
    private static final Dimension MIN_RESOLUTION = new Dimension(640, 360);

    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final Image backgroundImage;

    /**
     * Constructs a FrameManager with the specified title and background image.
     *
     * @param title           the title of the frame
     * @param backgroundImage the background image for the frame
     */
    public FrameManager(final String title, final Image backgroundImage) {
        this.frame = new JFrame(title);
        this.backgroundImage = new ImageIcon(backgroundImage).getImage();
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(this.cardLayout);
        this.initFrame();
    }

    private void initFrame() {
        this.frame.setSize(DEFAULT_RESOLUTION);

        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.add(cardPanel);
        this.frame.setVisible(true);
        this.frame.setFocusable(true);
        this.frame.requestFocus();

        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                final int width = Math.max(frame.getWidth(), MIN_RESOLUTION.width);
                final int height = Math.max((int) (width / ASPECT_RATIO), MIN_RESOLUTION.height);
                frame.setSize(new Dimension(width, height));
                for (final Component comp : cardPanel.getComponents()) {
                    if (comp instanceof AbstractBasePanel basePanel) {
                        basePanel.updateComponentSize();
                    }
                }
            }
        });
    }

    /**
     * Adds panels to the card layout.
     *
     * @param panels a map of panel names to JPanel instances
     */
    public void addPanels(final Map<String, JPanel> panels) {
        panels.forEach(this.cardPanel::add);
    }

    /**
     * Shows the specified screen by its name.
     *
     * @param name the name of the screen to show
     */
    public void showScreen(final String name) {
        this.cardLayout.show(cardPanel, name);
    }

    /**
     * Shows an error message dialog.
     *
     * @param message the error message to display
     */
    public void showError(final String message) {
        javax.swing.JOptionPane.showMessageDialog(this.frame, message, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Returns the current size of the frame.
     *
     * @return the current frame size
     */
    public Dimension getFrameSize() {
        return this.frame.getSize();
    }

    /**
     * Returns the background image of the frame.
     *
     * @return the background image
     */
    public Image getBackgroundImage() {
        return new ImageIcon(this.backgroundImage).getImage();
    }
}
