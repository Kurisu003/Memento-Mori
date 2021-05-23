package Model;

import Controller.Handler1;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This class represents every block the background is made of.
 */
public class Box extends GameObject {
    private transient BufferedImage displayImage;

    private int hitboxW;
    private int hitboxH;
    private int xOffset;
    private int yOffset;
    private boolean isPortal;
    private int frameCounter;
    private ArrayList<BufferedImage> portalSprites;

    /**
     * Constructor to create an instance.
     * @param x x-coordinate where the block should be displayed
     * @param y y-coordinate where the block should be displayed
     * @param id this object's id
     * @param bufferedImage the image which should represent the Box
     */
    public Box(int x, int y, ID id, BufferedImage bufferedImage, boolean smallHitboxTrue, boolean isPortal) {
        super(x, y,id);
        this.isPortal = isPortal;
        if(isPortal)
            portalSprites = Game.getInstance(17).getPortalSprites();
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
        if(bufferedImage != null)
        this.displayImage = bufferedImage;
    }

    /**
     * This method is to check for actions of the block.
     */

    @Override
    public void tick() {
        if(isPortal) {
            frameCounter = (frameCounter + 1) % 80;
            if (frameCounter % 10 == 0) {
                displayImage = portalSprites.get(frameCounter / 10);
            }
        }
    }

    /**
     * Renders the given image and draws the hitbox of the Box.
     * @param g graphics where it should be drawn on
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(displayImage, x, y, null);

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
        if(!isPortal)
            return new Rectangle(x + xOffset,y + yOffset, hitboxW, hitboxH);
        else
            return new Rectangle(x + xOffset,y + yOffset, 64, 35);
    }


}
