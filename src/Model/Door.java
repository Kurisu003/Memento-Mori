package Model;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class represents the doors in the game. They can be set locked or unlocked and they are meant to let
 * the player pass from room to room.
 */
public class Door extends GameObject {

    private final transient BufferedImage openDoorImage;
    private final transient BufferedImage closedDoorImage;
    private transient BufferedImage displayedImage;
    private int firstTimeEntered = 1;
    private boolean isLocked = false;

    /**
     * This is the constructor for the class Door
     * @param x x-coordinate of the door's position
     * @param y y-coordinate of the door's position
     * @param id ID of the object
     * @param openDoorImage image to display the open door
     * @param closedDoorImage image to display the closed door
     */
    public Door(int x, int y, ID id, BufferedImage openDoorImage, BufferedImage closedDoorImage) {
        super(x, y, id);
        this.openDoorImage = openDoorImage;
        this.closedDoorImage = closedDoorImage;
        this.displayedImage = openDoorImage;
    }

    /**
     * Is checked for a certain period of time in order to detect changes.
     */
    @Override
    public void tick() {
    }

    /**
     * This method renders the given graphics.
     * @param g graphics to be rendered
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(displayedImage, x, y, null);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.red);
//        To draw hitboxes
        if(Game.showHitbox && !isLocked)
            g2.draw(getBounds());
        else if(Game.showHitbox)
            g2.draw(getSmallerBounds());
    }

    /**
     * This method checks if it was the first time entered
     * @param action 1 if true, 0 if false
     */
    @Override
    public void doAction(int action) {
        int temp = firstTimeEntered;
        firstTimeEntered = action;
    }

    /**
     * This method is called when the doors should be set locked. The locked-door-image is saved to the attribute
     * displayImage which will be rendered afterwards. The attribute isLocked is now set to true.
     */
    public void lockDoor(){
        displayedImage = closedDoorImage;
        isLocked = true;
    }
    /**
     * This method is called when the doors should be set unlocked. The open-door-image is saved to the attribute
     * displayImage which will be rendered afterwards. The attribute isLocked is now set to false.
     */
    public void unlockDoor(){
        displayedImage = openDoorImage;
        isLocked = false;
    }

    /**
     * This method is the getter-method of the attribute isLocked which indicates whether the doors are locked
     * or not.
     * @return true -> if doors are locked, false -> if doors are unlocked
     */
    public boolean isLocked(){
        return isLocked;
    }

    /**
     * Returns the bounds of the door when it is unlocked. Then they're bigger so the player can get easier through it.
     * @return bounds in form of a rectangle
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x - 20,y - 20,104, 104);
    }

    /**
     * Returns the bounds of the door when it is locked. The bounds are smaller because the player does not have to
     * interact with the door.
     * @return a 64x64 rectangle at the position of the door
     */
    public Rectangle getSmallerBounds() {
        return new Rectangle(x,y,64, 64);
    }

}
