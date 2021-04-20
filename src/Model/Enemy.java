package Model;

import java.awt.*;
import java.util.Random;
import java.util.logging.Handler;
import Controller.*;

public class Enemy extends GameObject {

    private Handler1 handler;

    Random r= new Random();
    int choose=0;
    int hp=100;

    public Enemy(int x, int y, ID id, Handler1 handler) {
        super(x, y, id);
        this.handler=handler;
    }

    public void tick() {
        x += velX;
        y += velY;

        choose = r.nextInt(10);

        for(int i = 0; i < handler.objects.size(); i++) {
            GameObject temp = handler.objects.get(i);

            if(temp.getId() == ID.Block) {
                if(getBoundsBigger().intersects(temp.getBounds())) {
                    x += (velX * 4) * -1;
                    y += (velY * 4) * -1;
                    velX *= -1;
                    velY *= -1;
                } else if(choose == 0) {
                    velX = (r.nextInt(4 - -4) + -4);
                    velY = (r.nextInt(4 - -4) + -4);
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
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x,y,32,32);
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }
//  Needed so that enemy doesnt accidentally get stuck on walls
    public Rectangle getBoundsBigger() {
        return new Rectangle(x-16,y-16,64,64);
    }
}
