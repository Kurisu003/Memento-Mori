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

    transient BufferedImage image;

    int tickCounter = 0;
    int animationCounter = 0;

    Random r= new Random();
    Random r2= new Random();
    int choose=0;

    private int hp = 200;
    private final int speed;

    transient BufferedImage displayedImage;
    transient ArrayList<BufferedImage> enemyAnimation = new ArrayList<>();

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
        this.hp += health;
        this.speed = speed;

        BufferedImageLoader loader = new BufferedImageLoader();
        enemyAnimation.add(loader.loadImage("../Character/FrontAnimation1&3.png"));
        enemyAnimation.add(loader.loadImage("../Character/FrontAnimation2.png"));
        enemyAnimation.add(loader.loadImage("../Character/FrontAnimation1&3.png"));
        enemyAnimation.add(loader.loadImage("../Character/FrontAnimation4.png"));
        //this.image=image;
    }

    /**
     * Updates in order to check changes and to react on them. For example if damage is done to the enemy to update the
     * remaining health or to display it if it's dead.
     */
    @Override
    public void tick() {
        x+=velX;
        y+=velY;

        if (++tickCounter % 5 == 0){
            displayedImage = enemyAnimation.get(animationCounter);
            animationCounter = (animationCounter + 1) % 4;
        }


        choose = r.nextInt(50);

        for(int i = 0; i < Handler1.getInstance().objects.size(); i++) {
            GameObject temp = Handler1.getInstance().objects.get(i);

            if(temp.getId() == ID.Block||temp.getId()==ID.Door) {
                if(getBoundsBigger().intersects(temp.getBounds())) {
                    x += (velX * 4) * -1;
                    y += (velY * 4) * -1;
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
                if (++tickCounter % (r.nextInt(250 - 200) + 200) == 0) {
                    Handler1.getInstance().addObject(new Bullet(x, y, ID.Bullet, temp.getX() +(r.nextInt( 11+11) -11), temp.getY()+(r.nextInt(11 +11) -11), 30, 1, image));
                }
            }

            if(hp <= 0) {
                System.out.println("test in gegner");
//                Handler1.getInstance().addObject(new Coin(x, y));
                Handler1.getInstance().removeObject(this);
            }
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
        g.setColor(Color.green);
        g.fillRect(x,y,32,32);

        if(Game.showHitbox) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.red);
            g2.draw(getBounds());
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
        return new Rectangle(x-8,y-8,40,40);
    }

    /**
     * Returns the bigger bounds so the enemy does not stuck in the walls.
     * @return bigger bounds as a rectangle
     */
    public Rectangle getBoundsBigger() {
        return new Rectangle(x-16,y-16,64,64);
    }
}
