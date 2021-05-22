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
    private static final ArrayList<BufferedImage> sprites = Game.getInstance().getDamageObstacleSprites();

    public DamageObstacle(int x, int y, boolean isSpike){
        this.x = x;
        this.y = y;
    }

    @Override
    public void tick() {
        if(isSpike)
            displayImage = sprites.get(frameCounter + 8);
        else {
            frameCounter = (frameCounter + 1) % 64;
            displayImage = sprites.get(frameCounter / 8);
        }

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
