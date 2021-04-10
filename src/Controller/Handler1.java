package Controller;

import Model.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class Handler1 {

    // Linked list an Objekten die sich im Spiel befinden
    // Beispiel: Spieler, Wand, etc.
    public LinkedList<GameObject>objects = new LinkedList<GameObject>();

    private boolean up=false,down=false,right=false,left=false;

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
        for(int i=0;i<objects.size();i++){
            GameObject temp=objects.get(i);
            temp.tick();
        }
    }

    public void render(Graphics g){
        for(int i=0;i<objects.size();i++){
            GameObject temp=objects.get(i);
            temp.render(g);
        }
    }

    public void addObject(GameObject temp){
        objects.add(temp);
    }

    public void removeObject(GameObject temp){
        objects.remove(temp);
    }

}
