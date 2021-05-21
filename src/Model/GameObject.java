package Model;

import java.awt.*;
import java.io.Serializable;

/**
 * Abstract class from which every kind of object in the game extends from.
 * It is used as a template for every object.
 */
public abstract class GameObject implements Serializable {

    protected int x,y;
    protected double velX=0, velY=0;
    protected ID id;

    /**
     * Empty constructor of GameObject
     */
    public GameObject() {

    }

    /**
     * Constructor of GameObject which is called by every extending class.
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param id ID of the object
     */
    public GameObject(int x, int y, ID id){
        this.x=x;
        this.y=y;
        this.id=id;
    }

    /*
        METHODS WHICH MUST BE IMPLEMENTED BY SUBCLASSES
     */

    /**
     * To update every object.
     */
    public abstract void tick();

    /**
     * To draw every object
     * @param g graphics where the drawing should succeed.
     */
    public abstract void render( Graphics g);//every pbeject has to be drawn to

    /**
     * To get bounds for every objects to detect collisions.
     * @return rectangle of the bounds
     */
    public abstract Rectangle getBounds();

    /*
        CAN BE IMPLEMENTED
     */

    /**
     * To indicate damage done to an object.
     * @param action how much damage is done
     */
    public void doAction(int action){
    }

    /*
        SETTER METHODS WHICH CAN BE IMPLEMENTED BY SUBCLASSES
     */

    /**
     * Setter of the ID
     * @param id ID of the current object
     */
    public void setId(ID id) {
        this.id = id;
    }

    /**
     * Setter of the x-coordinate.
     * @param x x-coordinate which should be set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter of the y-coordinate.
     * @param y y-coordinate which should be set
     */
    public void setY(int y) {
        this.y = y;
    }

    /*
        GETTER METHODS WHICH CAN BE IMPLEMENTED BY SUBCLASSES
     */

    /**
     * Getter method of the ID.
     * @return ID of the current object
     */
    public ID getId() {
        return id;
    }

    /**
     * Getter method of the x-coordinate.
     * @return current x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Getter method of the y-coordinate.
     * @return current y-coordinate
     */
    public int getY() {
        return y;
    }
}
