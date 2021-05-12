package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SmartEnemy extends GameObject{
    int hp=200;
    private transient BufferedImage displayedImage;
    private int directionalOffsetForAnimationX;
    private int directionalOffsetForAnimationY;

    int frameCounter=0;
    int hittingAnimationCounter=0;
    int walkingAnimationCounter=0;
    boolean isAnimating = false;

    public SmartEnemy(int x, int y, ID id) {
        super(x, y, id);
        displayedImage = Game.getInstance().getEnemySprites().get(0);
    }

    @Override
    public void tick() {
        directionalOffsetForAnimationX = 0;
        directionalOffsetForAnimationY = 0;

        frameCounter++;
        walkingAnimationCounter = (walkingAnimationCounter + 1) % 42;

        for(GameObject temp: Handler1.getInstance().objects){

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
                // For walking animation
                // needs to play before hitting animation
                // so it can be overwritten.
                // 40 needs to be added as there is 20 Game.getInstance().getEnemySprites() before it
                // from the hitting animation
                if(Dante.getInstance().getX() > x) {
                    if (hittingAnimationCounter / 3 == 0)
                        displayedImage = Game.getInstance().getEnemySprites().get(walkingAnimationCounter / 3 + 40);
                    directionalOffsetForAnimationY = 5;
                }
                else{
                    if (hittingAnimationCounter / 3 == 0)
                        displayedImage = Game.getInstance().getEnemySprites().get(walkingAnimationCounter / 3 + 54);
                    directionalOffsetForAnimationY = 7;
                    directionalOffsetForAnimationX = -20;
                }

                // For hitting animation
                if (this.getBounds().intersects(temp.getBounds()) || isAnimating) {
                    hittingAnimationCounter++;

                    if(Dante.getInstance().getX() > x) {
                        displayedImage = Game.getInstance().getEnemySprites().get((hittingAnimationCounter / 5) % 20);
                        directionalOffsetForAnimationY = 0;
                    }
                    else{
                        displayedImage = Game.getInstance().getEnemySprites().get((hittingAnimationCounter / 5) % 20 + 20);
                        directionalOffsetForAnimationX = -33;
                        directionalOffsetForAnimationY = 0;
                    }
                    // isAnimating is needed so that animation finishes playing
                    // if player steps out of hitbox
                    isAnimating = true;
                    // Needed so that enemy stands still while hitting player
                    velX += velX * -1;
                    velY += velY * -1;

                    // Needed so hittingAnimationCounter doesn't overflow
                    if(hittingAnimationCounter >= 100){
                        hittingAnimationCounter = 0;
                    }
                    // Needed to time when damage needs to be done to player
                    if(hittingAnimationCounter >= 40 && hittingAnimationCounter <= 70 && this.getBounds().intersects(temp.getBounds())){
                        Dante.getInstance().doAction(1);
                    }
                }
                // If hittingAnimationCounter has reached its finish,
                // is animating is set to false
                if(((hittingAnimationCounter / 5)  % 20) == 0){
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
            Handler1.getInstance().removeObject(this);
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

        g.drawImage(displayedImage, x + directionalOffsetForAnimationX, y - 32 + directionalOffsetForAnimationY, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y - 5,32,37);
    }

    public Rectangle getBoundsBigger(){
        return new Rectangle(x,y - 10,30,50);
    }
}
