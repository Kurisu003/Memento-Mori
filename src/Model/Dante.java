package Model;

import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Dante extends GameObject {


    private BufferedImage playerDown = null;
    private BufferedImage playerUp = null;
    private BufferedImage playerLeft = null;
    private BufferedImage playerRight = null;

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

    public void tick() {
        x+=velX;
        y+=velY;

        collision();

        if(handler.isUp()){
            if(!handler.isDown()) {
                velY = -5;
                setImage(0);
            }
            else if(handler.isDown())
                velY = 0;
        }
        else if(!handler.isDown()) velY = 0;

        if(handler.isDown()){
            if(!handler.isUp()){
                velY = 5;
                setImage(1);
            }
            else if(handler.isUp())
                velY = 0;
        }
        else if(!handler.isUp()) velY = 0;

        if(handler.isRight()){
            if(!handler.isLeft()){
                velX = 5;
                setImage(2);
            }
            else if(handler.isLeft())
                velX = 0;
        }
        else if(!handler.isLeft()) velX = 0;

        if(handler.isLeft()) {
            if (!handler.isRight()){
                velX = -5;
                setImage(3);
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
            case (0) -> bufferedImage = playerUp;
            case (1) -> bufferedImage = playerDown;
            case (2) -> bufferedImage = playerLeft;
            case (3) -> bufferedImage = playerRight;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,32,48);
    }
}
