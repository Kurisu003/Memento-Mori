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
//        To draw hitboxes
//        Graphics2D g2 = (Graphics2D)g;
//        g2.setColor(Color.red);
//        g2.draw(getBounds());
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x + 7,y + 7,50, 50);
    }
}
