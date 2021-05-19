package Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import Controller.*;

public class Bullet extends GameObject {

    private transient final BufferedImage bufferedShotImage;
    private int timeAlive = 0;
    private final int range;
    private final int damage;

    Random r= new Random();

    public Bullet(int x, int y, ID id, int mx, int my, int range, int damage, BufferedImage shotType) {
        super(x, y, id);
        this.range = range;
        this.bufferedShotImage = shotType;
        this.damage = damage;

        velX= (mx-x)/10;
        velY= (my-y)/10;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (++timeAlive > range) {
            Handler1.getInstance().removeObject(this);
        }

        for (int i = 0; i < Handler1.getInstance().objects.size(); i++) {
            GameObject temp = Handler1.getInstance().objects.get(i);
            if (getBounds().intersects(temp.getBounds())) {
                if (temp.getId() == ID.Enemy || temp.getId() == ID.SmartEnemy || temp.getId() == ID.Miniboss || temp.getId() == ID.ShotEnemy) {
                    Handler1.getInstance().removeObject(this);
                    temp.doAction(damage);
                }
                if (temp.getId() == ID.Block || temp.getId() == ID.Door)
                    Handler1.getInstance().removeObject(this);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if(Game.showHitbox) {
            g.setColor(Color.CYAN);
            g.drawOval(x,y,20,20);
        }
        g.drawImage(bufferedShotImage, x+2, y+2, null);
        g.dispose();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,8,8);
    }
}
