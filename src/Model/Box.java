package Model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Box extends GameObject {
    private final BufferedImage bufferedImage;
    public Box(int x, int y, ID id, BufferedImage bufferedImage) {
        super(x, y,id);
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(bufferedImage, x, y, null);

//        To draw hitboxes
        if(Game.showHitbox) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.blue);
            g2.draw(getBounds());
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,64,64);
    }
}
