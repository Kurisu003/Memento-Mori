package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EndBoss extends GameObject{

    private transient final BufferedImage hpImage;
    private transient final BufferedImage noHpImage;
    private final transient BufferedImageLoader loader;
    private final transient BufferedImage bulletImage;
    private int tickCounter = 0;

    Random r= new Random();
    private int choose=0;

    private int hp = 10000;
    private final int speed = 0;


    public EndBoss(int x, int y, ID id) {
        super(x, y, id);

        loader = new BufferedImageLoader();
        bulletImage = loader.loadImage("../Assets/Bullet.png");

        hpImage = loader.loadImage("../Assets/redRec.png");
        noHpImage = loader.loadImage("../Assets/whiteRec.png");

    }

    @Override
    public void tick() {
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
                double diffx=x-temp.getX()-32;
                double diffy=y-temp.getY()-32;
                double distance=Math.sqrt((x-temp.getX())*(x-temp.getX())+(y-temp.getY())*(y- temp.getY()));

                velX=((-1.0/distance)*diffx)*1.2;
                velY=((-1.0/distance)*diffy)*1.2;

                if (++tickCounter % (r.nextInt(50) + 50) == 0) {
                    Handler1.getInstance().addObject(new Bullet(x, y, ID.Bullet,
                            temp.getX()+6, temp.getY()+6,
                            50, 1, bulletImage ,30));
                }
            }
            if(temp.getId() == ID.Block && getBounds().intersects(temp.getBounds())){

            }
        }


        if(hp <= 0) {
            Random rand = new Random();
            if(rand.nextInt(100) <= 33)
                Handler1.getInstance().addObject(new Coin(x, y));
            Handler1.getInstance().removeObject(this);
        }
        x+=velX;
        y+=velY;
    }

    @Override
    public void render(Graphics g) {

        if(Game.showHitbox) {
            g.setColor(Color.green);
            g.drawRect(x, y, 64, 64);
        }

        //Draw hp bar
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
     * Detects damage done to the enemy
     * @param action how much damage is done
     */
    public void doAction(int action){
        this.hp -= action;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x-16,y-16,64,64);
    }

    public Rectangle getBoundsBigger() {
        return new Rectangle(x-16,y-16,64,64);
    }
}
