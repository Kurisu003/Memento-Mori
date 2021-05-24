package Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This class represents the obstacle which does damage to the player.
 */
public class DamageObstacle extends GameObject{

    private int x;
    private int y;

    private int hitboxH;
    private int hitboxW;
    private transient BufferedImage displayImage;
    private int frameCounter = 0;
    private boolean isSpike;

    /**
     * This is the constructor to make an instance of this class
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param isSpike boolean to check if the spikes are up to do damage to the player
     */
    public DamageObstacle(int x, int y, boolean isSpike){
        this.isSpike = isSpike;
        this.x = x;
        this.y = y;
        if(!isSpike){
            hitboxH = 45;
            hitboxW = 40;
        }
        else{
            hitboxH = 60;
            hitboxW = 60;
        }
    }

    /**
     * This method keeps the object updated
     */
    @Override
    public void tick() {
        if(isSpike) {
            frameCounter = (frameCounter + 1) % 300;
            displayImage = Game.getInstance(1).getDamageObstacleSprites().get((frameCounter / 10) + 8);
        }
        else {
            frameCounter = (frameCounter + 1) % 64;
            displayImage = Game.getInstance(2).getDamageObstacleSprites().get(frameCounter / 8);
        }
    }

    /**
     * Returns if the spikes are up
     * @return true -> spikes appear, false -> spikes disappear
     */
    public boolean getIsSpike(){
        return isSpike;
    }

    /**
     * Returns the frameCounter value
     * @return value of frameCounter
     */
    public int getFrameCounter(){
        return frameCounter;
    }

    /**
     * Renders the graphics
     * @param g graphics where the drawing should succeed.
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(displayImage, x, y, null);
        if(Game.showHitbox){
            if(hitboxH == 45)
                g.drawRect(x,y + 20,hitboxH,hitboxW);
            else
                g.drawRect(x + 2,y + 2,hitboxH,hitboxW);

        }
    }

    /**
     * Returns the bounds as a rectangle
     * @return bounds of the obstacle
     */
    @Override
    public Rectangle getBounds() {
        if(hitboxH == 45)
            return new Rectangle(x, y + 20, hitboxH, hitboxW);
        else
            return new Rectangle(x + 2, y + 2, hitboxH, hitboxW);

    }

    /**
     * Returns the ID of the object
     * @return ID.DamageObstacle
     */
    public ID getId(){
        return ID.DamageObstacle;
    }


    /**
     * Returns the x-coordination of the position
     * @return x-coordinate
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the position
     * @param x x-coordinate which should be set
     */
    @Override
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the position
     * @return y-coordinate
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the position
     * @param y y-coordinate which should be set
     */
    @Override
    public void setY(int y) {
        this.y = y;
    }
}
