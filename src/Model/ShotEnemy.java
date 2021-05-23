package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * This enemy is able to shoot and moves to the direction of Dante
 */
public class ShotEnemy extends GameObject{

    private final transient BufferedImageLoader loader;
    private final transient BufferedImage bulletImage;

    int tickCounter = 0;

    Random r= new Random();
    int choose=0;

    private int hp = 200;
    private final int speed;

    private transient BufferedImage displayedImage;
    private final transient BufferedImage left;
    private final transient BufferedImage right;

    /**
     * Constructor to create an instance.
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param id ID of this object
     * @param health amount of health
     * @param speed amount of speed
     */
    public ShotEnemy(int x, int y, ID id, int health, int speed) {
        super(x, y, id);
        loader = new BufferedImageLoader();
        bulletImage = loader.loadImage("../Assets/Bullet.png");
        left = Game.getInstance(15).getEnemySprites().get(70);
        right = Game.getInstance(16).getEnemySprites().get(71);
        this.hp += health;
        this.speed = speed;
    }

    /**
     * Updates in order to check changes and to react on them. For example if damage is done to the enemy to update the
     * remaining health or to display it if it's dead.
     */
    @Override
    public void tick() {
        x+=velX;
        y+=velY;

        if(Dante.getInstance().getX() <= x)
            displayedImage = left;
        else
            displayedImage = right;

        choose = r.nextInt(50);

        for(int i = 0; i < Handler1.getInstance().objects.size(); i++) {
            GameObject temp = Handler1.getInstance().objects.get(i);

            if(temp.getId() == ID.Block||temp.getId()==ID.Door|| temp.getId() == ID.Obstacle) {
                if(getBoundsBigger().intersects(temp.getBounds())) {
                    x += (velX) * -1;
                    y += (velY) * -1;
                    velX *= -1;
                    velY *= -1;
                } else if(choose == 0) {
                    velX = (r.nextInt(2 + 2) - 2) + speed;
                    velY = (r.nextInt(2 + 2) - 2) + speed;

                    if (velX!=0){
                        if (velY<0){
                            velY+=1;
                        }else {
                            velY-=1;
                        }
                    }

                    if (velY!=0){
                        if (velX>0){
                            velX-=1;
                        }else {
                            velX+=1;
                        }
                    }
                }
            }
            if(temp.id==ID.Dante){
                if (++tickCounter % (r.nextInt(50) + 50) == 0) {

                    Handler1.getInstance().addObject(new Bullet(x, y, ID.Bullet,
                            temp.getX() +(r.nextInt( 11+11) -11),
                            temp.getY()+(r.nextInt(11 +11) -11),
                            30, 1, bulletImage,30));
                }
            }
        }

        if(hp <= 0) {
            Random rand = new Random();
            if(rand.nextInt(100) <= 15)
                Handler1.getInstance().addObject(new Coin(x, y));
            Handler1.getInstance().removeObject(this);
        }
    }

    /**
     * Detects damage done to the enemy
     * @param action how much damage is done
     */
    public void doAction(int action){
        this.hp -= action;
    }

    /**
     * Renders the image and graphics of the enemy
     * @param g graphics where the drawing should succeed.
     */
    @Override
    public void render(Graphics g) {

        g.drawImage(displayedImage, x, y, null);

        if(Game.showHitbox) {
            g.setColor(Color.green);
            g.fillRect(x, y, 64, 64);
        }
    }

    /**
     * Getter of the enemy's health.
     * @return amount of health
     */
    public int getHp() {
        return hp;
    }

    /**
     * Getter of the enemy's speed.
     * @return amount of speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Returns the normal bounds of the enemy.
     * @return rectangle of the bounds
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,64,64);
    }

    /**
     * Returns the bigger bounds so the enemy does not stuck in the walls.
     * @return bigger bounds as a rectangle
     */
    public Rectangle getBoundsBigger() {
        return new Rectangle(x-16,y-16,64,64);
    }
}
