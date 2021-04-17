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

    private BufferedImage playerHeadUp = null;
    private BufferedImage playerHeadDown = null;
    private BufferedImage playerHeadLeft = null;
    private BufferedImage playerHeadRight = null;

    ArrayList<BufferedImage> playerBodyUpAnimation = new ArrayList<>();
    ArrayList<BufferedImage> playerBodyDownAnimation = new ArrayList<>();
    ArrayList<BufferedImage> playerBodyLeftAnimation = new ArrayList<>();
    ArrayList<BufferedImage> playerBodyRightAnimation = new ArrayList<>();
    ArrayList<BufferedImage> playerIdleAnimation = new ArrayList<>();

    private BufferedImage shotType1 = null;

    private int timeSinceLastShot = 20;
    private int fireSpeed = 20;
    private int range = 30;
    private int damage = 100;
    private int health = 5;
    private int maxHealth = 5;
    private int armor = 2;

    private int frameCount = 0;
    private int animationSteps = 3;

    private BufferedImage bufferedBodyImage;
    private BufferedImage bufferedHeadImage;
    Controller.Handler1 handler;
    private Camera camera;

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

        playerHeadUp = loader.loadImage("../Tile5.png");
        playerHeadDown = loader.loadImage("../BrickTile32x32.png");
        playerHeadLeft = loader.loadImage("../Tile5.png");
        playerHeadRight = loader.loadImage("../BrickTile32x32.png");

        playerBodyUpAnimation.add(loader.loadImage("../CharBack.png"));
        playerBodyUpAnimation.add(loader.loadImage("../CharBack.png"));
        playerBodyUpAnimation.add(loader.loadImage("../CharBack.png"));
        playerBodyUpAnimation.add(loader.loadImage("../CharBack.png"));

        playerBodyDownAnimation.add(loader.loadImage("../CharFront.png"));
        playerBodyDownAnimation.add(loader.loadImage("../CharFront.png"));
        playerBodyDownAnimation.add(loader.loadImage("../CharFront.png"));
        playerBodyDownAnimation.add(loader.loadImage("../CharFront.png"));

        playerBodyLeftAnimation.add(loader.loadImage("../CharLeft.png"));
        playerBodyLeftAnimation.add(loader.loadImage("../CharLeft.png"));
        playerBodyLeftAnimation.add(loader.loadImage("../CharLeft.png"));
        playerBodyLeftAnimation.add(loader.loadImage("../CharLeft.png"));

        playerBodyRightAnimation.add(loader.loadImage("../CharRight.png"));
        playerBodyRightAnimation.add(loader.loadImage("../CharRight.png"));
        playerBodyRightAnimation.add(loader.loadImage("../CharRight.png"));
        playerBodyRightAnimation.add(loader.loadImage("../CharRight.png"));

        playerIdleAnimation.add(loader.loadImage("../CharFront.png"));
        playerIdleAnimation.add(loader.loadImage("../CharFront.png"));
        playerIdleAnimation.add(loader.loadImage("../CharFront.png"));
        playerIdleAnimation.add(loader.loadImage("../CharFront.png"));

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
            case (4) -> bufferedBodyImage = playerIdleAnimation.get(frameCount);
        }
    }

    private void setHeadImage(int headImageCount) {
        switch (headImageCount) {
            case (0) -> bufferedHeadImage = playerHeadUp;
            case (1) -> bufferedHeadImage = playerHeadDown;
            case (2) -> bufferedHeadImage = playerHeadRight;
            case (3) -> bufferedHeadImage = playerHeadLeft;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,50,50);
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
            shotY = y - 100;
            shotX = x + 32;

            shotXStart = x + 32;

            setHeadImage(0);
        }
        if(handler.isShootDown()){
            shotY = y + 100;
            shotX = x + 32;

            shotXStart = x + 32;

            setHeadImage(1);
        }
        if(handler.isShootLeft()){
            shotX = x - 100;
            shotY = y;

            setHeadImage(2);
        }
        if(handler.isShootRight()){
            shotX = x + 100 + 32;
            shotY = y;

            shotXStart = x + 32;

            setHeadImage(3);
        }

        if(shotY != y|| shotX != x)
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
                x+=velX*-1;
                y+=velY*-1;

//                temp.getX()
                // To know if its horizontal
                // Checks if player is within
                // a 10 px margin of door vertically
                if(temp.getY() - 10 < y && temp.getY() + 10 > y){
                    // If player is to the left
                    // of a door and wants to
                    // go to a room to the right
                    if(x < temp.getX()){
                        x += 128;
                        camera.setX(camera.getX() + 64 * 16);
                    }
                    // If player is to the right
                    // of a door and wants to
                    // go to a room to the left
                    else{
                        x -= 128;
                        camera.setX(camera.getX() - 64 * 16);
                    }
                    if(temp.doAction(0) == 1){

                    }
                }

                // To know if its vertical
                // Checks if player is within
                // a 10 px margin of door horizontally
                else if(temp.getX() - 10 < x && temp.getX() + 10 > x){
                    // If player is below
                    // a door and wants to
                    // go to a room above
                    if(y < temp.getY()){
                        y += 128;
                        camera.setY(camera.getY() + 64 * 8);
                    }
                    // If player is above
                    // a door and wants to
                    // go to a room below
                    else{
                        y -= 128;
                        camera.setY(camera.getY() - 64 * 8);
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

    // Used to render image of player head and body
    public void render(Graphics g) {
        g.drawImage(bufferedBodyImage, x, y, null);
        g.drawImage(bufferedHeadImage, x, y - 30, null);

        // Sets healtsh to a min value of 0
        if (health < 0)
            health = 0;

        // Draws background
        g.setColor(Color.BLACK);
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
        g.drawLine((i * 25) , 20, (25 * i) + 25 , 20);

        // Bottom healthbar line
        g.drawLine((i * 25) , 40, (25 * i) + 25 , 40);

        // healthbar starting line
        g.drawLine((25) , 20, 25 , 40);
        // healthbar dividing lines
        g.drawLine(((i + 1) * 25) , 20, ((i + 1) * 25) , 40);
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
        if(timeSinceLastShot % 7 == 0){
            frameCount = ++frameCount % animationSteps;
        }

        if(timeSinceLastShot % 1000 == 0){
            timeSinceLastShot = 30;
        }
        if  (timeSinceLastShot > fireSpeed &&
                (handler.isShootUp() || handler.isShootDown() || handler.isShootLeft() || handler.isShootRight())){
            shoot();
            timeSinceLastShot = 0;
        }

        // Used to keep track of which direction the player is looking in
        // 4 = Default value used for idle animation
        int setBodyImgCounter = 4;
        if(handler.isUp() && !handler.isDown()){
            velY = -5;
            setBodyImgCounter = 0;
        }
        else if(handler.isDown()) velY = 0;

        if(handler.isDown() && !handler.isUp()){
            velY = 5;
            setBodyImgCounter = 1;
        }
        else if(!handler.isUp()) velY = 0;

        if(handler.isRight() && !handler.isLeft()){
            velX = 5;
            setBodyImgCounter = 2;
        }
        else if(handler.isLeft()) velX = 0;

        if(handler.isLeft() && !handler.isRight()) {
            velX = -5;
            setBodyImgCounter = 3;
        }
        else if (!handler.isRight()) velX = 0;

        setBodyImage(setBodyImgCounter);
    }
}
