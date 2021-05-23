package Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DamageObstacle extends GameObject{

    private int x;
    private int y;

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    private int hitboxH;
    private int hitboxW;
    private transient BufferedImage displayImage;
    private int frameCounter = 0;
    private boolean isSpike;

    public DamageObstacle(int x, int y, boolean isSpike){
        this.isSpike = isSpike;
        this.x = x;
        this.y = y;
        if(!isSpike){
            hitboxH = 45;
            hitboxW = 40;
        }
        else{
            hitboxH = 64;
            hitboxW = 64;
        }
    }

    @Override
    public void tick() {
        if(isSpike) {
            frameCounter = (frameCounter + 1) % 300;
            displayImage = Game.getInstance(1).getDamageObstacleSprites().get((frameCounter / 10) + 8);
        }
        else {
            frameCounter = (frameCounter + 1) % 64;
            displayImage = Game.getInstance(2).getDamageObstacleSprites().get(frameCounter / 8);
        }
    }

    public boolean getIsSpike(){
        return isSpike;
    }

    public int getFrameCounter(){
        return frameCounter;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(displayImage, x, y, null);
        if(Game.showHitbox){
            if(hitboxH == 45)
                g.drawRect(x,y + 20,hitboxH,hitboxW);
            else
                g.drawRect(x,y,hitboxH,hitboxW);

        }
    }

    @Override
    public Rectangle getBounds() {
        if(hitboxH == 45)
            return new Rectangle(x, y + 20, hitboxH, hitboxW);
        else
            return new Rectangle(x, y, hitboxH, hitboxW);

    }

    public ID getId(){
        return ID.DamageObstacle;
    }
}
