package Model;

import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Coin extends GameObject{

    private int x;
    private int y;
    private int framCounter = 0;
    private transient Graphics g;
    private transient ArrayList<BufferedImage> sprites;
    private transient BufferedImage displayImage;

    public Coin(int x, int y){
        this.x = x;
        this.y = y;
        g = Game.getInstance().getG();
        sprites = Game.getInstance().getCoinSprites();
    }

    @Override
    public void tick() {
        framCounter = (framCounter + 1) % 110;

        if(framCounter % 10 == 0)
            displayImage = sprites.get(framCounter / 10);
    }

    @Override
    public void render(Graphics g) {

        if(Game.showHitbox){
            g.setColor(Color.ORANGE);
            g.drawOval(x,y,32,32);
        }


        g.drawImage(displayImage, x + 5, y + 5, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }

    @Override
    public ID getId() {
        return ID.Coin;
    }
}
