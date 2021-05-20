package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This is the "miniboss" in the fourth level. It can pass walls and doors and follows the main character wherever
 * it is going until it's defeated.
 */
public class Miniboss extends SmartEnemy {

    int hp = 10000;

    private transient final BufferedImage hpImage;
    private transient final BufferedImage noHpImage;
    private transient BufferedImage displayImage;
    private transient final ArrayList<BufferedImage> sprites;
    private final Dante dante;

    public Miniboss(int x, int y, ID id, int health, int speed) {
        super(x, y, id, health, speed);
        BufferedImageLoader loader = new BufferedImageLoader();

        dante = (Dante) Dante.getInstance();

        sprites = new ArrayList<>();
        sprites.add(loader.loadImage("../Enemies/Miniboss/MinibossLeft.png"));
        sprites.add(loader.loadImage("../Enemies/Miniboss/MinibossRight.png"));

        displayImage = sprites.get(0);

        hpImage = loader.loadImage("../Assets/redRec.png");
        noHpImage = loader.loadImage("../Assets/whiteRec.png");
    }

    @Override
    public void tick(){
        for(GameObject temp: Handler1.getInstance().objects){
            if(temp.id==ID.Dante){
                double diffx=x-temp.getX()-32;
                double diffy=y-temp.getY()-32;
                double distance=Math.sqrt((x-temp.getX())*(x-temp.getX())+(y-temp.getY())*(y- temp.getY()));

                velX=((-1.0/distance)*diffx)*1.5;
                velY=((-1.0/distance)*diffy)*1.5;
            }
        }
        if(hp <= 0) {
            Handler1.getInstance().removeObject(this);
        }

        if (dante.getX() < x)
            displayImage = sprites.get(0);
        else
            displayImage = sprites.get(1);

        x+=velX;
        y+=velY;
    }

    public int doAction(int action){
        this.hp -= action;
        return 0;
    }

    @Override
    public void render(Graphics g) {
        if(Game.showHitbox) {
            g.setColor(Color.green);
            g.fillRect(x, y, 128, 128);
        }

        g.drawImage(displayImage, x, y, null);

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

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 128, 128);
    }
}
