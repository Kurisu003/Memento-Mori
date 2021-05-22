package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * This is the "miniboss" in the fourth level. It can pass walls and doors and follows the main character wherever
 * it is going until the miniboss itself is defeated.
 */
public class Miniboss extends SmartEnemy {

    int hp = 10000;


    private transient final BufferedImage hpImage;
    private transient final BufferedImage noHpImage;
    private transient BufferedImage displayImage;
    private transient final ArrayList<BufferedImage> sprites;
    private final Dante dante;
    private double increasingSpeed;

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

        dante = (Dante) Dante.getInstance();

        sprites = new ArrayList<>();
        sprites.add(loader.loadImage("../Enemies/Miniboss/MinibossLeft.png"));
        sprites.add(loader.loadImage("../Enemies/Miniboss/MinibossRight.png"));

        displayImage = sprites.get(0);

        hpImage = loader.loadImage("../Assets/redRec.png");
        noHpImage = loader.loadImage("../Assets/whiteRec.png");

        increasingSpeed = 1.5;

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

                velX=((-1.0/distance)*diffx)*increasingSpeed;
                velY=((-1.0/distance)*diffy)*increasingSpeed;
            }
        }
        if(hp <= 0) {
            for(int i = 0; i < 5; i++){
                Random rand = new Random();
                Handler1.getInstance().addObject(new Coin(x + rand.nextInt(50) + 25, y + rand.nextInt(50) + 25));
            }
            Handler1.getInstance().removeObject(this);
        }

        if (dante.getX() < x)
            displayImage = sprites.get(0);
        else
            displayImage = sprites.get(1);

        x+=velX;
        y+=velY;
    }

    /**
     * When the miniboss got hit by the bullet the health decreases.
     * @param action how much damage the bullet did
     */
    public void doAction(int action){
        this.hp -= action;
        //Miniboss getting faster every -1000hp
        switch (this.hp) {
            case (9000), (8000), (7000), (6000), (5000), (4000), (3000), (2000), (1000) -> this.increasingSpeed += 0.1;
        }
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

    /**
     * Returns the bounds of the enemy.
     * @return rectangle which indicates the bounds
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 128, 128);
    }
}
