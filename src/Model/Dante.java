package Model;

import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// To implement:
// recoil player and enemy on contact
// in collision

public class Dante extends GameObject {

//    private BufferedImage playerBodyDown = null;
//    private BufferedImage playerBodyUp = null;
//    private BufferedImage playerBodyLeft = null;
//    private BufferedImage playerBodyRight = null;

    private final BufferedImage playerGunUp;
    private final BufferedImage playerGunDown;
    private final BufferedImage playerGunLeft;
    private final BufferedImage playerGunRight;

    ArrayList<BufferedImage> playerBodyUpAnimation = new ArrayList<>();
    ArrayList<BufferedImage> playerBodyDownAnimation = new ArrayList<>();
    ArrayList<BufferedImage> playerBodyLeftAnimation = new ArrayList<>();
    ArrayList<BufferedImage> playerBodyRightAnimation = new ArrayList<>();
    ArrayList<BufferedImage> playerIdleAnimation = new ArrayList<>();

    private final BufferedImage shotType1;

    private int timeSinceLastShot = 20;
    private int fireSpeed = 20;
    private int range = 30;
    private int health = 5;
    private int armor = 2;

    private int frameCount = 0;
    private int idleAnimationCounter = 0;

    private BufferedImage bufferedBodyImage;
    private BufferedImage bufferedGunImage;
    Controller.Handler1 handler;
    private final Camera camera;

    public Dante(int x, int y, ID id, Controller.Handler1 handler1, Camera camera) {
        super(x, y, id);
        this.handler = handler1;
        this.camera = camera;

        // Different images according to the direction
        // the player is looking in
        BufferedImageLoader loader = new BufferedImageLoader();
//        playerBodyDown = loader.loadImage("../playerDown.png");
//        playerBodyUp = loader.loadImage("../playerUp.png");
//        playerBodyLeft = loader.loadImage("../playerLeft.png");
//        playerBodyRight = loader.loadImage("../playerRight.png");

        playerGunUp = loader.loadImage("../Guns/M4/M4Up.png");
        playerGunDown = loader.loadImage("../Guns/M4/M4Down.png");
        playerGunLeft = loader.loadImage("../Guns/M4/M4Right.png");
        playerGunRight = loader.loadImage("../Guns/M4/M4Left.png");

        playerBodyUpAnimation.add(loader.loadImage("../Character/BackAnimation1&3.png"));
        playerBodyUpAnimation.add(loader.loadImage("../Character/BackAnimation2.png"));
        playerBodyUpAnimation.add(loader.loadImage("../Character/BackAnimation1&3.png"));
        playerBodyUpAnimation.add(loader.loadImage("../Character/BackAnimation4.png"));

        playerBodyDownAnimation.add(loader.loadImage("../Character/FrontAnimation1&3.png"));
        playerBodyDownAnimation.add(loader.loadImage("../Character/FrontAnimation2.png"));
        playerBodyDownAnimation.add(loader.loadImage("../Character/FrontAnimation1&3.png"));
        playerBodyDownAnimation.add(loader.loadImage("../Character/FrontAnimation4.png"));

        playerBodyLeftAnimation.add(loader.loadImage("../Character/LeftAnimation1&3.png"));
        playerBodyLeftAnimation.add(loader.loadImage("../Character/LeftAnimation2.png"));
        playerBodyLeftAnimation.add(loader.loadImage("../Character/LeftAnimation1&3.png"));
        playerBodyLeftAnimation.add(loader.loadImage("../Character/LeftAnimation4.png"));

        playerBodyRightAnimation.add(loader.loadImage("../Character/RightAnimation1&3.png"));
        playerBodyRightAnimation.add(loader.loadImage("../Character/RightAnimation2.png"));
        playerBodyRightAnimation.add(loader.loadImage("../Character/RightAnimation1&3.png"));
        playerBodyRightAnimation.add(loader.loadImage("../Character/RightAnimation4.png"));

        playerIdleAnimation.add(loader.loadImage("../Idle1.png"));
        playerIdleAnimation.add(loader.loadImage("../Idle2.png"));
        playerIdleAnimation.add(loader.loadImage("../Idle3.png"));
        playerIdleAnimation.add(loader.loadImage("../Idle4.png"));

        shotType1 = loader.loadImage("../Tile.png");
    }

    // to set firespeed of weapon
    public void setFireSpeed(int speed){
        this.fireSpeed = speed;
    }

    // to set range of Weapon
    public void setRange(int range){
        this.range = range;
    }

    // preferably to be fixed with array
    // so it doesnt have to be checked each tick
    public void setBodyImage(int bodyImageCount){
        switch (bodyImageCount) {
            case (0) -> bufferedBodyImage = playerBodyUpAnimation.get(frameCount);
            case (1) -> bufferedBodyImage = playerBodyDownAnimation.get(frameCount);
            case (2) -> bufferedBodyImage = playerBodyRightAnimation.get(frameCount);
            case (3) -> bufferedBodyImage = playerBodyLeftAnimation.get(frameCount);
            case (4) -> bufferedBodyImage = playerIdleAnimation.get(idleAnimationCounter);
        }
    }

