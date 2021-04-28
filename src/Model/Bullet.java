package Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import Controller.*;

public class Bullet extends GameObject {

    private final Controller.Handler1 handler;
    private final BufferedImage bufferedShotImage;
    private int timeAlive = 0;
    private final int range;
    private final int damage;

    Random r= new Random();

    public Bullet(int x, int y, ID id, Handler1 handler, int mx, int my, int range, int damage, BufferedImage shotType) {
        super(x, y, id);
        this.handler = handler;
        this.range = range;
        this.bufferedShotImage = shotType;
        this.damage = damage;

        velX= (mx-x)/10;
        velY= (my-y)/10;
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
            if(getBounds().intersects(temp.getBounds())){
                if(temp.getId() == ID.Enemy || temp.getId()==ID.SmartEnemy)
                    temp.doAction(damage);
                handler.removeObject(this);
            }
        }
    }

    @Override
    public void render(Graphics g) {
       g.setColor(Color.CYAN);
       g.fillOval(x,y,20,20);
        //g.drawImage(bufferedShotImage, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,8,8);
    }
}
