package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Miniboss extends SmartEnemy {
    private final Handler1 handler;
    int hp = 1000;

    private final BufferedImage hpImage;
    private final BufferedImage noHpImage;

    public Miniboss(int x, int y, ID id, Handler1 handler) {
        super(x, y, id, handler);
        this.handler = handler;

        BufferedImageLoader loader = new BufferedImageLoader();
        hpImage = loader.loadImage("../Assets/redRec.png");
        noHpImage = loader.loadImage("../Assets/whiteRec.png");
    }
    @Override
    public void tick(){
        for(GameObject temp: handler.objects){
            if(temp.id==ID.Dante){
                double diffx=x-temp.getX()-32;
                double diffy=y-temp.getY()-32;
                double distance=Math.sqrt((x-temp.getX())*(x-temp.getX())+(y-temp.getY())*(y- temp.getY()));

                velX=((-1.0/distance)*diffx)*1.5;
                velY=((-1.0/distance)*diffy)*1.5;
            }
        }
        if(hp <= 0) {
            handler.removeObject(this);
        }
        x+=velX;
        y+=velY;
    }

    public int doAction(int action){
        this.hp -= action;
        return 0;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(x, y, 128, 128);

        //Draw hp bar
        for(int i = 0; i < 10; i++)
            if(i < this.hp/100)
                g.drawImage(hpImage, (int)Game.getCamera().getX()+i*40+344, (int)Game.getCamera().getY()+10, null);
            else
                g.drawImage(noHpImage,(int)Game.getCamera().getX()+i*40+344, (int)Game.getCamera().getY()+10, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 128, 128);
    }
}
