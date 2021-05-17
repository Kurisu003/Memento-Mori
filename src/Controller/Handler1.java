package Controller;

import Model.Game;
import Model.GameObject;
import Model.ID;

import java.awt.*;
import java.util.LinkedList;
import java.util.logging.Handler;

public class Handler1 {

    // Linked list an Objekten die sich im Spiel befinden
    // Beispiel: Spieler, Wand, etc.
    public LinkedList<GameObject>objects = new LinkedList<GameObject>();

    private boolean up=false, down=false, right=false, left=false;

    private static Handler1 instance = null;

    private Handler1(){}

    public static Handler1 getInstance(){
        if(instance == null){
            instance = new Handler1();
        }
        return instance;
    }

    public boolean isShootUp() {
        return shootUp;
    }

    public void setShootUp(boolean shootUp) {
        this.shootUp = shootUp;
    }

    public boolean isShootDown() {
        return shootDown;
    }

    public void setShootDown(boolean shootDown) {
        this.shootDown = shootDown;
    }

    public boolean isShootRight() {
        return shootRight;
    }

    public void setShootRight(boolean shootRight) {
        this.shootRight = shootRight;
    }

    public boolean isShootLeft() {
        return shootLeft;
    }

    public void setShootLeft(boolean shootLeft) {
        this.shootLeft = shootLeft;
    }

    private boolean shootUp=false,shootDown=false,shootRight=false,shootLeft=false;

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void tick(){
        for(int i = 0; i < objects.size(); i++){
            GameObject temp=objects.get(i);
            double t1 = System.currentTimeMillis();
            temp.tick();
            double t2 = System.currentTimeMillis();
            System.out.println(temp.getId() + "  " + (t2-t1));
        }
    }

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
    }

    public void addObject(GameObject temp){
        objects.add(temp);
    }

    public void removeLastObject(){
        objects.removeLast();
    }

    public void removeObject(GameObject temp){
        objects.remove(temp);
    }

    public void setHitbox(boolean b) {
        Game.showHitbox = b;
    }
}
