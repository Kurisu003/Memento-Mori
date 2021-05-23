package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EndBoss extends GameObject{

    int tickCounter = 0;

    Random r= new Random();
    int choose=0;

    private int hp = 200;
    private final int speed = 0;


    public EndBoss(int x, int y, ID id) {
        super(x, y, id);
    }

    @Override
    public void tick() {

        x+=velX;
        y+=velY;


        choose = r.nextInt(50);

        for(int i = 0; i < Handler1.getInstance().objects.size(); i++) {
            GameObject temp = Handler1.getInstance().objects.get(i);

            if(temp.getId() == ID.Block||temp.getId()==ID.Door|| temp.getId() == ID.Obstacle) {
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
                if (++tickCounter % (r.nextInt(50) + 50) == 0) {

                    //Handler1.getInstance().addObject(new Bullet(x, y, ID.Bullet,
                            //temp.getX() +(r.nextInt( 11+11) -11),
                            //temp.getY()+(r.nextInt(11 +11) -11),
                            //30, 1, ,30));
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

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x-16,y-16,64,64);
    }

    public Rectangle getBoundsBigger() {
        return new Rectangle(x-16,y-16,64,64);
    }
}
