package Model;

import Controller.Handler1;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Box extends GameObject {
    private transient final BufferedImage bufferedImage;

    int hitboxW;
    int hitboxH;
    int xOffset;
    int yOffset;

    public Box(int x, int y, ID id, BufferedImage bufferedImage, boolean smallHitboxTrue) {
        super(x, y,id);
        if(smallHitboxTrue){
            hitboxW = 58;
            hitboxH = 58;
            xOffset = 4;
            yOffset = 4;
        }
        else{
            hitboxH = 64;
            hitboxW = 64;
        }
        this.bufferedImage = bufferedImage;
    }



    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(bufferedImage, x, y, null);

//        To draw hitboxes
        if(Game.showHitbox) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.blue);
            g2.draw(getBounds());
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x + xOffset,y + yOffset, hitboxW, hitboxH);
    }

    public int doAction(int action){
        Handler1.getInstance().removeObject(this);
        return 0;
    }
}
