package Model;

import Controller.Handler1;

import java.awt.*;

public class SmartEnemy extends GameObject{
    private Handler1 handler;


    public SmartEnemy(int x, int y, ID id, Handler1 handler) {
        super(x, y, id);
        this.handler=handler;


    }

    @Override
    public void tick() {

        for(GameObject temp: handler.objects){
            if(temp.id==ID.Dante){
               double diffx=x-temp.getX()-32;
               double diffy=y-temp.getY()-32;
               double distance=Math.sqrt((x-temp.getX())*(x-temp.getX())+(y-temp.getY())*(y- temp.getY()));

               velX=(double) ((-1.0/distance)*diffx)*2;
               velY=(double) ((-1.0/distance)*diffy)*2;
            }
        }

        x+=velX;
        y+=velY;

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x,y,32,32);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }

    public Rectangle getBoundBigger(){
        return new Rectangle(x-16,y-16,64,64);
    }
}
