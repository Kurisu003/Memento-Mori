package Model;

import Controller.Handler1;

import java.awt.*;
import java.util.logging.Handler;

public class SmartEnemy extends GameObject{
    private Handler1 handler;
    public SmartEnemy(int x, int y, ID id, Handler1 handler) {
        super(x, y, id);
        this.handler=handler;
    }

    @Override
    public void tick() {

        for(GameObject temp: handler.objects){

        }

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
