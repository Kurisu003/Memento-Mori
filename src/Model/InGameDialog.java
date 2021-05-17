package Model;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * In this class the little dialogues within the game are displayed.
 */
public class InGameDialog extends GameObject implements Serializable {
    private final String levelName;
    //Alpha composite to set visibility (0 = invisible)
    private final transient Composite fullVisible = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
    private final transient Composite halfVisible = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f);
    private final transient Composite invisible = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0);

    //Array to check if W, A, S and D was pressed for the tutorial
    private static boolean[] pressedButtons = {false, false, false, false};
    private static int boolCounter;



    public InGameDialog(int x, int y, ID id, String levelName) {
        super(x, y,id);
        this.levelName = levelName;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

        //Only for the first level (Limbo)
        //TODO change "Heresy" to Limbo
        if(this.levelName.equals("Heresy"))
            tutorialLimbo(g);

        else {
            Graphics2D g2 = (Graphics2D) g;

            //Font
            g2.setComposite(fullVisible);
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2.drawString(levelName, 70 + 3 * 64 * 17, 550 + 3 * 64 * 9);
        }

        /*
        x = 3*64*17
        y = 3*64*9
         */
    }

    public void tutorialLimbo(Graphics g){
        //Tutorial rectangle
        Graphics2D g2 = (Graphics2D) g;

        g2.setComposite(halfVisible);
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(250+3*64*17+3*64, 400+3*64*9, 200, 80);

        //Font
        g2.setComposite(fullVisible);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2.drawString("MOVE WITH",290+3*64*17+3*64, 435+3*64*9);
        g2.drawString("W, A, S, D", 300+3*64*17+3*64, 460+3*64*9);



    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(5+3*64*17, 520+3*64*9);
    }

    /*
    Getter + setter
     */

    public static boolean[] getPressedButtons() {
        return pressedButtons;
    }

    public static void setPressedButtons(int index) {
        System.out.println(index + " set true");
        pressedButtons[index] = true;
        for (boolean b: pressedButtons
             ) {
            if(b) {
                boolCounter++;
                System.out.println("BOOLC = " + boolCounter);
            }
            else{
                boolCounter = 0;
                break;
            }
        }
        if(boolCounter == 3)
            System.out.println("OK");
    }
}
