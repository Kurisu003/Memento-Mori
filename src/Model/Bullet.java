package Model;

import java.awt.*;
import java.util.logging.Handler;
import Controller.*;

public class Bullet extends GameObject {

    private Controller.Handler1 handler;

    public Bullet(int x, int y, ID id, Handler1 handler, int mx, int my) {
        super(x, y, id);
        this.handler=handler;

        velX=(mx-x)/10;
        velY=(my-y)/10;
    }

    @Override
    public void tick() {
        x+=velX;
        y+=velY;

        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject temp = handler.objects.get(i);
            if (temp.getId()== ID.Block){
                if(getBounds().intersects(temp.getBounds())){
                    handler.removeObject(this);
                }

            }
        }



    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillOval(x,y,8,8);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,8,8);
    }
}
