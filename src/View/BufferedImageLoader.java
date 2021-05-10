package View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class BufferedImageLoader {

    private transient BufferedImage image;

    public BufferedImage loadImage(String path){
        try {
            image= ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
