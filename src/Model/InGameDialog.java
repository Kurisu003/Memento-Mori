package Model;

import java.awt.*;
import java.io.Serializable;


/**
 * In this class the little dialogues within the game are displayed.
 */
public class InGameDialog extends GameObject implements Serializable {
    private final String levelName;
    //Alpha composite to set visibility (0 = invisible)
    private final transient Composite fullVisible = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
    private final transient Composite halfVisible = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f);

    //Array to check if W, A, S and D was pressed for the tutorial
    private static boolean[] pressedButtons = {false, false, false, false};
    private static int boolCounter;

    /**
     * Constructor for the dialog
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param id ID of this object
     * @param levelName the name of the level to be displayed
     */
    public InGameDialog(int x, int y, ID id, String levelName) {
        super(x, y,id);
        this.levelName = levelName;
    }

    /**
     * Checks for updates and does changes if necessary.
     */
    @Override
    public void tick() {

    }

    /**
     * Renders the dialogues.
     * @param g graphics where the drawing should succeed.
     */
    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Font
        g2.setComposite(halfVisible);
        g2.setColor(Color.red);
        g2.fillRect(2+3*64*17, 530+3*64*9, levelName.length()*25, 40);
        g2.setComposite(fullVisible);
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g2.drawString(levelName, 5 + 3 * 64 * 17, 565 + 3 * 64 * 9);

        /*
        x = 3*64*17
        y = 3*64*9
         */
    }

    /**
     * Get the bounds of the dialog.
     * @return the size of the dialog as a rectangle
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(5+3*64*17, 520+3*64*9);
    }

}