    private void setGunImage(int gunImageCount) {
        switch (gunImageCount) {
            case (0) -> bufferedGunImage = playerGunUp;
            case (1) -> bufferedGunImage = playerGunDown;
            case (2) -> bufferedGunImage = playerGunLeft;
            case (3) -> bufferedGunImage = playerGunRight;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x + 7,y,50,64);
    }

    @Override
    // To do damage to player character
    public int doAction(int action) {
        if(armor == 0) health -= action;
        else armor -= action;
        return 0;
    }

    public void shoot(){
        int shotY = y;
        int shotX = x;
        int shotYStart = y;
        int shotXStart = x;

//  Version with side shooting
//        if(handler.isShootUp()) {
//            shotY -= 100;
//            shotXStart += 32;
//        }
//        if(handler.isShootDown()) {
//            shotY += 100;
//            shotXStart += 32;
//            shotYStart += 35;
//        }
//        if(handler.isShootLeft()) {
//            shotX -= 100;
//            shotYStart = shotYStart - y < 35 ? shotYStart + 35 : shotYStart;
//        }
//        if(handler.isShootRight()) {
//            shotX += 100;
//            shotXStart = shotXStart - x < 32 ? shotXStart + 50 : shotXStart;
//            shotYStart = shotYStart - y < 35 ? shotYStart + 35 : shotYStart;
//        }

//  Version where shooting stops if other key is pressed
//        if(handler.isShootUp() && !handler.isShootDown() && !handler.isShootLeft() && !handler.isShootRight()){
//            shotY = y - 100;
//            shotX = x;
//        }
//        if(handler.isShootDown() && !handler.isShootUp() && !handler.isShootLeft() && !handler.isShootRight()){
//            shotY = y + 100;
//            shotX = x;
//        }
//        if(handler.isShootLeft() && !handler.isShootDown() && !handler.isShootUp() && !handler.isShootRight()){
//            shotX = x - 100;
//            shotY = y;
//        }
//        if(handler.isShootRight() && !handler.isShootDown() && !handler.isShootLeft() && !handler.isShootUp()){
//            shotX = x + 100;
//            shotY = y;
//        }


//  Version like the binding of isaac
        if(handler.isShootUp()){
            shotY = y - 132;
            shotX = x + 32;

            shotXStart = x + 30;
            shotYStart = y - 32;

            //setGunImage(0);
        }
        if(handler.isShootDown()){
            shotY = y + 175;
            shotX = x + 30;

            shotXStart = x + 30;
            shotYStart = y + 75;

//            setGunImage(1);
        }
        if(handler.isShootLeft()){
            shotX = x - 130;
            shotY = y + 40;

            shotXStart = x;
            shotYStart = y + 40;

//            setGunImage(2);
        }
        if(handler.isShootRight()){
            shotX = x + 164;
            shotY = y + 40;

            shotXStart = x + 64;
            shotYStart = y + 40;

//            setGunImage(3);
        }

        int damage = 100;
        if(shotY != y || shotX != x)
            handler.addObject(new Bullet(shotXStart, shotYStart, ID.Bullet, handler, shotX, shotY, range, damage, shotType1));
    }

    public void collision(){
        // Checks for collision with blocks and
        // stops player from moving if they're
        // intersecting
        for(GameObject temp : handler.objects){

            if(temp.getId() == ID.Block && getBounds().intersects(temp.getBounds())){
                x+=velX*-1;
                y+=velY*-1;
            }
            if(temp.getId() == ID.Door && getBounds().intersects(temp.getBounds())){
//                x+=velX*-1;
//                y+=velY*-1;

//                temp.getX()
               // System.out.println("player: " + x + " " + y + "\nDoor: " + temp.getX() + " " + temp.getY());

                // To know if its horizontal
                // Checks if player is within
                // a 10 px margin of door vertically
                if(temp.getY() - 32 < y && temp.getY() + 32 > y && (handler.isLeft() || handler.isRight())){
                    // If player is to the left
                    // of a door and wants to
                    // go to a room to the right
                    if(x < temp.getX()){
                        x += 185;
                        camera.setX(camera.getX() + 1088);
                    }
                    // If player is to the right
                    // of a door and wants to
                    // go to a room to the left
                    else{
                        x -= 185;
                        camera.setX(camera.getX() - 1088);
                    }
                }

                // To know if its vertical
                // Checks if player is within
                // a 10 px margin of door horizontally
                else if(temp.getX() - 32 < x && temp.getX() + 64 > x && (handler.isUp() || handler.isDown())){
                    // If player is below
                    // a door and wants to
                    // go to a room above
                    if(y < temp.getY()){
                        y += 185;
                        camera.setY(camera.getY() + 576);
                    }
                    // If player is above
                    // a door and wants to
                    // go to a room below
                    else{
                        y -= 185;
                        camera.setY(camera.getY() - 576);
                    }
                }
            }
            if(temp.getId() == ID.Enemy && getBounds().intersects(temp.getBounds())){
                // To do damage to player
                doAction(1);

//                temp.x = temp.getX() - 1;
//                temp.y = temp.getY() - 1;
//                x += velX*-2;
//                y += velY*-2;
            }
        }
    }

