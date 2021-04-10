package Model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Door extends GameObject {

    private BufferedImage bufferedImage;
    public Door(int x, int y, ID id, BufferedImage bufferedImage) {
        super(x, y, id);
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(bufferedImage, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
