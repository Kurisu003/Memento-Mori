package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SmartEnemy extends GameObject{
    private Handler1 handler;
    int hp=200;
    private BufferedImage displayedImage;
    private ArrayList<BufferedImage> sprites;

    int frameCounter=0;
    int animationCounter=0;
    boolean isAnimating = false;

    public SmartEnemy(int x, int y, ID id, Handler1 handler) {
        super(x, y, id);
        this.handler=handler;
        sprites = new ArrayList<BufferedImage>();

        BufferedImageLoader loader = new BufferedImageLoader();
        for(int i = 1; i <= 20; i++)
            sprites.add(loader.loadImage("../Enemies/SmartEnemy/Sprite (" + i + ").png").getSubimage(130, 22,80,72));

        displayedImage = sprites.get(0);
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
                double diffX = x - temp.getX() - 32;
                double diffY = y - temp.getY() - 32;
                double distance = Math.sqrt((x - temp.getX()) * (x - temp.getX()) + (y - temp.getY()) * (y - temp.getY()));

                velX = ((-1.0 / distance) * diffX);
                velY = ((-1.0 / distance) * diffY);

                if(!(this.getBoundsBigger().intersects(temp.getBounds()))) {
                    if (frameCounter%50==0) {
                        velX *= 2;
                        velY *= 2;
                        frameCounter=0;
                    }
                }

                // For hitting animation
                if (this.getBounds().intersects(temp.getBounds()) || isAnimating) {
                    animationCounter++;
                    displayedImage = sprites.get((animationCounter / 5) % 20);
                    // isAnimating is needed so that animation finishes playing
                    // if player steps out of hitbox
                    isAnimating = true;
                    // Needed so that enemy stands still while hitting player
                    velX += velX * -1;
                    velY += velY * -1;

                    // Needed so animationcounter doesn't overflow
                    if(animationCounter >= 100){
                        animationCounter = 0;
                    }
                    // Needed to time when damage needs to be done to player
                    if(animationCounter >= 40 && animationCounter <= 70 && this.getBounds().intersects(temp.getBounds())){
                        Dante.getInstance().doAction(1);
                    }
                }
                // If animationcounter has reached its finish,
                // is animating is set to false
                if(((animationCounter / 5)  % 20) == 0){
                    isAnimating = false;
                }
                }else if(temp.getId() == ID.SmartEnemy && temp.hashCode() != this.hashCode()){
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
//        g.setColor(Color.red);
//        g.fillRect(x,y,32,32);

        if(Game.showHitbox) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.green);
            g2.draw(getBoundsBigger());
        }

        g.drawImage(displayedImage, x, y - 32, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y - 5,32,37);
    }

    public Rectangle getBoundsBigger(){
        return new Rectangle(x,y,40,40);
    }
}
