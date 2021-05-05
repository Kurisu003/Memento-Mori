package Model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Door extends GameObject {

    private BufferedImage openDoorImage;
    private BufferedImage closedDoorImage;
    private BufferedImage displayedImage;
    private int firstTimeEntered = 1;
    private boolean isLocked = false;

    public Door(int x, int y, ID id, BufferedImage openDoorImage, BufferedImage closedDoorImage) {
        super(x, y, id);
        this.openDoorImage = openDoorImage;
        this.closedDoorImage = closedDoorImage;
        this.displayedImage = openDoorImage;
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(displayedImage, x, y, null);
//        To draw hitboxes
//        Graphics2D g2 = (Graphics2D)g;
//        g2.setColor(Color.red);
//        g2.draw(getBounds());
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x - 20,y - 20,104, 104);
    }

    public void lockDoor(){
        displayedImage = closedDoorImage;
        isLocked = true;
    }

    public void unlockDoor(){
        displayedImage = openDoorImage;
        isLocked = false;
    }

    public boolean isLocked(){
        return isLocked;
    }

    @Override
    public int doAction(int action) {
        int temp = firstTimeEntered;
        firstTimeEntered = action;
        return temp;
    }
}
