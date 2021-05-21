package Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import Controller.*;

/**
 * This is a very simple and dumb enemy which just walks around.
 */
public class Enemy extends GameObject {

    private final int tickCounter = 0;
    private final int animationCounter = 0;

    transient BufferedImage displayedImage;
    transient ArrayList<BufferedImage> enemyAnimation = new ArrayList<>();


    Random r= new Random();

    private int hp=100;
    private final int speed;

    /**
     * The constructor to create an instance of this.
     * @param x x-coordinate where the enemy should spawn
     * @param y y-coordinate where the enemy should spawn
     * @param id ID for this object
     * @param health how much health the enemy has
     * @param speed how fast the enemy is
     */
    public Enemy(int x, int y, ID id, int health, int speed) {
        super(x, y, id);
        this.hp += health;
        this.speed = speed;
    }

    /**
     * Checks in a certain period of time if something has changed. Eventually removes the enemy if it has no health
     * remaining. Checks for the bounds so the enemy does not stuck in walls.
     */
    @Override
    public void tick() {
        x += velX;
        y += velY;

        int choose = r.nextInt(50);

        if (Dante.getInstance().getX() < x)
            displayedImage = Game.getInstance().getEnemySprites().get(68);
        else
            displayedImage = Game.getInstance().getEnemySprites().get(69);


        for(int i = 0; i < Handler1.getInstance().objects.size(); i++) {
            GameObject temp = Handler1.getInstance().objects.get(i);

            if(temp.getId() == ID.Block) {
                if(getBoundsBigger().intersects(temp.getBounds())||(temp.getId()==ID.Enemy && temp.hashCode()!=this.hashCode())) {
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
        }

        if(hp <= 0) {

            Handler1.getInstance().removeObject(this);
        }
    }

    /**
     * Displays the enemy image.
     * @param g graphics where it should be rendered on
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(displayedImage, x, y, null);
    }

    /**
     * This method is called when Dante does damage to the enemy and then updates the remaining health.
     * @param action how much damage it does to the enemy
     */
    @Override
    public void doAction(int action){
        this.hp -= action;
    }

    /**
     * Returns the health of the enemy.
     * @return amount of health
     */
    public int getHp() {
        return hp;
    }

    /**
     * Returns how fast the enemy is.
     * @return speed of the enemy
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Returns the actual size of the enemy.
     * @return a rectangle which indicates the enemy's bounds
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }

    /**
     * Returns a bigger bound in order to provide the enemy from being stuck in the walls.
     * @return bounds double as big as the actual object is
     */
    public Rectangle getBoundsBigger() {
        return new Rectangle(x-16,y-16,64,64);
    }
}
