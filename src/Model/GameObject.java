package Model;

import java.awt.*;

public abstract class GameObject {

    protected int x,y;
    protected double velX=0, velY=0;
    protected ID id;

    public GameObject() {

    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public GameObject(int x, int y, ID id){
        this.x=x;
        this.y=y;
        this.id=id;
    }

    public abstract void tick();//to uptade every objects
    public abstract void render(Graphics g);//every pbeject has to be drawn to
    public abstract Rectangle getBounds();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public int doAction(int action){
        return 0;
    }


}
