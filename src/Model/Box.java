package Model;

import Controller.Handler1;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class represents every block the background is made of.
 */
public class Box extends GameObject {
    private transient final BufferedImage bufferedImage;

    private int hitboxW;
    private int hitboxH;
    private int xOffset;
    private int yOffset;

    /**
     * Constructor to create an instance.
     * @param x x-coordinate where the block should be displayed
     * @param y y-coordinate where the block should be displayed
     * @param id this object's id
     * @param bufferedImage the image which should represent the Box
     */
    public Box(int x, int y, ID id, BufferedImage bufferedImage, boolean smallHitboxTrue) {
        super(x, y,id);
        if(smallHitboxTrue){
            hitboxW = 58;
            hitboxH = 58;
            xOffset = 4;
            yOffset = 4;
        }
        else{
            hitboxH = 64;
            hitboxW = 64;
        }
        this.bufferedImage = bufferedImage;
    }

    /**
     * This method is to check for actions of the block.
     */

    @Override
    public void tick() {
    }

    /**
     * Renders the given image and draws the hitbox of the Box.
     * @param g graphics where it should be drawn on
     */
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

    /**
     * Returns the bounds of the generated block
     * @return the bounds as a 64x64 rectangle, with the position of the given block
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x + xOffset,y + yOffset, hitboxW, hitboxH);
    }


}
