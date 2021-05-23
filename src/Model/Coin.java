package Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This class is in order to create Coins which can be used to buy upgrades.
 */
public class Coin extends GameObject{

    private int x;
    private int y;
    private int frameCounter = 0;
    private transient Graphics g;
    private transient ArrayList<BufferedImage> sprites;
    private transient BufferedImage displayImage;

    /**
     * Constructor for the Coin to create new instances of it
     * @param x x-coordinate where the coin spawns
     * @param y y-coordinate where the coin spawns
     */
    public Coin(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Keeps the coin updated
     */
    @Override
    public void tick() {
        frameCounter = (frameCounter + 1) % 110;

        if(frameCounter % 10 == 0)
            displayImage = Game.getInstance(23).getCoinSprites().get(frameCounter / 10);
    }

    /**
     * Renders the graphic of the coin
     * @param g graphics where the drawing should succeed.
     */
    @Override
    public void render(Graphics g) {

        if(Game.showHitbox){
            g.setColor(Color.ORANGE);
            g.drawOval(x,y,32,32);
        }
        g.drawImage(displayImage, x + 3, y + 3, null);
    }

    /**
     * Returns the bounds as a rectangle
     * @return bounds
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }

    /**
     * Returns the ID
     * @return ID.Coin
     */
    @Override
    public ID getId() {
        return ID.Coin;
    }
}
