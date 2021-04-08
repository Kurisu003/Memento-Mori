package Model;

import java.awt.*;

public class Dante extends GameObject {

    Controller.Handler1 handler;
    public static boolean isKeyPressed;

    public Dante(int x, int y, ID id, Controller.Handler1 handler1) {
        super(x, y, id);
        this.handler=handler1;
    }

    public void tick() {
        x+=velX;
        y+=velY;

        collision();

        if(handler.isUp()){
            if(!handler.isDown())
                velY = -5;
            else if(handler.isDown())
                velY = 0;
        }
        else if(!handler.isDown()) velY = 0;

        if(handler.isDown()){
            if(!handler.isUp())
                velY = 5;
            else if(handler.isUp())
                velY = 0;
        }
        else if(!handler.isUp()) velY = 0;

        if(handler.isRight()){
            if(!handler.isLeft())
                velX = 5;
            else if(handler.isLeft())
                velX = 0;
        }
        else if(!handler.isLeft()) velX = 0;

        if(handler.isLeft()) {
            if (!handler.isRight())
                velX = -5;
            else if (handler.isRight())
                velX = 0;
        }
        else if(!handler.isRight()) velX = 0;


    }

    public void collision(){
        for(int i=0;i<handler.objects.size();i++){
            GameObject temp= handler.objects.get(i);
            if(temp.getId()== ID.Block){
                if(getBounds().intersects(temp.getBounds())){
                    x+=velX*-1;
                    y+=velY*-1;
                }
            }
        }
    }

    public void render(Graphics g) {

        g.setColor(Color.BLUE);
        g.fillRect(x,y,32,48);

    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,32,48);
    }
}
