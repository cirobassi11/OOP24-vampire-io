package it.unibo.vampireio.view;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

class ImageManager {
    private final Map<String, Image> images = new HashMap<>();

    Image getImage(String key) {
        if (this.images.containsKey(key)) {
            return this.images.get(key);
        }
        try {
            Image img = ImageIO.read(ImageManager.class.getResource("/images/" + key + ".png"));
            this.images.put(key, img);
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}