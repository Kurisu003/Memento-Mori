package Model;

import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EndBoss extends GameObject{

    private final BufferedImageLoader loader;
    private final transient BufferedImage bulletImage;

    int tickCounter = 0;

    Random r= new Random();
    int choose=0;

    private int hp = 200;
    private final int speed = 0;

    private transient BufferedImage displayedImage;
    private final transient BufferedImage left;
    private final transient BufferedImage right;

    public EndBoss(int x, int y, ID id) {
        super(x, y, id);

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
