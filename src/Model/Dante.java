package Model;

import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Dante extends GameObject {


    private BufferedImage playerDown = null;
    private BufferedImage playerUp = null;
    private BufferedImage playerLeft = null;
    private BufferedImage playerRight = null;

    private BufferedImage playerDownRight = null;
    private BufferedImage playerUpRight = null;
    private BufferedImage playerDownLeft = null;
    private BufferedImage playerUpLeft = null;

    private int shotCounter = 0;
    private int fireSpeed = 10;

    private BufferedImage bufferedImage;
    Controller.Handler1 handler;
    public static boolean isKeyPressed;

    public Dante(int x, int y, ID id, Controller.Handler1 handler1, BufferedImage bufferedImage) {
        super(x, y, id);
        this.handler=handler1;
        this.bufferedImage = bufferedImage;

        BufferedImageLoader loader = new BufferedImageLoader();
        playerDown = loader.loadImage("../PlayerDown.png");
        playerUp = loader.loadImage("../PlayerUp.png");
    }

    public void setFireSpeed(int speed){
        this.fireSpeed = speed;
    }

    public void tick() {
        x+=velX;
        y+=velY;
        int setImgCounter = 0;
        collision();
        if(++shotCounter % fireSpeed == 0){
            shoot();
            shotCounter = 0;
        }

        if(handler.isUp()){
            if(!handler.isDown()) {
                velY = -5;
                setImgCounter += 1;
            }
            else if(handler.isDown())
                velY = 0;
        }
        else if(!handler.isDown()) velY = 0;

        if(handler.isDown()){
            if(!handler.isUp()){
                velY = 5;
                setImgCounter += 2;
            }
            else if(handler.isUp())
                velY = 0;
        }
        else if(!handler.isUp()) velY = 0;

        if(handler.isRight()){
            if(!handler.isLeft()){
                velX = 5;
                setImgCounter += 4;
            }
            else if(handler.isLeft())
                velX = 0;
        }
        else if(!handler.isLeft()) velX = 0;

        if(handler.isLeft()) {
            if (!handler.isRight()){
                velX = -5;
                setImgCounter += 8;
            }
            else if (handler.isRight())
                velX = 0;
        }
        else if(!handler.isRight()) velX = 0;
    }

    public void collision(){
        for(int i=0;i<handler.objects.size();i++){
            GameObject temp= handler.objects.get(i);
            if(temp.getId()== ID.Block){
                if(getBounds().intersects(temp.getBounds())){
                    x+=velX*-1;
                    y+=velY*-1;
                }
            }
        }
    }

//    public void render(Graphics g) {
//
//        g.setColor(Color.BLUE);
//        g.fillRect(x,y,32,48);
//
//    }

    public void render(Graphics g) {
        g.drawImage(bufferedImage, x, y, null);
    }

    public void setImage(int position){
        switch (position) {
            case (1) -> bufferedImage = playerUp;
            case (2) -> bufferedImage = playerDown;
            case (4) -> bufferedImage = playerLeft;
            case (5) -> bufferedImage = playerUpLeft;
            case (6) -> bufferedImage = playerDownLeft;
            case (8) -> bufferedImage = playerRight;
            case (9) -> bufferedImage = playerUpRight;
            case (10) -> bufferedImage = playerDownRight;
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
        if(handler.isShootUp()) {
            shotY -= 100;
            shotXStart += 32;
        }
        if(handler.isShootDown()) {
            shotY += 100;
            shotXStart += 32;
            shotYStart += 35;
        }
        if(handler.isShootLeft()) {
            shotX -= 100;
            shotYStart = shotYStart - y < 35 ? shotYStart + 35 : shotYStart;
        }
        if(handler.isShootRight()) {
            shotX += 100;
            shotXStart = shotXStart - x < 32 ? shotXStart + 50 : shotXStart;
            shotYStart = shotYStart - y < 35 ? shotYStart + 35 : shotYStart;
        }

        if(shotY != y|| shotX != x)
            handler.addObject(new Bullet(shotXStart, shotYStart, ID.Bullet, handler, shotX + 32, shotY + 32));
    }
}
