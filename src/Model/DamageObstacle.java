package Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DamageObstacle extends GameObject{

    private int x;
    private int y;
    private transient BufferedImage displayImage;
    private int frameCounter = 0;
    private boolean isSpike;

    public DamageObstacle(int x, int y, boolean isSpike){
        this.isSpike = isSpike;
        this.x = x;
        this.y = y;
    }

    @Override
    public void tick() {
        if(isSpike) {
            frameCounter = (frameCounter + 1) % 300;
            displayImage = Game.getInstance().getDamageObstacleSprites().get((frameCounter / 10) + 8);
        }
        else {
            frameCounter = (frameCounter + 1) % 64;
            displayImage = Game.getInstance().getDamageObstacleSprites().get(frameCounter / 8);
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
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 64, 64);
    }

    public ID getId(){
        return ID.DamageObstacle;
    }
}
