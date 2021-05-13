package Model;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * In this class the little dialogues within the game are displayed.
 */
public class InGameDialog extends GameObject {
    private final String levelName;
    public InGameDialog(int x, int y, ID id, String levelName) {
        super(x, y,id);
        this.levelName = levelName;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(5+3*64*17, 520+3*64*9, 200, 50);
            g.setColor(Color.BLACK);

            g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
            g.drawString(levelName,70+3*64*17, 550+3*64*9);


        /*
        x = 3*64*17
        y = 3*64*9
         */
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(5+3*64*17, 520+3*64*9);
    }
}