    // Used to render image of player gun and body
    public void render(Graphics g) {
        g.drawImage(bufferedBodyImage, x, y, null);
        g.drawImage(bufferedGunImage, x - 20, y - 30, null);


//        To draw hitboxes
//        Graphics2D g2 = (Graphics2D)g;
//        g2.setColor(Color.green);
//        g2.draw(getBounds());

        // Sets health to a min value of 0
        if (health < 0)
            health = 0;

        // Draws background
        g.setColor(Color.BLACK);
        int maxHealth = 5;
        g.fillRect(25, 19, 25 * maxHealth, 23);

        // Draws red healthbar
        g.setColor(Color.RED);
        g.fillRect(25, 20, 25 * health, 20);

        // Draws white outline
        for(int i = 1; i <= maxHealth; i++)
            addHealth(g, i, Color.white);

        // Draws Armor outline
        for(int i = 1; i <= armor; i++)
            addHealth(g, i, Color.cyan);

    }

    // Draws white healthbar outlines
    public void addHealth(Graphics g, int i, Color color){
        ((Graphics2D) g).setStroke(new BasicStroke(3));
        g.setColor(color);

        // Top healthbar line
        g.drawLine(i * 25 , 20, (25 * i) + 25 , 20);

        // Bottom healthbar line
        g.drawLine(i * 25 , 40, (25 * i) + 25 , 40);

        // healthbar starting line
        g.drawLine(25 , 20, 25 , 40);
        // healthbar dividing lines
        g.drawLine((i + 1) * 25 , 20, (i + 1) * 25 , 40);
    }

    public void tick() {
        x += velX;
        y += velY;

        // checks for collision with objects
        collision();

        // Needed so that shooting is always available when button is pressed
        // and enough time has passed
        timeSinceLastShot++;

        // Reuses timeSinceLastShot as a sort
        // of way to keep way of ticks since it
        // counts up every time. This is needed as we dont
        // want to up animation counter every time as the
        // animations are too fast that way
        int animationSteps = 4;
        if(timeSinceLastShot % 7 == 0){
            frameCount = ++frameCount % animationSteps;
        }
        if(timeSinceLastShot % 20 == 0)
            idleAnimationCounter = ++idleAnimationCounter % animationSteps;

        // Used to keep track of which direction the player is looking in
        // 4 = Default value used for idle animation
        int setBodyImgCounter = 4;
        setGunImage(1);

        if(handler.isUp() && !handler.isDown()){
            velY = -5;
            setBodyImgCounter = 0;
            setGunImage(0);
        }
        else if(handler.isDown()) velY = 0;

        if(handler.isDown() && !handler.isUp()){
            velY = 5;
            setBodyImgCounter = 1;
            setGunImage(1);
        }
        else if(!handler.isUp()) velY = 0;

        if(handler.isRight() && !handler.isLeft()){
            velX = 5;
            setBodyImgCounter = 2;
            setGunImage(2);
        }
        else if(handler.isLeft()) velX = 0;

        if(handler.isLeft() && !handler.isRight()) {
            velX = -5;
            setBodyImgCounter = 3;
            setGunImage(3);
        }
        else if (!handler.isRight()) velX = 0;

        // Needed so character looks int the
        // same direction he shoots
        if(handler.isShootUp()){
            setBodyImgCounter = 0;
            setGunImage(0);

        }
        if(handler.isShootDown()){
            setBodyImgCounter = 1;
            setGunImage(1);

        }
        if(handler.isShootRight()){
            setBodyImgCounter = 2;
            setGunImage(2);
        }
        if(handler.isShootLeft()){
            setBodyImgCounter = 3;
            setGunImage(3);
        }

        // Needed so that walking animation doesnt play while
        // shooting and not moving
        if(!handler.isDown() && !handler.isUp() && !handler.isLeft() && !handler.isRight()){
            frameCount = 0;
        }

        setBodyImage(setBodyImgCounter);

        if(timeSinceLastShot % 1000 == 0){
            timeSinceLastShot = 30;
        }
        if  (timeSinceLastShot > fireSpeed &&
            (handler.isShootUp() || handler.isShootDown() || handler.isShootLeft() || handler.isShootRight())){
            shoot();
            timeSinceLastShot = 0;
        }
    }
}
