package Model;

import java.io.Serializable;

/**
 * This class is the Camera. It sets the position of every object displayed in the game.
 */
public class Camera implements Serializable {
    private double x,y;
    private double tempx,tempy;
    private static Camera instance = null;

    /**
     * This is a private constructor so there's only private access to create an instance of the class Camera.
     */
    private Camera(){
    }

    /**
     * With this method only one instance of the class Camera can be made so here's the use of the singleton pattern.
     * If there's no instance of Camera the private Constructor {@link #Camera()} is called. Otherwise the exisiting
     * instance is returned.
     * @return the one and only instance of Camera
     */
    public static Camera getInstance(){
        if(instance == null){
            instance = new Camera();
        }
        return instance;
    }

    /*
        SETTER FOR EVERY ATTRIBUTE
     */

    /**
     * Setter of tempx
     * @param tempx value for Camera's temporary x-coordinate
     */
    public void setTempx(double tempx) {
        this.tempx = tempx;
    }
    /**
     * Setter of tempy
     * @param tempy value for Camera's temporary y-coordinate
     */
    public void setTempy(double tempy) {
        this.tempy = tempy;
    }
    /**
     * Setter of x
     * @param x value for Camera's current x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }
    /**
     * Setter of y
     * @param y value for Camera's current y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /*
        GETTER METHODS FOR ALL ATTRIBUTES
     */

    /**
     * Getter of tempx
     * @return value of Camera's temporary x-coordinate
     */
    public double getTempx() {
        return tempx;
    }
    /**
     * Getter of tempy
     * @return value of Camera's temporary y-coordinate
     */
    public double getTempy() {
        return tempy;
    }
    /**
     * Getter of x
     * @return value of Camera's current x-coordinate
     */
    public double getX() {
        return x;
    }
    /**
     * Getter of y
     * @return value of Camera's current y-coordinate
     */
    public double getY() {
        return y;
    }


    /**
     * To check for changes in a certain period of time.
     * @param object the object which (has to be) changed
     */
    public void tick(GameObject object){
//        x+=(((object.getX())-x)-1000/2)*0.05;
//        y+=(((object.getY())-y)-563/2)*0.05;

// Used to keep camera in place in case of trying to go off screen
//        if(x<=0)x=0;
//        if(x>=1032)x=1032;
//        if(y<=0)y=0;
//        if(y>=563+16)y=563+16;

    }


}
