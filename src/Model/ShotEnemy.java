package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class ShotEnemy extends GameObject{

    BufferedImage image;

    int tickCounter = 0;
    int animationCounter = 0;

    Random r= new Random();
    Random r2= new Random();
    int choose=0;

    BufferedImage displayedImage;
    ArrayList<BufferedImage> enemyAnimation = new ArrayList<>();

    int hp = 200;
    private final int speed;

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
                Handler1.getInstance().removeObject(this);
            }
        }
    }

    // to do damage to enemy
    public int doAction(int action){
        this.hp -= action;
        return 0;
    }

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

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x-8,y-8,40,40);
    }

    public Rectangle getBoundsBigger() {
        return new Rectangle(x-16,y-16,64,64);
    }
}
