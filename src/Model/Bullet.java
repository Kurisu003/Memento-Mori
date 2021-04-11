package Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Handler;
import Controller.*;

public class Bullet extends GameObject {

    private Controller.Handler1 handler;
    private BufferedImage bufferedShotImage = null;
    private int timeAlive = 0;
    private int range = 30;
    private int damage = 10;

    public Bullet(int x, int y, ID id, Handler1 handler, int mx, int my, int range, int damage, BufferedImage shotType) {
        super(x, y, id);
        this.handler = handler;
        this.range = range;
        this.bufferedShotImage = shotType;
        this.damage = damage;

        velX=(mx-x)/10;
        velY=(my-y)/10;
    }

    @Override
    public void tick() {
        x+=velX;
        y+=velY;

        if(++timeAlive > range){
            handler.removeObject(this);
        }

        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject temp = handler.objects.get(i);
            if (temp.getId()== ID.Block && getBounds().intersects(temp.getBounds())){
                handler.removeObject(this);
            }
            if (temp.getId()== ID.Enemy && getBounds().intersects(temp.getBounds())){
                handler.removeObject(this);
                temp.doAction(damage);
            }
        }
    }

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.CYAN);
//        g.fillOval(x,y,20,20);
        g.drawImage(bufferedShotImage, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,8,8);
    }
}
