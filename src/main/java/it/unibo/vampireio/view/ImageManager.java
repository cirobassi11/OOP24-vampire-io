package it.unibo.vampireio.view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

class ImageManager {
    private GameViewImpl view;
    private final Map<String, Image> images = new HashMap<>();
    private final Set<String> failedImages = new HashSet<>();

    private final String loadingError = "An error occurred while loading image";

    public ImageManager(GameViewImpl view) {
        this.view = view;
    }

    Image getImage(String key) {
        if (this.images.containsKey(key)) {
            return this.images.get(key);
        }

        if (this.failedImages.contains(key)) {
            return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        }

        try {
            Image img = ImageIO.read(ImageManager.class.getResource("/images/" + key + ".png"));
            this.images.put(key, img);
            return img;
        } catch (IOException | IllegalArgumentException e) {
            this.failedImages.add(key);
            this.view.showError(loadingError + key);
            return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        }
    }
}