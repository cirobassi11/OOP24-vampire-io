package it.unibo.vampireio.view.manager;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.vampireio.view.api.GameView;

/**
 * ImageManager is responsible for loading and managing images used in the game.
 * It caches loaded images to avoid reloading them multiple times.
 * If an image fails to load, it returns a default empty image and notifies the view of the error.
 */
public class ImageManager {
    private static final String LOADING_ERROR = "An error occurred while loading image";

    private final GameView view;
    private final Map<String, Image> images = new HashMap<>();
    private final Set<String> failedImages = new HashSet<>();

    /**
     * Constructs an ImageManager with the specified GameViewImpl.
     * This manager will use the view to notify errors related to image loading.
     *
     * @param view the GameView instance to notify in case of errors
     */
    @SuppressFBWarnings(
        value = "EI2", 
        justification = "The GameView instance is intentionally shared and is used in a controlled way within ImageManager."
        )
    public ImageManager(final GameView view) {
        this.view = view;
    }

    /**
     * Retrieves an image by its key.
     * If the image is already loaded, it returns the cached image.
     * If the image has failed to load previously, it returns a default empty image.
     * If the image is not found, it attempts to load it from resources and caches it.
     *
     * @param key the key of the image to retrieve
     * @return the loaded image or a default empty image if loading fails
     */
    public Image getImage(final String key) {
        if (this.images.containsKey(key)) {
            return this.images.get(key);
        }

        if (this.failedImages.contains(key)) {
            return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        }

        try {
            final Image img = ImageIO.read(ImageManager.class.getResource("/images/" + key + ".png"));
            this.images.put(key, img);
            return img;
        } catch (IOException | IllegalArgumentException e) {
            this.failedImages.add(key);
            this.view.notifyError(LOADING_ERROR + key);
            return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        }
    }
}
