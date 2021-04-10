package Model;

import Model.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Box extends GameObject {
    private BufferedImage bufferedImage;
    public Box(int x, int y, ID id, BufferedImage bufferedImage) {
        super(x, y,id);
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void tick() {
    }

    @Override
//    public void render(Graphics g) {
//        g.setColor(Color.BLACK);
//        g.fillRect(x,y,32,32);
//
//    }
    public void render(Graphics g) {
        g.drawImage(bufferedImage, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,64,64);
    }
}
