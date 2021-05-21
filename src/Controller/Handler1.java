package Controller;

import Model.Game;
import Model.GameObject;
import Model.GameState;
import Model.ID;

import java.awt.*;
import java.util.LinkedList;

/**
 * In this class all game objects are saved in a linked list. Different values are checked in this class for example
 * to check in which direction the player is moving or shooting at.
 */
public class Handler1 {

    // Linked list of objects which are in the game
    // examples: character, walls, ...
    public LinkedList<GameObject>objects = new LinkedList<GameObject>();

    private boolean up=false, down=false, right=false, left=false;
    private boolean shootUp=false,shootDown=false,shootRight=false,shootLeft=false;

    private static Handler1 instance = null;

    /**
     * Private constructor of the class Handler1 to only get one instance of this object.
     */
    private Handler1(){}

    /**
     * This method checks if there already exists an instance of this class, if not an instance is created.
     * If there's already an instance the existing one is returned
     * @return the one and only instance of Handler1
     */
    public static Handler1 getInstance(){
        if(instance == null){
            instance = new Handler1();
        }
        return instance;
    }

    /*
        SETTER METHODS OF EVERY ATTRIBUTE
     */

    /**
     * Set the shooting direction to up or not-up
     * @param shootUp true if shooting direction is up, false if not
     */
    public void setShootUp(boolean shootUp) {
        this.shootUp = shootUp;
    }
    /**
     * Set the shooting direction to down or not-down
     * @param shootDown true if shooting direction is down, false if not
     */
    public void setShootDown(boolean shootDown) {
        this.shootDown = shootDown;
    }
    /**
     * Set the shooting direction to right or not-right
     * @param shootRight true if shooting direction is right, false if not
     */
    public void setShootRight(boolean shootRight) {
        this.shootRight = shootRight;
    }
    /**
     * Set the shooting direction to left or not-left
     * @param shootLeft true if shooting direction is left, false if not
     */
    public void setShootLeft(boolean shootLeft) {
        this.shootLeft = shootLeft;
    }
    /**
     * Set the moving direction to up or not-up
     * @param up true if moving direction is up, false if not
     */
    public void setUp(boolean up) {
        this.up = up;
    }
    /**
     * Set the moving direction to down or not-down
     * @param down true if moving direction is down, false if not
     */
    public void setDown(boolean down) {
        this.down = down;
    }
    /**
     * Set the moving direction to right or not-right
     * @param right true if moving direction is right, false if not
     */
    public void setRight(boolean right) {
        this.right = right;
    }
    /**
     * Set the moving direction to left or not-left
     * @param left true if moving direction is left, false if not
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * Set the hitbox to be seen.
     * @param b true -> hitbox can be seen, false -> hitbox can't be seen
     */
    public void setHitbox(boolean b) {
        Game.showHitbox = b;
    }

    /*
        GETTER METHODS OF ALL ATTRIBUTES
     */
    /**
     * Check if shooting direction is up or not-up
     * @return true if shooting direction is set to up, false if not
     */
    public boolean isShootUp() {
        return shootUp;
    }
    /**
     * Check if shooting direction is down or not-down
     * @return true if shooting direction is set to down, false if not
     */
    public boolean isShootDown() {
        return shootDown;
    }
    /**
     * Check if shooting direction is right or not-right
     * @return true if shooting direction is set to right, false if not
     */
    public boolean isShootRight() {
        return shootRight;
    }
    /**
     * Check if shooting direction is left or not-left
     * @return true if shooting direction is set to left, false if not
     */
    public boolean isShootLeft() {
        return shootLeft;
    }
    /**
     * Check if moving direction is up or not-up
     * @return true if moving direction is set to up, false if not
     */
    public boolean isUp() {
        return up;
    }
    /**
     * Check if moving direction is down or not-down
     * @return true if moving direction is set to down, false if not
     */
    public boolean isDown() {
        return down;
    }
    /**
     * Check if moving direction is right or not-right
     * @return true if moving direction is set to right, false if not
     */
    public boolean isRight() {
        return right;
    }
    /**
     * Check if moving direction is left or not-left
     * @return true if moving direction is set to left, false if not
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * This method updates every GameObject in the linked list objects.
     */
    public void tick(){
        for(int i = 0; i < objects.size(); i++){
            GameObject temp=objects.get(i);
            double t1 = System.currentTimeMillis();
            if((temp.getId() == ID.Dante)){
                if(!Game.getState().equals(GameState.GameOver))
                    temp.tick();
            }else
                temp.tick();

            double t2 = System.currentTimeMillis();
//            System.out.println(temp.getId() + "  " + (t2-t1));
        }
    }

    /**
     * This method renders every graphic. In order to keep Dante always on the top the method checks for his ID and
     * displays him as last object.
     * @param g graphic to be rendered
     */
    public void render(Graphics g){

//      Always renders Dante last so its on top of everything
        for(GameObject temp : objects){
            if(!temp.getId().equals(ID.Dante))
                temp.render(g);
        }
        for(GameObject temp : objects){
            if(temp.getId().equals(ID.Dante))
                temp.render(g);
        }

        g.dispose();
    }

    /**
     * Adds an object to the linked list.
     * @param temp object to be added
     */
    public void addObject(GameObject temp){
        objects.add(temp);
    }

    /**
     * Removes an object from the linked list.
     * @param temp object to be removed
     */
    public void removeObject(GameObject temp){
        objects.remove(temp);
    }

    /**
     * Removes the last object from the linked list.
     */
    public void removeLastObject(){
        objects.removeLast();
    }
}
