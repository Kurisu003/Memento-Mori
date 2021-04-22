package Model;

import Controller.Handler1;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ShotEnemy extends GameObject{

    private final Handler1 handler;
    BufferedImage image;


    public ShotEnemy(int x, int y, ID id,Handler1 handler) {
        super(x, y, id);
        this.handler=handler;
        //this.image=image;
    }

    @Override
    public void tick() {
        x+=velX;
        y+=velY;

        for(int i = 0; i < handler.objects.size(); i++) {
            GameObject temp = handler.objects.get(i);
            if(temp.id==ID.Dante){
                handler.addObject(new Bullet(x,y,ID.Bullet,handler,temp.getX(),temp.getY(),30,1,image));
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
}
