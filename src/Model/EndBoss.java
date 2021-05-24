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
public class EndBoss extends GameObject{

    private transient final BufferedImage hpImage;
    private transient final BufferedImage noHpImage;
    private final transient BufferedImageLoader loader;
    private final transient BufferedImage bulletImage;
    private final transient BufferedImage displayImage;

    int tickCounter = 0;

    Random r= new Random();
    int choose=0;

    private int hp = 10000;

    /**
     * Constructor to create an instance.
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param id ID of this object
     */
    public EndBoss(int x, int y, ID id) {
        super(x, y, id);
        loader = new BufferedImageLoader();
        bulletImage = loader.loadImage("../Assets/SatanBullet.png");
        hpImage = loader.loadImage("../Assets/redRec.png");
        noHpImage = loader.loadImage("../Assets/whiteRec.png");
        displayImage = loader.loadImage("../Enemies/Satan/SatanSprite2.png");
    }

    /**
     * Updates in order to check changes and to react on them. For example if damage is done to the enemy to update the
     * remaining health or to display it if it's dead.
     */
    @Override
    public void tick() {
        x+=velX;
        y+=velY;

        choose = r.nextInt(50);

        tickCounter++;

        if(tickCounter % 10 == 0){
            System.out.println("bullet");
            Handler1.getInstance().addObject(new Bullet(x, y, ID.Bullet, x, y + 100, 100, 1, bulletImage, 2));
        }

        for(int i = 0; i < Handler1.getInstance().objects.size(); i++) {
            GameObject temp = Handler1.getInstance().objects.get(i);

            if(temp.getId() == ID.Block||temp.getId()==ID.Door|| temp.getId() == ID.Obstacle) {
                if(getBoundsBigger().intersects(temp.getBounds())) {
                    x += (velX) * -1;
                    y += (velY) * -1;
                    velX *= -1;
                    velY *= -1;
                } else if(choose == 0) {
                    velX = (r.nextInt(2 + 2) - 2);
                    velY = (r.nextInt(2 + 2) - 2);

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
//            if(temp.getId() == ID.EndBoss)
//                if (++tickCounter % (r.nextInt(30) + 30) == 0) {
//
//                    Handler1.getInstance().addObject(new Bullet(temp.getX()+32, temp.getY(), ID.Bullet,
//                            temp.getX() +32, //up
//                            temp.getY() +1000,
//                            30, 1, bulletImage,30));
//                    Handler1.getInstance().addObject(new Bullet(temp.getX()+32, temp.getY()-64, ID.Bullet,
//                            temp.getX()+32, temp.getY() -64-1000, 30, 1, bulletImage,30)); //down
//                    Handler1.getInstance().addObject(new Bullet(temp.getX(), temp.getY()-32, ID.Bullet,
//                            temp.getX() -1000, temp.getY()-32, 30, 1, bulletImage,30)); //left
//                    Handler1.getInstance().addObject(new Bullet(temp.getX()+64, temp.getY()-32, ID.Bullet,
//                            temp.getX() +64+1000, temp.getY() -32, 30, 1, bulletImage,30)); //right
//            }
        }


        if(hp <= 0) {
            Game.setState(GameState.Won);
        }
    }

    /**
     * Detects damage done to the enemy
     * @param action how much damage is done
     */
    public void doAction(int action){
        this.hp -= action/2;
    }

    /**
     * Renders the image and graphics of the enemy
     * @param g graphics where the drawing should succeed.
     */
    @Override
    public void render(Graphics g) {

        if(Game.showHitbox) {
            g.setColor(Color.green);
            g.drawRect(x, y, 165, 99);
        }

        g.drawImage(displayImage, x, y, null);

        for(int i = 0; i < 100; i++){
            g.drawImage(noHpImage, (int) Camera.getInstance().getX() + i * 10 + 50,
                    (int) Camera.getInstance().getY() + 64 * 8, null);
        }

        for(int i = 0; i < hp/100; i++) {
            g.drawImage(hpImage, (int) Camera.getInstance().getX() + i * 10 + 50,
                    (int) Camera.getInstance().getY() + 64 * 8, null);
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
     * Returns the normal bounds of the enemy.
     * @return rectangle of the bounds
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x ,y,165,99);
    }

    /**
     * Returns the bigger bounds so the enemy does not stuck in the walls.
     * @return bigger bounds as a rectangle
     */
    public Rectangle getBoundsBigger() {
        return new Rectangle(x-16,y-16,64,64);
    }
}
