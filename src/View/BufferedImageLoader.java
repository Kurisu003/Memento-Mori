package View;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * This class loads all the images
 */
public class BufferedImageLoader {

    private transient BufferedImage image;

    /**
     * This method loads the desired image.
     * @param path the path were the desired image is saved in
     * @return the loaded image as BufferedImage
     */
    public BufferedImage loadImage(String path){
        try {
            image= ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
