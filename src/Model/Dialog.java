package Model;

import java.awt.*;

public class Dialog extends GameObject {
    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect((int)Game.getCamera().getX()+10*35+10, (int)Game.getCamera().getY()+30, 200, 50);
        g.setColor(Color.black);
        g.drawString("..",(int)Game.getCamera().getX()+10*35+20, (int)Game.getCamera().getY()+40);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
