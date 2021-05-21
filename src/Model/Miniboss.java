package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This is the "miniboss" in the fourth level. It can pass walls and doors and follows the main character wherever
 * it is going until the miniboss itself is defeated.
 */
public class Miniboss extends SmartEnemy {

    int hp = 2000;

    private transient final BufferedImage hpImage;
    private transient final BufferedImage noHpImage;

    /**
     * Constructor to create a new object. It loads the healthbar image immediately.
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param id ID of this object
     * @param health amount of health
     * @param speed amount of speed
     */
    public Miniboss(int x, int y, ID id, int health, int speed) {
        super(x, y, id, health, speed);

        BufferedImageLoader loader = new BufferedImageLoader();
        hpImage = loader.loadImage("../Assets/redRec.png");
        noHpImage = loader.loadImage("../Assets/whiteRec.png");
    }

    /**
     * Update of the position to follow Dante and update of the remaining health in case Dante shot him with the gun.
     */
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
        x+=velX;
        y+=velY;
    }

    /**
     * When the miniboss got hit by the bullet the health decreases.
     * @param action how much damage the bullet did
     */
    public void doAction(int action){
        this.hp -= action;
    }

    /**
     * Renders the hitbox of the enemy and draws the healthbar and keeps updated.
     * @param g graphics where it should be drawn
     */
    @Override
    public void render(Graphics g) {
        if(Game.showHitbox) {
            g.setColor(Color.green);
            g.fillRect(x, y, 128, 128);
        }

        //Draw hp bar
        for(int i = 0; i < 10; i++)
            if(i < this.hp/100)
                g.drawImage(hpImage, (int)Camera.getInstance().getX()+i*40+344,
                            (int)Camera.getInstance().getY()+10, null);
            else
                g.drawImage(noHpImage,(int)Camera.getInstance().getX()+i*40+344,
                            (int)Camera.getInstance().getY()+10, null);
    }

    /**
     * Returns the bounds of the enemy.
     * @return rectangle which indicates the bounds
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 128, 128);
    }
}
