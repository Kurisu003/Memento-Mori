package Model;

import Controller.Handler1;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * This enemy follows Dante and does damage to him.
 */
public class SmartEnemy extends GameObject{
    private transient BufferedImage displayedImage;
    private int directionalOffsetForAnimationX;
    private int directionalOffsetForAnimationY;

    private int frameCounter=0;
    private int hittingAnimationCounter=0;
    private int walkingAnimationCounter=0;
    private boolean isAnimating = false;
    private final transient ArrayList<BufferedImage> enemySprites;

    private int hp=200;
    private final int speed;

    /**
     * Constructor to create an object of this class
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param id ID of the object
     * @param health amount of health
     * @param speed amount of speed
     */
    public SmartEnemy(int x, int y, ID id, int health, int speed) {
        super(x, y, id);
        this.hp += health;
        this.speed = speed;
        enemySprites = Game.getInstance(22).getEnemySprites();
        displayedImage = enemySprites.get(0);
    }

    /**
     * Keeps the enemy updated to react on changes just like damage and changing directions.
     */
    @Override
    public void tick() {
        directionalOffsetForAnimationX = 0;
        directionalOffsetForAnimationY = 0;

        frameCounter++;
        walkingAnimationCounter = (walkingAnimationCounter + 1) % 42;

        for(GameObject temp: Handler1.getInstance().objects){

            if(temp.getId() == ID.Block || temp.getId() == ID.Obstacle) {
                if (getBoundsBigger().intersects(temp.getBounds())) {
                    x += (velX*2) * -1;
                    y += (velY*2) * -1;
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
                        displayedImage = enemySprites.get(walkingAnimationCounter / 3 + 40);
                    directionalOffsetForAnimationY = 5;
                    velX += speed;
                }
                else{
                    if (hittingAnimationCounter / 3 == 0)
                        displayedImage = enemySprites.get(walkingAnimationCounter / 3 + 54);
                    velX-=speed;
                    directionalOffsetForAnimationY = 7;
                    directionalOffsetForAnimationX = -20;
                }

                // For hitting animation
                if (this.getBounds().intersects(temp.getBounds()) || isAnimating) {
                    hittingAnimationCounter++;

                    if(Dante.getInstance().getX() > x) {
                        displayedImage = enemySprites.get((hittingAnimationCounter / 5) % 20);
                    }
                    else{
                        displayedImage = enemySprites.get((hittingAnimationCounter / 5) % 20 + 20);
                        directionalOffsetForAnimationX = -33;
                    }
                    directionalOffsetForAnimationY = 0;
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
            Random rand = new Random();
            if(rand.nextInt(100) <= 33)
                Handler1.getInstance().addObject(new Coin(x, y));
            Handler1.getInstance().removeObject(this);
        }
    }

    /**
     * Indicates when enemy gets hit by a bullet.
     * @param action how much damage is done
     */
    @Override
    public void doAction(int action){
        this.hp -= action;
    }

    /**
     * Renders the graphics displayed for this enemy.
     * @param g graphics where the drawing should succeed
     */
    @Override
    public void render(Graphics g) {
//        g.setColor(Color.red);
//        g.fillRect(x,y,32,32);

        if(Game.showHitbox) {
            g.setColor(Color.green);
            g.drawRect(x, y, 32, 37);
        }

        g.drawImage(displayedImage, x + directionalOffsetForAnimationX, y - 32 + directionalOffsetForAnimationY, null);
    }

    /**
     * Getter of the health.
     * @return amount of health
     */
    public int getHp() {
        return hp;
    }

    /**
     * Getter of the speed.
     * @return amount of speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Returns normal bounds of the enemy
     * @return rectangle which indicates the bounds
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y - 5,32,37);
    }

    /**
     * Returns bigger bounds so the enemy does not stuck in walls.
     * @return rectangle which indicates bigger bounds
     */
    public Rectangle getBoundsBigger(){
        return new Rectangle(x-16,y - 18,64,74);
    }
}
