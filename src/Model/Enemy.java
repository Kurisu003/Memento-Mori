package Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Handler;
import Controller.*;
import View.BufferedImageLoader;

public class Enemy extends GameObject {

    private Handler1 handler;
    int tickCounter = 0;
    int animationCounter = 0;
    BufferedImage displayedImage;
    ArrayList<BufferedImage> enemyAnimation = new ArrayList<>();

    Random r= new Random();
    int choose=0;
    int hp=100;

    public Enemy(int x, int y, ID id, Handler1 handler) {
        super(x, y, id);
        this.handler=handler;
        BufferedImageLoader loader = new BufferedImageLoader();
        enemyAnimation.add(loader.loadImage("../Character/FrontAnimation1&3.png"));
        enemyAnimation.add(loader.loadImage("../Character/FrontAnimation2.png"));
        enemyAnimation.add(loader.loadImage("../Character/FrontAnimation1&3.png"));
        enemyAnimation.add(loader.loadImage("../Character/FrontAnimation4.png"));
    }

    public void tick() {
        x += velX;
        y += velY;

        if (++tickCounter % 5 == 0){
            displayedImage = enemyAnimation.get(animationCounter);
            animationCounter = (animationCounter + 1) % 4;
        }


        choose = r.nextInt(50);

        for(int i = 0; i < handler.objects.size(); i++) {
            GameObject temp = handler.objects.get(i);

            if(temp.getId() == ID.Block) {
                if(getBoundsBigger().intersects(temp.getBounds())||(temp.getId()==ID.Enemy && temp.hashCode()!=this.hashCode())) {
                    x += (velX * 4) * -1;
                    y += (velY * 4) * -1;
                    velX *= -1;
                    velY *= -1;
                } else if(choose == 0) {
                    velX = (r.nextInt(2 - -2) + -2);
                    velY = (r.nextInt(2 - -2) + -2);

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
            handler.removeObject(this);
        }
    }

    // to do damage to enemy
    public int doAction(int action){
        this.hp -= action;
        return 0;
    }


    public void render(Graphics g) {
        g.drawImage(displayedImage, x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }
//  Needed so that enemy doesnt accidentally get stuck on walls
    public Rectangle getBoundsBigger() {
        return new Rectangle(x-16,y-16,64,64);
    }
}
