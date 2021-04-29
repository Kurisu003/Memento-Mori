package Model;

import Controller.Handler1;

import java.awt.*;

public class SmartEnemy extends GameObject{
    private Handler1 handler;
    int hp=200;

    int frameCounter=0;


    public SmartEnemy(int x, int y, ID id, Handler1 handler) {
        super(x, y, id);
        this.handler=handler;


    }

    @Override
    public void tick() {
        frameCounter++;
        for(GameObject temp: handler.objects){

            if(temp.getId() == ID.Block) {
                if (getBoundsBigger().intersects(temp.getBounds())) {
                    x += (velX * 2) * -1;
                    y += (velY * 2) * -1;
                    velX *= -1;
                    velY *= -1;
                }
            }else if(temp.getId()==ID.Dante){

                    if(!(this.getBoundsBigger().intersects(temp.getBounds()))) {
                        if (frameCounter%50==0) {
                            double diffx = x - temp.getX() - 32;
                            double diffy = y - temp.getY() - 32;
                            double distance = Math.sqrt((x - temp.getX()) * (x - temp.getX()) + (y - temp.getY()) * (y - temp.getY()));

                            velX = (double) ((-1.0 / distance) * diffx) * 2;
                            velY = (double) ((-1.0 / distance) * diffy) * 2;

                            frameCounter=0;
                        }
                    }else{

                        double diffx = x - temp.getX() - 32;
                        double diffy = y - temp.getY() - 32;
                        double distance = Math.sqrt((x - temp.getX()) * (x - temp.getX()) + (y - temp.getY()) * (y - temp.getY()));

                        velX = (double) ((-1.0 / distance) * diffx);
                        velY = (double) ((-1.0 / distance) * diffy);

                    }
            }else if(temp.getId()== ID.SmartEnemy && temp.hashCode() != this.hashCode()){
                if(frameCounter%20==0) {
                    if (getBoundsBigger().intersects(temp.getBounds())) {
                        x += (velX * 2) * -1;
                        y += (velY * 2) * -1;
                    }
                }

            }

        }

        x+=velX;
        y+=velY;

        if(hp <= 0) {
            handler.removeObject(this);
        }



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

        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.green);
       g2.draw(getBoundsBigger());

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }

    public Rectangle getBoundsBigger(){
        return new Rectangle(x,y,40,40);
    }
}
