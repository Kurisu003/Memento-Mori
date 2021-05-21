package Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import Controller.*;

/**
 * This class is for the bullets which Dante's gun shoots. It renders the graphics of the bullet and sets
 * the collision with enemies.
 */
public class Bullet extends GameObject {

    private transient final BufferedImage bufferedShotImage;
    private int timeAlive = 0;
    private final int range;
    private final int damage;

    Random r= new Random();

    /**
     * This is the constructor to create an instance of Bullet
     * @param x x-coordinate where the bullet should start
     * @param y y-coordinate where the bullet should start
     * @param id ID for this class
     * @param mx x-coordinate of the bullet's last position
     * @param my y-coordinate of the bullet's last position
     * @param range maximal distance the bullet can go to
     * @param damage how much damage the bullet does to enemies
     * @param shotType what image should be displayed for this bullet
     */
    public Bullet(int x, int y, ID id, int mx, int my, int range, int damage, BufferedImage shotType) {
        super(x, y, id);
        this.range = range;
        this.bufferedShotImage = shotType;
        this.damage = damage;

        velX= (mx-x)/10;
        velY= (my-y)/10;
    }

    /**
     * Checks for every period of time if the bullet collides with an object. If it intersects with the hitbox of an
     * enemy the bullet will disappear and the enemy will receive damage. If the bullet collides with a door or a block
     * the bullet will just disappear. The bullet will also disappear if it has reached its range.
     */
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

    /**
     * Renders the hitbox and the bullet image.
     * @param g graphics where everything should be drawn on
     */
    @Override
    public void render(Graphics g) {
        if(Game.showHitbox) {
            g.setColor(Color.CYAN);
            g.drawOval(x,y,20,20);
        }
        g.drawImage(bufferedShotImage, x+2, y+2, null);
    }

    /**
     * Returns the bounds of the bullet object
     * @return bounds in form of a rectangle
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,8,8);
    }
}
