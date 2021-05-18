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

    private int tickCounter = 0;
    private int animationCounter = 0;

    transient BufferedImage displayedImage;
    transient ArrayList<BufferedImage> enemyAnimation = new ArrayList<>();


    Random r= new Random();
    private int choose=0;

    public int getHp() {
        return hp;
    }

    public int getSpeed() {
        return speed;
    }

    private int hp=100;
    private int speed;

    public Enemy(int x, int y, ID id, int health, int speed) {
        super(x, y, id);
        this.hp += health;
        this.speed = speed;
        BufferedImageLoader loader = new BufferedImageLoader();
    }

    public void tick() {
        x += velX;
        y += velY;

        choose = r.nextInt(50);

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
