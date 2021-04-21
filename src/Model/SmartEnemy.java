package Model;

import Controller.Handler1;

import java.awt.*;

public class SmartEnemy extends GameObject{
    private Handler1 handler;
    int hp=200;


    public SmartEnemy(int x, int y, ID id, Handler1 handler) {
        super(x, y, id);
        this.handler=handler;


    }

    @Override
    public void tick() {

        for(GameObject temp: handler.objects){

            if(temp.getId() == ID.Block) {
                if (getBoundsBigger().intersects(temp.getBounds())) {
                    x += (velX * 4) * -1;
                    y += (velY * 4) * -1;
                    velX *= -1;
                    velY *= -1;
                }
            }

            if(temp.id==ID.Dante){
               double diffx=x-temp.getX()-32;
               double diffy=y-temp.getY()-32;
               double distance=Math.sqrt((x-temp.getX())*(x-temp.getX())+(y-temp.getY())*(y- temp.getY()));

               velX=(double) ((-1.0/distance)*diffx)*2;
               velY=(double) ((-1.0/distance)*diffy)*2;
            }


        }

        if(hp <= 0) {
            handler.removeObject(this);
        }

        x+=velX;
        y+=velY;

    }

    // to do damage to enemy
    public int doAction(int action){
        this.hp -= action;
        return 0;
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

    public Rectangle getBoundsBigger(){
        return new Rectangle(x-16,y-16,64,64);
    }
}
