package Model;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Dialog extends GameObject {
    private String levelName;
    private int[][] firstLevelSeed = {{3*64*17}, {3*64*9}};
    public Dialog(int x, int y, ID id, String levelName) {
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
            g.setColor(Color.black);

            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.drawString(levelName,10+3*64*27, 515+3*64*9);

        /*
        x = 3*64*17
        y = 3*64*9
         */
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(10+3*64*27, 515+3*64*9);
    }
}
