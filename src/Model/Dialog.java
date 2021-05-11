package Model;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Dialog extends GameObject {
    private String level;
    public Dialog(int x, int y, ID id, String level) {
        super(x, y,id);
        this.level = level;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        if(this.level.equals("Limbo")){
            /*g.setColor(Color.WHITE);
            g.fillRect((int)Game.getCamera().getX()+35, (int)Game.getCamera().getY()+500, 200, 50);
            g.setColor(Color.black);

             */
            g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
            g.drawString("Limbo",(int)Game.getInstance().getCamera().getX()+10*35+30,
                        (int)Game.getInstance().getCamera().getY()+300);
        }
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
