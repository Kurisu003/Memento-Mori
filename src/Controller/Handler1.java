package Controller;

import Model.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class Handler1 {

    // Linked list an Objekten die sich im Spiel befinden
    // Beispiel: Spieler, Wand, etc.
    public LinkedList<GameObject>objects = new LinkedList<GameObject>();

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

    private boolean up=false,down=false,right=false,left=false;
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
