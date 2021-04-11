package Model;

import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Dante extends GameObject {


    private BufferedImage playerBodyDown = null;
    private BufferedImage playerBodyUp = null;
    private BufferedImage playerBodyLeft = null;
    private BufferedImage playerBodyRight = null;

    private BufferedImage playerHeadUp = null;
    private BufferedImage playerHeadDown = null;
    private BufferedImage playerHeadLeft = null;
    private BufferedImage playerHeadRight = null;

    private BufferedImage shotType1 = null;

    private int timeSinceLastShot = 20;
    private int fireSpeed = 20;
    private int range = 30;

    private BufferedImage bufferedBodyImage;
    private BufferedImage bufferedHeadImage;
    Controller.Handler1 handler;

    public Dante(int x, int y, ID id, Controller.Handler1 handler1) {
        super(x, y, id);
        this.handler = handler1;

        // Different images according to the direction
        // the player is looking in
        BufferedImageLoader loader = new BufferedImageLoader();
        playerBodyDown = loader.loadImage("../playerDown.png");
        playerBodyUp = loader.loadImage("../playerUp.png");
        playerBodyLeft = loader.loadImage("../playerLeft.png");
        playerBodyRight = loader.loadImage("../playerRight.png");

        playerHeadUp = loader.loadImage("../Tile5.png");
        playerHeadDown = loader.loadImage("../BrickTile32x32.png");
        playerHeadLeft = loader.loadImage("../Tile5.png");
        playerHeadRight = loader.loadImage("../BrickTile32x32.png");

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
            case (0) -> bufferedBodyImage = playerBodyUp;
            case (1) -> bufferedBodyImage = playerBodyDown;
            case (2) -> bufferedBodyImage = playerBodyRight;
            case (3) -> bufferedBodyImage = playerBodyLeft;
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
        return new Rectangle(x,y,64,64);
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
            handler.addObject(new Bullet(shotXStart, shotYStart, ID.Bullet, handler, shotX, shotY, range, shotType1));
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
        }
    }

    // Used to render image of player head and body
    public void render(Graphics g) {
        g.drawImage(bufferedBodyImage, x, y, null);
        g.drawImage(bufferedHeadImage, x, y - 30, null);
    }


    public void tick() {
        x += velX;
        y += velY;

        // checks for collision with objects
        collision();


        // Needed so that shooting is always available when button is pressed
        // and enough time has passed
        timeSinceLastShot++;
        if  (timeSinceLastShot > fireSpeed &&
                (handler.isShootUp() || handler.isShootDown() || handler.isShootLeft() || handler.isShootRight())){
            shoot();
            timeSinceLastShot = 0;
        }


        // Used to keep track of which direction the player is looking in
        int setBodyImgCounter = 1;

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
