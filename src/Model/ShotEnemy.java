package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class ShotEnemy extends GameObject{

    private final Handler1 handler;
    BufferedImage image;

    int tickCounter = 0;
    int animationCounter = 0;

    Random r= new Random();
    Random r2= new Random();
    int choose=0;

    BufferedImage displayedImage;
    ArrayList<BufferedImage> enemyAnimation = new ArrayList<>();


    public ShotEnemy(int x, int y, ID id,Handler1 handler) {
        super(x, y, id);
        this.handler=handler;

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

        for(int i = 0; i < handler.objects.size(); i++) {
            GameObject temp = handler.objects.get(i);

            if(temp.getId() == ID.Block||temp.getId()==ID.Door) {
                if(getBoundsBigger().intersects(temp.getBounds())) {
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
            if(temp.id==ID.Dante){
                if (++tickCounter % (r.nextInt(250 - 200) + 200) == 0) {
                    handler.addObject(new Bullet(x, y, ID.Bullet, handler, temp.getX() +(r.nextInt( 11+11) -11), temp.getY()+(r.nextInt(11 +11) -11), 30, 1, image));
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(x,y,32,32);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x-16,y-16,32,32);
    }

    public Rectangle getBoundsBigger() {
        return new Rectangle(x-16,y-16,64,64);
    }
}
